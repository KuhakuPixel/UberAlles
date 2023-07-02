package com.kuhakupixel.uberalles

import androidx.compose.material3.Text
import com.kuhakupixel.libuberalles.overlay.service.OverlayButtonController
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow


class MyFloatingButton : UberAllesWindow() {
    val TRASH_SIZE_DP = 90
    val OVERLAY_BUTTON_DEFAULT_SIZE_DP = 85

    private lateinit var overlayButtonController: OverlayButtonController
    fun onOverlayButtonClick() {
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
                buttonRadiusDp = OVERLAY_BUTTON_DEFAULT_SIZE_DP,
                trashSizeDp = TRASH_SIZE_DP
            ) {
                Text("Button")
            }
    }

    override fun onWindowShown() {
        super.onWindowShown()
        overlayButtonController.enableView()
    }
}