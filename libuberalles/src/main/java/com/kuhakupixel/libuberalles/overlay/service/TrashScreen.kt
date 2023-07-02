package com.kuhakupixel.libuberalles.overlay.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import com.kuhakupixel.libuberalles.ui.overlay.service.ServiceState
import com.kuhakupixel.libuberalles.overlay.composables.Trash

@Composable
fun TrashContentScreen(
    showOverlayButton: Boolean,
    serviceState: ServiceState,
    buttonRadiusDp: Int,
    trashSizeDp: Int,
) {

    Box(Modifier.onGloballyPositioned {
        serviceState.screenWidthPx = it.size.width
        serviceState.screenHeightPx = it.size.height
    }) {
        // future.txt correct z-order
        if (showOverlayButton) {
            ShowTrash(
                serviceState = serviceState,
                buttonRadiusDp = buttonRadiusDp,
                trashSizeDp = trashSizeDp
            )
        }
    }
}

@Composable
fun ShowTrash(serviceState: ServiceState, buttonRadiusDp: Int, trashSizeDp: Int) {
    val overlayState = serviceState.overlayButtonState

    if (overlayState.showTrash) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Trash(overlayState, buttonRadiusDp = buttonRadiusDp, trashSizeDp = trashSizeDp)
        }
    }
}


