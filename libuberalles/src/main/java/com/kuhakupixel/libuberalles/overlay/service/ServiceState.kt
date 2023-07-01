package com.kuhakupixel.libuberalles.ui.overlay.service

import com.kuhakupixel.libuberalles.overlay.service.OverlayState

class ServiceState {
    val overlayButtonState = OverlayState()

    // these could be moved back into OverlayController
    // meh
    var screenWidthPx = Int.MAX_VALUE
    var screenHeightPx = Int.MAX_VALUE
}