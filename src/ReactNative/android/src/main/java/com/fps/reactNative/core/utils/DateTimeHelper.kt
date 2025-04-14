package com.fps.reactNative.core.utils

import android.annotation.SuppressLint
import com.fps.reactNative.core.constants.DATETIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class DateTimeHelper {
    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getCurrent(): String {
            val format = SimpleDateFormat(DATETIME_FORMAT, Locale.ENGLISH)
            return format.format(Date())
        }

        @SuppressLint("SimpleDateFormat")
        fun parseDateTime(strDate: String): Date? {
            val formatter = SimpleDateFormat(DATETIME_FORMAT)
            val date = formatter.parse(strDate)
            return date
        }
    }
}