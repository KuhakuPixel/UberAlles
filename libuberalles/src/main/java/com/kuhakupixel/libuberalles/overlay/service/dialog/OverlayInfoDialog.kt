package com.kuhakupixel.libuberalles.overlay.service.dialog

import android.view.WindowManager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder

class OverlayInfoDialog(
    createDialogOverlay: (
        content: @Composable () -> Unit,
    ) -> OverlayViewHolder,

    windowManager: WindowManager
) : OverlayDialog(
    createDialogOverlay = createDialogOverlay, windowManager = windowManager,
) {
    private val dialogText: MutableState<String> = mutableStateOf("")

    @Composable
    override fun DialogBody() {
        Text(
            dialogText.value, modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }

    fun show(title: String, text: String, onConfirm: () -> Unit) {
        dialogText.value = text
        super.show(title = title, onConfirm = onConfirm, onClose = {})
    }


}