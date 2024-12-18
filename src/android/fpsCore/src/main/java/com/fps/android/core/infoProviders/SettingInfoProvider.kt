package com.fps.android.core.infoProviders

import android.content.Context
import android.os.Build
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.safeException
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

internal interface ISettingInfoProvider {
    fun getTimezone(): String
    fun getSystemLanguage(): String
    fun getCountry(): String
    fun getDeviceLanguageDisplayName(context: Context): String
}

internal class SettingInfoProvider(
    private val context: Context
) : ISettingInfoProvider {

    override fun getTimezone(): String {
        return safeException({
            val timeZone = TimeZone.getDefault()
            val offsetMillis = timeZone.rawOffset + timeZone.dstSavings // Add DST offset if applicable
            val hours = offsetMillis / (1000 * 60 * 60)
            val minutes = abs(offsetMillis / (1000 * 60) % 60)

            return@safeException String.format(Locale.getDefault(), "%s%02d:%02d", if (hours >= 0) "+" else "-", abs(hours), minutes)
        }, UNKNOWN_VALUE)
    }

    private fun getDeviceLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }

    override fun getDeviceLanguageDisplayName(context: Context): String{
        return safeException({
            getDeviceLocale(context).getDisplayLanguage(getDeviceLocale(context))
        }, UNKNOWN_VALUE)
    }

    override fun getSystemLanguage(): String {

        Locale.getDefault()
        return safeException({
            getDeviceLocale(context).isO3Language
        }, UNKNOWN_VALUE)
    }

    override fun getCountry(): String {
        return safeException({
            getDeviceLocale(context).country
        }, UNKNOWN_VALUE)
    }
}