package com.kuhakupixel.libuberalles.overlay.service.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuhakupixel.libuberalles.overlay.OverlayContext
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayEnableDisableMode
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayViewController


@Composable
private fun DrawOverlayDialog(
    title: String,
    onConfirm: () -> Unit,
    onClose: () -> Unit,
    body: @Composable () -> Unit,
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Text(
            title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.1f),
        )
        Box(Modifier.weight(0.8f)) {
            body()
        }
        // Cancel - Okay Button
        Row(
            Modifier
                .weight(0.1f)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onClose,
            ) { Text("Cancel") }

            Button(
                onClick = {
                    onClose()
                    onConfirm()
                },
            ) { Text("Okay") }
        }
    }
}


/**
 * Show Dialog For overlay screen
 * this can be subclassed to create an even more complex dialog
 * for example, input dialog, multiple choice dialog and etc
 * which have a title, a cancel, confirm button
 * and content which can be set by overriding [DialogBody]
 * */
abstract class OverlayDialog(
    val overlayContext: OverlayContext, val alpha: Float = 1.0f
) {
    private var overlayViewController: OverlayViewController? = null

    open fun createDialogOverlay(
        content: @Composable () -> Unit,
    ): OverlayViewHolder {

        val dialogViewHolder = OverlayViewHolder(
            windowManager = overlayContext.windowManager,
            alpha = alpha,
            service = overlayContext.service,
        ) {
            overlayContext.applyTheme?.invoke {
                content()
            }

        }

        return dialogViewHolder
    }


    @Composable
    abstract fun DialogBody()

    /**
     * close this dialog
     * */
    open fun close(){
        // TODO: also call onClose
        this.overlayViewController!!.disableView()
    }

    /**
     * open the dialog
     * */
    open fun show(title: String, onConfirm: () -> Unit, onClose: () -> Unit = {}) {
        this.overlayViewController =
            OverlayViewController(
                createOverlayViewHolder = fun(): OverlayViewHolder {
                    return createDialogOverlay {
                        DrawOverlayDialog(
                            title = title,
                            body = { DialogBody() },
                            onConfirm = onConfirm,
                            onClose = {
                                onClose()
                                close()
                            },
                        )
                    }
                },
                windowManager = overlayContext.windowManager,
                enableDisableMode = OverlayEnableDisableMode.CREATE_AND_DESTROY,
            )
        overlayViewController!!.enableView()
    }

}