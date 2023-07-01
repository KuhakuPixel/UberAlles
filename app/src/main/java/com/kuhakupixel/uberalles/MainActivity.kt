package com.kuhakupixel.uberalles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.kuhakupixel.atg.ui.overlay.service.FloatingService
import com.kuhakupixel.libuberalles.overlay.INTENT_COMMAND
import com.kuhakupixel.libuberalles.overlay.INTENT_COMMAND_OVERLAY_BUTTON_CREATE
import com.kuhakupixel.libuberalles.overlay.OverlayPermission
import com.kuhakupixel.uberalles.ui.theme.UberAllesTheme

class MainActivity : ComponentActivity() {

    fun askForOverlayPermission() {
        OverlayPermission.askForOverlayPermission(
            context = applicationContext,
            componentActivity = this,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            UberAllesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenu(askForOverlayPermission = { askForOverlayPermission() })
                }
            }
        }
    }
}

@Composable
fun MainMenu(askForOverlayPermission: () -> Unit) {
    val context = LocalContext.current
    val showAskForDrawOverOtherApp: MutableState<Boolean> = remember { mutableStateOf(false) }
    Row {
        Button(
            onClick = fun() {
                if (!Settings.canDrawOverlays(context)) {
                    showAskForDrawOverOtherApp.value = true
                    return
                }
                //
                startOverlayButton(context)
            },


            ) {
            Text("Show Overlay Button")
        }
    }
    if (showAskForDrawOverOtherApp.value) {
        AlertDialog(
            confirmButton = {
                Button(
                    onClick = {
                        askForOverlayPermission()
                        showAskForDrawOverOtherApp.value = false

                    },
                ) {
                    Text("Okay")
                }
            },
            onDismissRequest = {
                showAskForDrawOverOtherApp.value = false
            },
            text = { Text("Please Grant Overlay draw app permission") },
        )

    }

}

fun startOverlayButton(context: Context) {
    val intent = Intent(context.applicationContext, MyCustomService::class.java)
    intent.putExtra(INTENT_COMMAND, INTENT_COMMAND_OVERLAY_BUTTON_CREATE)
    context.startForegroundService(intent)
}