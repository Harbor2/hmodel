package com.habit.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.habit.app.R
import com.habit.app.databinding.ActivityMainBinding
import com.habit.app.helper.KeyValueManager
import com.habit.app.ui.base.BaseActivity
import com.habit.app.ui.base.BaseFragment
import com.wyz.emlibrary.util.immersiveWindow

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private val homeFragmentTag = "HomeFragment"
    private val alarmFragmentTag = "AlarmFragment"
    private val toolFragmentTag = "ToolFragment"
    private val settingFragmentTag = "SettingFragment"
    private var currentFragmentTag: String = homeFragmentTag


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        immersiveWindow(binding.root, false)
        setContentView(binding.root)
        KeyValueManager.saveBooleanValue(KeyValueManager.KEY_ENTERED_HOME, true)

        initData()
        initListener()
    }

    private fun initData() {
        // 设置底部选中
        binding.tabHome.isChecked = true
        switchFragment(currentFragmentTag)
    }

    private fun initListener() {
        binding.containerTabHome.setOnClickListener {
            binding.tabHome.isChecked = true
            binding.tabAlarm.isChecked = false
            binding.tabTool.isChecked = false
            binding.tabSetting.isChecked = false
            switchFragment(homeFragmentTag)
        }
        binding.containerTabAlarm.setOnClickListener {
            binding.tabHome.isChecked = false
            binding.tabAlarm.isChecked = true
            binding.tabTool.isChecked = false
            binding.tabSetting.isChecked = false
            switchFragment(alarmFragmentTag)
        }
        binding.containerTabTool.setOnClickListener {
            binding.tabHome.isChecked = false
            binding.tabAlarm.isChecked = false
            binding.tabTool.isChecked = true
            binding.tabSetting.isChecked = false
            switchFragment(toolFragmentTag)
        }
        binding.containerTabSetting.setOnClickListener {
            binding.tabHome.isChecked = false
            binding.tabAlarm.isChecked = false
            binding.tabTool.isChecked = false
            binding.tabSetting.isChecked = true
            switchFragment(settingFragmentTag)
        }
    }

    private fun switchFragment(tag: String) {
        currentFragmentTag = tag
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentByTag(tag)
        val transaction = fragmentManager.beginTransaction()
        if (currentFragment == null) {
            // Fragment 不存在，创建新的实例
            val newFragment = when (tag) {
                homeFragmentTag -> HomeFragment()
                alarmFragmentTag -> AlarmFragment()
                toolFragmentTag -> ToolFragment()
                settingFragmentTag -> SettingFragment()
                else -> return
            }
            transaction.add(R.id.fragment_container, newFragment, tag)
            newFragment.updateFragmentSelect(true)
        } else {
            // Fragment 已存在，直接显示
            transaction.show(currentFragment)
            (currentFragment as? BaseFragment<*>)?.updateFragmentSelect(true)
        }
        // 隐藏其他 Fragment
        for (fragment in fragmentManager.fragments) {
            if (fragment.tag != tag) {
                transaction.hide(fragment)
                (fragment as? BaseFragment<*>)?.updateFragmentSelect(false)
            }
        }
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}