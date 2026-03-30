package com.example.testmodule

import android.content.Context
import android.widget.Toast

object TTUtil1 {
    fun showToast(context: Context) {
        Toast.makeText(context, "1111", Toast.LENGTH_SHORT).show()
    }

    fun showLongToast(context: Context) {
        Toast.makeText(context, "2222", Toast.LENGTH_LONG).show()
    }
}