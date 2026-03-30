package com.habit.app.helper

import com.habit.app.data.db.DBManager

object KeyValueManager {


    private const val STR_NUM_ONE = "1"
    private const val STR_NUM_ZERO = "0"

    /**
     * 是否进入主页
     */
    const val KEY_ENTERED_HOME = "is_entered_home"

    const val KEY_RATE_DIALOG = "key_rate_dialog"

    /**
     * 闪光灯开关、振动开关
     */
    const val KEY_LIGHT_SWITCH = "key_light_switch"
    const val KEY_LIGHT_SWITCH_ALARM = "key_light_switch_alarm"
    const val KEY_VIBRATE_SWITCH = "key_vibrate_switch"
    const val KEY_VIBRATE_SWITCH_ALARM = "key_vibrate_switch_alarm"

    /**
     * 找手机提醒 音量阈值
     */
    const val KEY_VOLUME_THRESHOLD = "key_volume_threshold"

    /**
     * 铃声时长
     */
    const val KEY_ALARM_DURATION = "key_alarm_duration"
    const val KEY_ALARM_SELECTED = "key_alarm_selected"
    const val KEY_ALARM_ALARM_SELECTED = "key_alarm_alarm_selected"
    const val KEY_ALARM_VOLUME = "key_alarm_volume"
    const val KEY_ALARM_SENSITIVITY = "key_alarm_sensitivity"

    /**
     * 闪光灯模式、振动模式
     */
    const val KEY_ALARM_MODEL_FLASH = "key_alarm_model_flash"
    const val KEY_ALARM_MODEL_VIBRATE = "key_alarm_model_vibrate"

    const val KEY_COLOR_START = "key_color_start"
    const val KEY_COLOR_END = "key_color_end"
    const val KEY_BRIGHTNESS = "key_brightness"
    const val KEY_SPEED = "key_speed"

    /**
     * 好评弹窗弹出时间戳
     * 按自然日记录
     */
    const val KEY_RATE_SHOW_STAMP = "key_rate_show_stamp"

    fun saveBooleanValue(key: String, value: Boolean) {
        DBManager.getDao().updateKeyValue(key, if (value) STR_NUM_ONE else STR_NUM_ZERO)
    }

    fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        val result = DBManager.getDao().getValueByKey(key)
            ?: if (defaultValue) STR_NUM_ONE else STR_NUM_ZERO
        return result == STR_NUM_ONE
    }


    fun saveValueWithKey(key: String, value: String) {
        DBManager.getDao().updateKeyValue(key, value)
    }

    fun getValueByKey(key: String): String? {
        return DBManager.getDao().getValueByKey(key)
    }
}