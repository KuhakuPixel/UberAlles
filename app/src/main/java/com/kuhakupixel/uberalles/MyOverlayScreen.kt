package com.kuhakupixel.uberalles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kuhakupixel.libuberalles.overlay.OverlayContext
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import com.kuhakupixel.libuberalles.overlay.service.OverlayInterface
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayViewController
import com.kuhakupixel.uberalles.ui.theme.UberAllesTheme
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayChoicesDialog
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayInfoDialog
import com.kuhakupixel.libuberalles.overlay.service.dialog.OverlayInputDialog

@Composable
fun MyMainScreen(overlayContext: OverlayContext) {
    var i: MutableState<Int> = remember { mutableStateOf(0) }
    Column() {
        Text(text = "current value ${i.value}")
        Button(onClick = {
            i.value++
        }) {
            Text("Click me to incremeant")
        }

        Button(
            onClick = fun() {
                OverlayInfoDialog(overlayContext)
                    .show("Title Of the Dialog", "Body of the dialog", onConfirm = {})
            }
        ) {
            Text("Show Info Dialog")
        }

        Button(
            onClick = fun() {
                OverlayInfoDialog(overlayContext, alpha = 0.90f)
                    .show("Title Of the Dialog", "Body of the dialog", onConfirm = {})
            }
        ) {
            Text("Show Transparent Info Dialog")
        }

        Button(
            onClick = fun() {
                OverlayInputDialog(overlayContext)
                    .show(
                        "Title Of the Dialog",
                        onConfirm = { input: String ->
                            Log.d("UberAlles", "input is $input")
                        }
                    )
            }
        ) {
            Text("Show Input Dialog")
        }

        val choices: MutableList<String> = mutableListOf()
        for (idx in 1..100)
            choices.add("Choice ${idx}")
        Button(
            onClick = fun() {
                OverlayChoicesDialog(overlayContext)
                    .show(
                        title = "Input Choices",
                        choices = choices,
                        chosenIndex = 0,
                        onConfirm = { index, input ->
                            Log.d("UberAlles", "input is $input, index is $index")
                        },
                        onClose = {}
                    )

            }
        ) {
            Text("Show Choices Dialog")
        }

        Button(
            onClick = fun() {
                MyCustomOverlayDialog(overlayContext)
                    .show(
                        title = "Custom Dialog",
                        onConfirm = { input, isChecked ->
                            Log.d("UberAlles", "Input is $input, isChecked $isChecked")
                        }
                    )

            }
        ) {
            Text("Custom Dialog")
        }

    }
}

class MyOverlayScreenController(
    val overlayContext: OverlayContext, val onClosed: () -> Unit
) : OverlayInterface {

    private val hackingScreenController = OverlayViewController(
        createOverlayViewHolder = this::createOverlay,
        windowManager = overlayContext.windowManager,
        name = "Main Screen",
    )

    private fun createOverlay(): OverlayViewHolder {

        val hackingScreen = OverlayViewHolder(
            windowManager = overlayContext.windowManager,
            alpha = 0.9f,
            service = overlayContext.service,
        ) {
            overlayContext.applyTheme?.invoke {
                Column(modifier = Modifier.fillMaxSize()) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(onClick = onClosed) {
                            Text("Close")
                        }
                    }
                    MyMainScreen(overlayContext)
                }
            }
        }
        return hackingScreen
    }

    override fun disableView() {
        hackingScreenController.disableView()
    }

    override fun enableView() {
        hackingScreenController.enableView()
    }
}
