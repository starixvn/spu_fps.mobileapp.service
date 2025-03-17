package com.fps.flutter.core.utils

import android.annotation.SuppressLint
import com.fps.flutter.core.constants.DATETIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Date

internal class DateTimeHelper {
    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getCurrent(): String {
            val format = SimpleDateFormat(DATETIME_FORMAT)
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