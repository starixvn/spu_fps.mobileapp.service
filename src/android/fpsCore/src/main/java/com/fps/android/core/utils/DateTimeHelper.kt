package com.fps.android.core.utils

import android.annotation.SuppressLint
import com.fps.android.core.constants.DATETIME_FORMAT
import java.text.SimpleDateFormat
import java.util.Date

internal class DateTimeHelper {
    companion object {

        @SuppressLint("SimpleDateFormat")
        fun getCurrent(): String {
            val format = SimpleDateFormat(DATETIME_FORMAT)
            return format.format(Date())
        }
    }
}