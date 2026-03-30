package com.habit.app.helper

import android.Manifest
import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.habit.app.data.FLASH_DEFAULT
import com.habit.app.data.VIBRATE_DEFAULT
import com.wyz.emlibrary.util.EMUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil

object UtilHelper {

    fun getAppVersionName(context: Context): String {
        var versionName: String = ""
        try {
            versionName = context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0.0"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return String.format("v%s", versionName)
    }

    fun showToast(context: Context, str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    /**
     * 获取当前的年月日信息
     */
    fun getCurDate(): String {
        val dateFormat = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        return dateFormat.format(date)
    }

    fun hasRecordAudioPerm(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * ms转分秒
     */
    fun convertMsToMinSec(milliseconds: Long): String {
        val totalSeconds = ceil(milliseconds / 1000.0).toInt()
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds) // 格式化为 MM:SS
    }

    fun checkFlashPerm(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    fun checkMicPerm(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
    }

    fun checkNotificationEnable(context: Context): Boolean {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

    fun shareApp(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun isServiceRunning(context: Context, serviceClass: Class<out Service>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = manager.getRunningServices(Int.MAX_VALUE)

        for (service in services) {
            if (service.service.className == serviceClass.name) {
                return true
            }
        }
        return false
    }

    fun isFlashSwitchOn(context: Context): Boolean {
        return KeyValueManager.getBooleanValue(KeyValueManager.KEY_LIGHT_SWITCH, true) && checkFlashPerm(context)
    }

    fun isVibrateSwitchOn(): Boolean {
        return KeyValueManager.getBooleanValue(KeyValueManager.KEY_VIBRATE_SWITCH, true)
    }

    fun setLightSwitch(switchOn: Boolean) {
        KeyValueManager.saveBooleanValue(KeyValueManager.KEY_LIGHT_SWITCH, switchOn)
    }

    fun setVibrateSwitch(switchOn: Boolean) {
        KeyValueManager.saveBooleanValue(KeyValueManager.KEY_VIBRATE_SWITCH, switchOn)
    }

    fun setFlashModel(model: String) {
        KeyValueManager.saveValueWithKey(KeyValueManager.KEY_ALARM_MODEL_FLASH, model)
    }

    fun getFlashModel(): String {
        return KeyValueManager.getValueByKey(KeyValueManager.KEY_ALARM_MODEL_FLASH) as? String ?: FLASH_DEFAULT
    }

    fun setVibrateModel(model: String) {
        KeyValueManager.saveValueWithKey(KeyValueManager.KEY_ALARM_MODEL_VIBRATE, model)
    }

    fun getVibrateModel(): String {
        return KeyValueManager.getValueByKey(KeyValueManager.KEY_ALARM_MODEL_VIBRATE) as? String?: VIBRATE_DEFAULT
    }

    fun getCurData(): String {
        return EMUtil.formatDateFromTimestamp("yyyy-MM-dd")
    }
}