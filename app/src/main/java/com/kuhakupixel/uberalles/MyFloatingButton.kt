package com.kuhakupixel.uberalles

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.kuhakupixel.libuberalles.overlay.OverlayManager
import com.kuhakupixel.libuberalles.overlay.service.OverlayButtonController
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow
import com.kuhakupixel.uberalles.ui.theme.UberAllesTheme


class MyFloatingButton : UberAllesWindow() {
    val TRASH_SIZE_DP = 90
    val OVERLAY_BUTTON_DEFAULT_SIZE_DP = 85

    private lateinit var overlayButtonController: OverlayButtonController
    private lateinit var overlayScreenController: MyOverlayScreenController
    fun onOverlayButtonClick() {
        // close the overlay button and open hacking menu
        overlayButtonController.disableView()
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Overlay Manager for drawing dialog and etc
        OverlayManager.init(
            windowManager = this.windowManager, service = this,
            applyTheme = { content ->
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
        overlayButtonController =
            OverlayButtonController(
                windowManager = windowManager,
                service = this,
                onClick = {
                    onOverlayButtonClick()
                    overlayButtonController.disableView()
                    overlayScreenController.enableView()
                },
                buttonRadiusDp = OVERLAY_BUTTON_DEFAULT_SIZE_DP,
                trashSizeDp = TRASH_SIZE_DP
            ) {
                Text("Button")
            }
        overlayScreenController =
            MyOverlayScreenController(

                service = this, windowManager = windowManager,
                onClosed = {
                    overlayScreenController.disableView()
                    overlayButtonController.enableView()
                },
            )
    }

    override fun onWindowShown() {
        super.onWindowShown()
        overlayButtonController.enableView()
    }
}