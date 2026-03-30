package com.habit.app.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.habit.app.R
import com.habit.app.databinding.LayoutRateDialogBinding
import com.habit.app.helper.GooglePlayUtil
import com.habit.app.helper.KeyValueManager
import com.habit.app.helper.UtilHelper
import com.wyz.emlibrary.em.EMManager

class RatingDialog(val activity: Activity) : Dialog(activity) {
    var binding: LayoutRateDialogBinding
    private val handler = Handler(Looper.getMainLooper())

    init {
        window?.setBackgroundDrawableResource(R.color.transparent)

        binding = LayoutRateDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        setCancelable(true)
        EMManager.from(binding.containerContent)
            .setCorner(12f)
            .setBackGroundColor(R.color.white)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        val ratingStarViews = arrayListOf<ImageView>()

        for (i in 1..5) {
            val ratingStarView = binding.root.findViewWithTag<ImageView>(i.toString())
            ratingStarViews.add(ratingStarView)

            ratingStarView.setOnClickListener {
                for (j in 0 until i) {
                    ratingStarViews[j].setImageResource(R.drawable.png_star_on)
                }
                binding.tvResult.visibility = View.VISIBLE
                // 记录弹窗
                KeyValueManager.saveBooleanValue(KeyValueManager.KEY_RATE_DIALOG, true)
                if (i != 5) {
                    handler.postDelayed({
                        dismiss()
                    }, 1500L)
                } else {
                    GooglePlayUtil.launchGooglePlay(activity, object : GooglePlayUtil.GooglePlayFlowListener {
                        override fun onCompleteListener() {}
                        override fun onErrorListener() {}
                    })
                    dismiss()
                }
            }
        }
    }

    companion object {
        fun tryShowDialog(activity: Activity, autoShow: Boolean = false) : Dialog? {
            if (KeyValueManager.getBooleanValue(KeyValueManager.KEY_RATE_DIALOG, false)) {
                if (!autoShow) {
                    UtilHelper.showToast(activity, activity.getString(R.string.toast_rated))
                }
                return null
            }
            val recordData = KeyValueManager.getValueByKey(KeyValueManager.KEY_RATE_SHOW_STAMP) ?: ""
            val curData = UtilHelper.getCurData()
            if (recordData == curData && autoShow) {
                // 同一自然日弹出过 自动弹出才考虑
                return null
            }
            // 弹窗展示记录
            KeyValueManager.saveValueWithKey(KeyValueManager.KEY_RATE_SHOW_STAMP, curData)

            val dialog = RatingDialog(activity)
            val window: Window? = dialog.window
            window?.let {
                it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                it.setGravity(Gravity.CENTER)
            }
            dialog.show()
            return dialog
        }
    }
}