package com.kuhakupixel.uberalles

import com.kuhakupixel.libuberalles.overlay.OverlayManager

/**
 * demonstrate how to create custom dialog by creating
 * Subclassing OverlayManager
 * */
class MyOverlayManager : OverlayManager() {
    companion object {
        fun getMyCustomDialog(): MyCustomDialog {
            return MyCustomDialog(
                createDialogOverlay = { content -> OverlayManager.createDialogOverlay(content) },
                windowManager = windowManager!!
            )
        }

    }
}