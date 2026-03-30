package com.habit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.habit.app.databinding.FragmentAlarmBinding
import com.habit.app.ui.base.BaseFragment

class AlarmFragment() : BaseFragment<FragmentAlarmBinding>() {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentAlarmBinding {
        return FragmentAlarmBinding.inflate(inflater, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}