package com.kuhakupixel.uberalles

import androidx.compose.material3.Text
import com.kuhakupixel.libuberalles.overlay.service.OverlayButtonController
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow


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