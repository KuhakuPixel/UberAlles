package com.kuhakupixel.libuberalles.overlay

import android.view.WindowManager
import androidx.compose.runtime.Composable
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayChoicesDialog
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayInfoDialog
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayInputDialog

class OverlayContext(
    val windowManager: WindowManager,
    val service: UberAllesWindow,
    val applyTheme: (@Composable (content: @Composable () -> Unit) -> Unit)? = null
) {
}