package com.kuhakupixel.libuberalles.overlay.service

import android.content.Context.INPUT_SERVICE
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.os.Build
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kuhakupixel.libuberalles.ui.overlay.service.UberAllesWindow
import com.kuhakupixel.libuberalles.ui.overlay.service.OverlayViewController
import com.kuhakupixel.libuberalles.ui.overlay.service.ServiceState
import com.kuhakupixel.libuberalles.overlay.OverlayViewHolder
import com.kuhakupixel.libuberalles.overlay.logd
import com.kuhakupixel.libuberalles.overlay.px

val LocalServiceState = compositionLocalOf<ServiceState> { error("No ServiceState provided") }

class OverlayButtonController(
    val windowManager: WindowManager,
    val service: UberAllesWindow,
    val onClick: () -> Unit,
    val buttonRadiusDp: Int,
    val content: @Composable () -> Unit,
) : OverlayInterface {

    private val overlayButtonState = service.state.overlayButtonState
    val buttonRadiusPx = buttonRadiusDp.px
    private val trashScreenViewController = OverlayViewController(
        createOverlayViewHolder = this::createTrashScreenOverlay,
        windowManager = windowManager,
        name = "FullScreen"
    )

    private val overlayButtonViewController = OverlayViewController(
        createOverlayViewHolder = this::createOverlayButtonClickTarget,
        windowManager = windowManager,
        name = "Button ClickTarget"
    )


    private fun createTrashScreenOverlay(): OverlayViewHolder {

        // https://developer.android.com/reference/android/view/WindowManager.LayoutParams#MaximumOpacity
        var alpha = 1f
        val inputManager =
            service.applicationContext.getSystemService(INPUT_SERVICE) as InputManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            logd("inputManager max opacity ${inputManager.maximumObscuringOpacityForTouch}")
            alpha = inputManager.maximumObscuringOpacityForTouch
        }

        val fullscreenOverlay = OverlayViewHolder(
            windowManager = windowManager,
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ),
            alpha = alpha,

            service = service,
        )


        fullscreenOverlay.setContent {
            CompositionLocalProvider(LocalServiceState provides service.state) {
                TrashContentScreen(
                    showOverlayButton = overlayButtonState.isVisible.value,
                    serviceState = service.state,
                    buttonRadiusDp = buttonRadiusDp,
                )
            }
        }


        return fullscreenOverlay
    }

    private fun createOverlayButtonClickTarget(): OverlayViewHolder {
        val overlayButtonClickTarget = OverlayViewHolder(
            windowManager = windowManager,
            params = WindowManager.LayoutParams(
                buttonRadiusPx,
                buttonRadiusPx,
                0,
                0,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ),
            alpha = 0.9f, service = service,
        )
        overlayButtonClickTarget.setContent {

            val showClickTarget = remember { mutableStateOf(true) }
            if (showClickTarget.value) {
                ClickTarget(
                    serviceState = service.state,
                    controller = this,
                    overlayState = overlayButtonState,
                    viewHolder = overlayButtonClickTarget,
                    onDropOnTrash = {
                        exitOverlayButton()
                        showClickTarget.value = false
                    },
                    onClick = {
                        onClick()
                    },
                    buttonContent = content,
                )
            }
        }
        return overlayButtonClickTarget
    }


    override fun enableView() {

        logd("Init the controller ")
        trashScreenViewController.enableView()
        overlayButtonViewController.enableView()
        overlayButtonState.isVisible.value = true
    }

    override fun disableView() {
        exitOverlayButton()
    }

    private fun exitOverlayButton() {
        trashScreenViewController.disableView()
        overlayButtonViewController.disableView()
        overlayButtonState.isVisible.value = false
        service.stopSelf()
    }
}