package com.kuhakupixel.libuberalles.overlay.service.dialog

import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.kuhakupixel.libuberalles.overlay.OverlayContext
import com.kuhakupixel.libuberalles.overlay.composables.NumberInputField


class OverlayInputDialog(
    overlayContext: OverlayContext, alpha: Float = 1.0f
) : OverlayDialog(
    overlayContext, alpha = alpha
) {
    private val valueInput: MutableState<String> = mutableStateOf("")

    @Composable
    override fun DialogBody() {
        NumberInputField(
            value = valueInput.value,
            onValueChange = { value ->
                valueInput.value = value
            },
            label = "Value Input ...",
            placeholder = "value ...",
        )
    }

    fun show(title: String, onConfirm: (input: String) -> Unit) {
        super.show(
            title = title,
            onConfirm = {
                onConfirm(valueInput.value)
            },
            onClose = {}
        )
    }


}