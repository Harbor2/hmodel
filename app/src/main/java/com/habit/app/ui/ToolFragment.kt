package com.habit.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.habit.app.databinding.FragmentToolBinding
import com.habit.app.ui.base.BaseFragment

class ToolFragment() : BaseFragment<FragmentToolBinding>() {

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentToolBinding {
        return FragmentToolBinding.inflate(inflater, parent, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}