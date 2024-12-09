package com.fps.android.core.infoProviders

import android.content.Context
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.safeException
import java.util.Locale
import java.util.TimeZone

internal interface ISettingInfoProvider {
    fun getTimezone(): String
    fun getSystemLanguage(): String
    fun getCountry(): String
}

internal class SettingInfoProvider(
    private val context: Context
) : ISettingInfoProvider {

    override fun getTimezone(): String {
        return safeException({
            TimeZone.getDefault().displayName
        }, UNKNOWN_VALUE)
    }

    override fun getSystemLanguage(): String {
        return safeException({
            Locale.getDefault().displayLanguage
        }, UNKNOWN_VALUE)
    }

    override fun getCountry(): String {
        return safeException({
            context.resources.configuration.locale.country
        }, UNKNOWN_VALUE)
    }
}