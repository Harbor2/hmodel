package com.habit.app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.habit.app.data.TAG
import com.wyz.emlibrary.em.EMLibrary
import com.habit.app.data.db.DBManager
import com.habit.app.helper.GrantedHelperSP

class MyApplication : Application() {
    private var isUserGrant = false
    private var isColdStart = true
    private var lastBackgroundTime = 0L
    private var activityStartCount = 0

    companion object {
        private const val BACKGROUND_THRESHOLD = 5000 // 5 秒
        /**
         * 热启动条件是否满足
         * 完成需要重置状态
         */
        var hotLaunchCondition = false
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
    }

    private val activityLifeCallback = object: ActivityLifecycleCallbacks {
        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            if (activityStartCount == 1) {
                Log.w(TAG, "app回到了前台")

                // 从后台 → 前台
                val now = System.currentTimeMillis()
                if (!isColdStart && lastBackgroundTime > 0 && !hotLaunchCondition) {
                    val stayTime = now - lastBackgroundTime
                    if (stayTime >= BACKGROUND_THRESHOLD) {
                        hotLaunchCondition = true
                    }
                }
                isColdStart = false
            }
        }
        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount == 0) {
                Log.w(TAG, "app退到了后台")
                // 前台 → 后台
                lastBackgroundTime = System.currentTimeMillis()
            }
        }
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        override fun onActivityResumed(activity: Activity) {}
        override fun onActivityPaused(activity: Activity) {}
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {}
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(activityLifeCallback)
        // 初始化
        initOnMainProcess()

        initSdkIfNeed()
    }

    private fun initOnMainProcess() {
        EMLibrary.init(this)
        DBManager.init(this)
    }

    fun initSdkIfNeed() {
        if (!isUserGrant && GrantedHelperSP.checkGranted(this)) {
            Log.d(TAG, "用户同意了隐私协议")
        }
    }
}