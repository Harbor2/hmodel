package com.habit.app.helper

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory

object GooglePlayUtil {

    interface GooglePlayFlowListener {
        fun onCompleteListener()
        fun onErrorListener()
    }

    fun launchGooglePlay(context: Activity, listener: GooglePlayFlowListener?) {
        val manager = ReviewManagerFactory.create(context)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo: ReviewInfo = task.result
                val flow: Task<Void> = manager.launchReviewFlow(context, reviewInfo)
                flow.addOnCompleteListener {
                    listener?.onCompleteListener()
                }
            } else {
                // There was some problem, log or handle the error code.
                listener?.onErrorListener()
            }
        }
    }
}