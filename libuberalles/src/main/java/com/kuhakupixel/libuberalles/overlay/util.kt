package com.kuhakupixel.libuberalles.overlay

import android.util.Log

fun logd(msg: String, includeCurrentLine: Boolean = false) {
    var msg: String = msg
    if (includeCurrentLine) {
        val currentLineInfo = Exception().stackTrace[1]
        msg = "$currentLineInfo: $msg"
    }
    Log.d("LibUberAlles", msg)
}