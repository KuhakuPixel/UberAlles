package com.kuhakupixel.uberalles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kuhakupixel.libuberalles.overlay.OverlayManager
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import com.kuhakupixel.libuberalles.overlay.service.OverlayInterface
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayViewController
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow
import com.kuhakupixel.uberalles.ui.theme.UberAllesTheme
import android.graphics.PixelFormat
import android.util.Log
import android.view.WindowManager
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

@Composable
fun MyMainScreen() {
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
                OverlayManager.getInfoDialog()
                    .show("Title Of the Dialog", "Body of the dialog", onConfirm = {})
            }
        ) {
            Text("Show Info Dialog")
        }

        Button(
            onClick = fun() {
                OverlayManager.getInputDialog()
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

        Button(
            onClick = fun() {
                OverlayManager.getChoicesDialog()
                    .show(
                        title = "Input Choices",
                        choices = listOf("Hello", "World", "Third Choice"),
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
                MyOverlayManager.getMyCustomDialog()
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
    val windowManager: WindowManager, val service: UberAllesWindow, val onClosed: () -> Unit
) : OverlayInterface {

    private val hackingScreenController = OverlayViewController(
        createOverlayViewHolder = this::createOverlay,
        windowManager = windowManager,
        name = "Main Screen",
    )

    private fun createOverlay(): OverlayViewHolder {

        val hackingScreen = OverlayViewHolder(
            windowManager = windowManager,
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                0,
                0,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                0,
                PixelFormat.TRANSLUCENT
            ),
            alpha = 0.9f,
            service = service,
        ) {
            UberAllesTheme(darkTheme = true) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Button(onClick = onClosed) {
                                Text("Close")
                            }
                        }
                        MyMainScreen()
                    }
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
