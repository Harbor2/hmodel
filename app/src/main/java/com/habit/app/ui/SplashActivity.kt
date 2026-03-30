package com.habit.app.ui

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import com.habit.app.MyApplication
import com.habit.app.R
import com.habit.app.ui.base.BaseActivity
import com.habit.app.databinding.ActivitySplashBinding
import com.habit.app.helper.GrantedHelperSP
import com.habit.app.helper.KeyValueManager
import com.wyz.emlibrary.em.Direction
import com.wyz.emlibrary.em.EMManager

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var progressAnimator: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initListener()
    }

    private fun initView() {
        EMManager.from(binding.bgColor)
            .setGradientColor(intArrayOf(R.color.bg_start_color, R.color.bg_end_color), Direction.TOP)
        EMManager.from(binding.btnStart)
            .setCorner(24f)
            .setGradientColor(intArrayOf(R.color.btn_color_start, R.color.btn_color_end), Direction.LEFT_TOP)

        val isEnteredHome = KeyValueManager.getBooleanValue(KeyValueManager.KEY_ENTERED_HOME, false)
        if (!isEnteredHome) {
            binding.containerSplash.isVisible = false
            binding.containerStart.isVisible = true
        } else {
            binding.containerStart.isVisible = false
            binding.containerSplash.isVisible = true
        }
    }

    private fun initListener() {
        if (binding.containerSplash.isVisible) {
            // splash
            progressAnimator = ValueAnimator.ofInt(0, 100).apply {
                duration = 3000
                addUpdateListener { animation ->
                    binding.progressView.progress = animation.animatedValue as Int
                }
                addListener(onEnd = {
                    jumpMainPage()
                })
                start()
            }
        } else {
            // start
            binding.btnStart.setOnClickListener {
                if (!GrantedHelperSP.checkGranted(this)) {
                    // 同意协议
                    GrantedHelperSP.reportGranted(this)
                    (MyApplication.instance as MyApplication).initSdkIfNeed()
                    jumpMainPage()
                } else {
                    jumpMainPage()
                }
            }
        }
    }

    override fun jumpWelcomePage() {}

    private fun jumpMainPage() {
        MainActivity.startActivity(this)
        finish()
    }
}