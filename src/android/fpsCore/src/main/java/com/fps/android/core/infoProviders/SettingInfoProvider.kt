package com.fps.android.core.infoProviders

import android.content.Context
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
        return TimeZone.getDefault().displayName
    }

    override fun getSystemLanguage(): String {
        return Locale.getDefault().displayLanguage
    }

    override fun getCountry(): String {
        return context.resources.configuration.locale.country
    }
}