package com.kuhakupixel.libuberalles.overlay

import android.view.WindowManager
import androidx.compose.runtime.Composable
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayServiceEntry

class OverlayContext(
    val windowManager: WindowManager,
    val service: OverlayServiceEntry,
    val applyTheme: (@Composable (content: @Composable () -> Unit) -> Unit)? = null
) {
}