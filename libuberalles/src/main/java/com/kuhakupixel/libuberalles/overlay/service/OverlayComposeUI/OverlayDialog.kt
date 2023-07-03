package com.kuhakupixel.libuberalles.overlay.service.OverlayComposeUI

import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayEnableDisableMode
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayViewController


@Composable
private fun DrawOverlayDialog(
    title: String,
    onConfirm: () -> Unit,
    onClose: () -> Unit,
    body: @Composable (modifier: Modifier) -> Unit,
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
        body(Modifier.weight(0.8f))
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
 * and content which can be set by [InitDialogBody] by subclasses
 * */
open class OverlayDialog(
    private val createDialogOverlay: (
        content: @Composable () -> Unit,
    ) -> OverlayViewHolder,
    private val windowManager: WindowManager,
) {
    private var overlayViewController: OverlayViewController? = null

    init {


    }

    @Composable
    open fun DialogBody() {
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
                            body =
                            { modifier: Modifier ->
                                Box(modifier = modifier) {
                                    DialogBody()
                                }
                            },
                            onConfirm = onConfirm,
                            onClose = {
                                onClose()
                                this.overlayViewController!!.disableView()
                            },
                        )
                    }
                },
                windowManager = windowManager,
                enableDisableMode = OverlayEnableDisableMode.CREATE_AND_DESTROY,
            )
        overlayViewController!!.enableView()
    }

}