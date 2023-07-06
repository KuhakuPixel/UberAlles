package com.kuhakupixel.uberalles

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kuhakupixel.libuberalles.overlay.OverlayContext
import com.kuhakupixel.libuberalles.overlay.service.OverlayDraggableViewController
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayServiceEntry
import com.kuhakupixel.uberalles.ui.theme.UberAllesTheme

class MyOverlayServiceEntry : OverlayServiceEntry() {
    val TRASH_SIZE_DP = 90
    val OVERLAY_BUTTON_DEFAULT_SIZE_DP = 85

    private lateinit var overlayDraggableViewController: OverlayDraggableViewController
    private lateinit var overlayScreenController: MyOverlayScreenController
    override fun onCreate() {
        super.onCreate()
        // Initialize Overlay Context for drawing dialog and etc
        val overlayContext = OverlayContext(
            windowManager = this.windowManager, service = this,
            applyTheme = { content ->
                // applying theme to overlay view
                UberAllesTheme(darkTheme = true) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                        content = content
                    )
                }
            },
        )
        //
        overlayDraggableViewController =
            OverlayDraggableViewController(
                windowManager = windowManager,
                service = this,
                onDestroyed = {
                    Log.d("UberAlles", "Button Destroyed")
                    this.stopSelf()
                },
                buttonRadiusDp = OVERLAY_BUTTON_DEFAULT_SIZE_DP,
                trashSizeDp = TRASH_SIZE_DP,
                viewAlpha = 0.8f,
            ) {
                Image(
                    painter = painterResource(R.drawable.icon),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            overlayDraggableViewController.disableView()
                            overlayScreenController.enableView()
                        },
                )
            }
        overlayScreenController =
            MyOverlayScreenController(
                overlayContext = overlayContext,
                onClosed = {
                    overlayScreenController.disableView()
                    overlayDraggableViewController.enableView()
                },
            )
    }

    override fun onOverlayServiceStarted() {
        overlayDraggableViewController.enableView()
    }
}