package com.example.dora

import android.content.Context
import dora.bugskiller.CrashInfo

class MyCrashInfo(context: Context?) :
    CrashInfo(context) {

    /**
     * 扩展后重写toString()。
     */
    override fun toString(): String {
        val tips = "不要慌，BUG来了，小场面而已"
        return "${tips}${super.toString()}"
    }
}