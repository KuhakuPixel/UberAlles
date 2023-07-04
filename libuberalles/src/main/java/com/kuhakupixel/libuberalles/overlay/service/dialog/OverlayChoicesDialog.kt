package com.kuhakupixel.libuberalles.overlay.service.dialog

import android.view.WindowManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.kuhakupixel.libuberalles.overlay.OverlayContext
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder

class OverlayChoicesDialog(
    overlayContext: OverlayContext
) : OverlayDialog(
    overlayContext
) {
    private val choices: MutableState<List<String>> = mutableStateOf(listOf())
    private val chosenIndex: MutableState<Int> = mutableStateOf(0)

    @Composable
    override fun DialogBody() {
        Column(Modifier.fillMaxSize()) {
            for (i in 0 until choices.value.size) {
                Row {
                    Text(choices.value[i], modifier = Modifier.weight(0.8f))
                    Checkbox(
                        modifier = Modifier.weight(0.2f),
                        // only checked if its chosen
                        checked = chosenIndex.value == i,
                        onCheckedChange = { checked: Boolean ->
                            // set chosen index if its checked
                            if (checked)
                                chosenIndex.value = i
                        },
                    )

                }
            }
        }
    }

    fun show(
        title: String, choices: List<String>,
        chosenIndex: Int,
        onConfirm: (index: Int, input: String) -> Unit,
        onClose: () -> Unit,
    ) {
        this.choices.value = choices
        this.chosenIndex.value = chosenIndex
        super.show(
            title = title,
            onConfirm = {
                val chosenValue: String = this.choices.value[this.chosenIndex.value]
                onConfirm(this.chosenIndex.value, chosenValue)
            },
            onClose = onClose
        )
    }


}