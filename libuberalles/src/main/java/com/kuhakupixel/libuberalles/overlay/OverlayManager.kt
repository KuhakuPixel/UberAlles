package com.kuhakupixel.libuberalles.overlay

import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.compose.runtime.Composable
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayChoicesDialog
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayInfoDialog
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayInputDialog

class OverlayManager(
) {

    companion object {
        private var windowManager: WindowManager? = null
        private var service: UberAllesWindow? = null
        private var applyTheme: (@Composable (content: @Composable () -> Unit) -> Unit)? = null

        public fun init(
            windowManager: WindowManager,
            service: UberAllesWindow,
            applyTheme: @Composable (content: @Composable () -> Unit) -> Unit
        ) {
            OverlayManager.windowManager = windowManager
            OverlayManager.service = service
            OverlayManager.applyTheme = applyTheme
        }

        private fun createDialogOverlay(
            content: @Composable () -> Unit,
        ): OverlayViewHolder {

            val dialogViewHolder = OverlayViewHolder(
                windowManager = windowManager!!,
                params = WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    0,
                    0,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    0,
                    PixelFormat.TRANSLUCENT
                ),
                alpha = 1f,
                service = service!!,
            )

            dialogViewHolder.setContent {
                applyTheme!!{
                    content()
                }

            }

            return dialogViewHolder
        }

        init {
        }

        fun getInfoDialog(): OverlayInfoDialog {
            return OverlayInfoDialog(
                createDialogOverlay = ::createDialogOverlay,
                windowManager = windowManager!!
            )
        }

        fun getInputDialog(): OverlayInputDialog {
            return OverlayInputDialog(
                createDialogOverlay = ::createDialogOverlay,
                windowManager = windowManager!!
            )
        }

        fun getChoicesDialog(): OverlayChoicesDialog {
            return OverlayChoicesDialog(
                createDialogOverlay = ::createDialogOverlay,
                windowManager = windowManager!!
            )
        }
    }
}