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
            .notification() //申请通知栏权限
            .permission()
            .onGranted {
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
            .start()
    }

    private fun initCrashSDK() {
        val crashInfo = MyCrashInfo(this)
        //这个地址根据你拿到dora的服务端源码后，发布到服务器的实际地址为准
        val url = "http://doramusic.site:8081/saveCrashInfo"
        //不能在Application中初始化，因为动态申请权限需要Activity
        DoraConfig.Builder(this)
                //请查看SD卡的/sdcard/android-dora目录和Logcat的debug信息
            .crashReportPolicy(LogcatPolicy(StoragePolicy(DoraWebPolicy(url))))
            .filterChain(CrashReportFilterChain().addLast(DefaultFilter()).filter)
            .crashInfo(crashInfo)   //可以自定义崩溃详细信息
            .enabled(true)  //全局启用/禁用Dora
            .interceptCrash(false)  //是否防止app闪退
            .initLogNotification(true)  //默认false，如果使用了LogNotificationPolicy，这个属性必须为true
            .build()
        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
        }, 2000)
    }
}