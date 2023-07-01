package com.kuhakupixel.libuberalles.overlay.service.OverlayComposeUI

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberInputField(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    value: String,
    label: String = "",
    placeholder: String = "",
    onValueChange: (String) -> Unit,
) {

    TextField(
        modifier = modifier,
        enabled = enabled,
        value = value,
        onValueChange = { value ->
            // make sure we don't pass in value with \n
            // because of enter, because that will cause the caller
            // parsing problem
            onValueChange(value.replace("\n", ""))
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
    )
}

class OverlayInputDialog(
    createDialogOverlay: (
        content: @Composable () -> Unit,
    ) -> OverlayViewHolder, windowManager: WindowManager
) : OverlayDialog(
    createDialogOverlay = createDialogOverlay, windowManager = windowManager,
) {
    private val valueInput: MutableState<String> = mutableStateOf("")

    init {
        InitDialogBody {
            NumberInputField(
                value = valueInput.value,
                onValueChange = { value ->
                    valueInput.value = value
                },
                label = "Value Input ...",
                placeholder = "value ...",
            )
        }
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