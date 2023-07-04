package com.kuhakupixel.uberalles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.kuhakupixel.libuberalles.overlay.OverlayContext
import com.kuhakupixel.libuberalles.overlay.composables.NumberInputField
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayDialog

class MyCustomOverlayDialog(
    overlayContext: OverlayContext, alpha: Float = 1.0f
) : OverlayDialog(
    overlayContext, alpha = alpha
) {
    private val valueInput: MutableState<String> = mutableStateOf("")
    private val checked: MutableState<Boolean> = mutableStateOf(false)

    @Composable
    override fun DialogBody() {
        Column {
            NumberInputField(
                value = valueInput.value,
                onValueChange = { value ->
                    valueInput.value = value
                },
                label = "Value Input ...",
                placeholder = "value ...",
            )
            Row() {
                Text("Your Checkbox")
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = { value -> checked.value = value })
            }
        }

    }

    fun show(title: String, onConfirm: (input: String, isChecked: Boolean) -> Unit) {
        super.show(
            title = title,
            onConfirm = {
                onConfirm(valueInput.value, checked.value)
            },
            onClose = {}
        )
    }


}
