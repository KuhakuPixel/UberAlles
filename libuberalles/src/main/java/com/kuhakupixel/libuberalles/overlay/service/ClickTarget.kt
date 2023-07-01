package com.kuhakupixel.libuberalles.overlay.service

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.kuhakupixel.libuberalles.ui.overlay.service.ServiceState
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import com.kuhakupixel.libuberalles.overlay.logd
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

// doitwrong
@Composable
fun ClickTarget(
    serviceState: ServiceState,
    controller: OverlayButtonController,
    overlayState: OverlayState,
    viewHolder: OverlayViewHolder,
    onDropOnTrash: () -> Unit,
    onClick: () -> Unit,
    buttonContent: @Composable () -> Unit,
) {
    Button(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        logd("clicktarget onDragStart")
                        overlayState.showTrash = true
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val dragAmountIntOffset =
                            IntOffset(dragAmount.x.roundToInt(), dragAmount.y.roundToInt())
                        val _timerOffset = overlayState.timerOffset + dragAmountIntOffset
                        var x = max(_timerOffset.x, 0)
                        x = min(x, serviceState.screenWidthPx - controller.timerSizePx)
                        var y = max(_timerOffset.y, 0)
                        y = min(y, serviceState.screenHeightPx - controller.timerSizePx)
                        // this is the good code
                        overlayState.timerOffset = IntOffset(x, y)

                        viewHolder.updateViewPos(
                            x = overlayState.timerOffset.x,
                            y = overlayState.timerOffset.y,
                        )
                    },
                    onDragEnd = {
                        logd("onDragEnd")
                        overlayState.showTrash = false

                        if (overlayState.isTimerHoverTrash) {
                            onDropOnTrash()
                            return@detectDragGestures
                        }

                        logd("onDragEnd x ${overlayState.timerOffset.x}")
                    },
                )
            },
        onClick = { onClick() },
    ) {
        buttonContent()
    }
}