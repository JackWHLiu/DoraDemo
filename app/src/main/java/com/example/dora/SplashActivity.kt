package com.example.dora

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lwh.jackknife.permission.XPermission
import com.lwh.jackknife.permission.runtime.Permission
import dora.bugskiller.CrashConfig
import dora.bugskiller.LogPolicy
import dora.bugskiller.StoragePolicy

class SplashActivity : AppCompatActivity() {

    val REQUEST_CODE_SETTING = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        XPermission.with(this)
            .runtime()
            .permission(Permission.WRITE_EXTERNAL_STORAGE)
            .onGranted {
                try {
                    initCrashSDK()
                } catch (throwable: Throwable) {
                    throwable.printStackTrace()
                }
            }
            .onDenied { permissions ->
                if (XPermission.hasAlwaysDeniedPermission(this@SplashActivity, permissions)) {
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    XPermission.with(this@SplashActivity).runtime().setting()
                        .start(REQUEST_CODE_SETTING)
                }
            }
            .start()
    }

    private fun initCrashSDK() {
        //不能在Application中初始化，因为动态申请权限需要Activity
        CrashConfig.Builder(this)
                //请查看SD卡的/sdcard/android-dora目录和Logcat的debug信息
            .crashReportPolicy(LogPolicy(StoragePolicy()))
            .testOnly(false)
            .build()
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}