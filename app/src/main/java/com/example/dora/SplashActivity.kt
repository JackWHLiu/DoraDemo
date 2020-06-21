package com.example.dora

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.lwh.jackknife.permission.XPermission
import com.lwh.jackknife.permission.runtime.Permission
import dora.bugskiller.*

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
        val crashInfo = MyCrashInfo(this)
        //这个地址根据你拿到dora的服务端源码后，发布到服务器的实际地址为准
        val url = "http://doramusic.site:8081/saveCrashInfo"
        //不能在Application中初始化，因为动态申请权限需要Activity
        CrashConfig.Builder(this)
                //请查看SD卡的/sdcard/android-dora目录和Logcat的debug信息
            .crashReportPolicy(LogPolicy(StoragePolicy(DoraWebPolicy(url))))
            .filterChain(CrashReportFilterChain().addLast(DefaultFilter()).filter)
            .crashInfo(crashInfo)
            .enabled(true)
            .interceptCrash(false)
            .build()
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}