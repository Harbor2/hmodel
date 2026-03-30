package com.habit.app.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.habit.app.MyApplication
import com.habit.app.event.BaseEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()

        if (MyApplication.hotLaunchCondition) {
            jumpWelcomePage()
        }
    }

    open fun jumpWelcomePage() {
        MyApplication.hotLaunchCondition = false
    }

    @Subscribe
    fun onBaseEvent(event: BaseEvent) {
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}