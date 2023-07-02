package com.kuhakupixel.libuberalles.overlay

import android.content.res.Resources.getSystem
import android.util.Log

fun logd(msg: String, includeCurrentLine: Boolean = false) {
    var msg: String = msg
    if (includeCurrentLine) {
        val currentLineInfo = Exception().stackTrace[1]
        msg = "$currentLineInfo: $msg"
    }
    Log.d("LibUberAlles", msg)
}

/**
 * Convert Pixel to Dp and Dp to Pixel
 * */
val Int.dp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
