package com.kuhakupixel.libuberalles.ui.overlay.service

import com.kuhakupixel.libuberalles.overlay.OVERLAY_BUTTON_DEFAULT_SIZE_DP
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.core.app.NotificationCompat
import com.kuhakupixel.libuberalles.overlay.FOREGROUND_SERVICE_NOTIFICATION_ID
import com.kuhakupixel.libuberalles.overlay.logd
import com.kuhakupixel.libuberalles.overlay.service.OverlayButtonController

open class UberAllesWindow : Service() {
    val state = ServiceState()

    private lateinit var overlayButtonController: OverlayButtonController
    private val windowManager get() = getSystemService(WINDOW_SERVICE) as WindowManager


    fun onOverlayButtonClick() {
        logd("Overlay Button Is Clicked")
        // close the overlay button and open hacking menu
        overlayButtonController.disableView()
    }

    var enableOverlayButton = true
    override fun onCreate() {
        super.onCreate()
        overlayButtonController =
            OverlayButtonController(
                windowManager = windowManager,
                service = this,
                onClick = {
                    // make sure to not allow double click
                    // to avoid crash
                    if (enableOverlayButton) {
                        onOverlayButtonClick()
                        enableOverlayButton = false
                    }
                },
                buttonRadiusDp = OVERLAY_BUTTON_DEFAULT_SIZE_DP
            ) {
                Text("Button")
            }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logd("FloatingService onStartCommand")
        startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
        overlayButtonController.enableView()
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