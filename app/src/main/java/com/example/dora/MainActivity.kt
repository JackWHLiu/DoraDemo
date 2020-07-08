package com.example.dora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dora.bugskiller.DoraLog
import dora.bugskiller.LogConsolePolicy
import dora.bugskiller.LogNotificationPolicy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val a = 12
        val b = 3
        DoraLog.getChannel().setLogPolicy(LogConsolePolicy(this, LogNotificationPolicy(this)))
        DoraLog.print("${a} / ${b} = ${a/b}")
//        1 / 0
    }
}