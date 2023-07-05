package com.kuhakupixel.libuberalles.ui.overlay.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.kuhakupixel.libuberalles.overlay.FOREGROUND_SERVICE_NOTIFICATION_ID
import com.kuhakupixel.libuberalles.overlay.logd

abstract class OverlayServiceEntry : Service() {
    val state = ServiceState()
    open val windowManager get() = getSystemService(WINDOW_SERVICE) as WindowManager

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    abstract fun onOverlayServiceStarted()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logd("FloatingService onStartCommand")
        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
        onOverlayServiceStarted()
        return START_STICKY
    }

    // https://stackoverflow.com/a/47533338/14073678
    private fun buildNotification(): Notification {

        // If earlier version channel ID is not used
        // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
        var channelId: String = ""
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId =
                createNotificationChannel(
                    "LibUberAllesOverlayButton",
                    "LibUberAlles's overlay button service"
                )
        }

        val notification: Notification =
            NotificationCompat.Builder(this, channelId).build()
        return notification
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
}