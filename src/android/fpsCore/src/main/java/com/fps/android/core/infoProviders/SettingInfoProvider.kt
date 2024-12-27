package com.fps.android.core.infoProviders

import android.content.res.Resources
import android.os.Build
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.safeException
import java.util.Locale
import java.util.TimeZone

internal interface ISettingInfoProvider {
    fun getTimezone(): String
    fun getSystemLanguage(): String
    fun getCountry(): String
}

internal class SettingInfoProvider : ISettingInfoProvider {

    override fun getTimezone(): String {
        return safeException({
            return@safeException TimeZone.getDefault().id
        }, UNKNOWN_VALUE)
    }

    private fun getLocale(): Locale {
        return Resources.getSystem().configuration.locales.get(0)
    }

    override fun getSystemLanguage(): String {
        return safeException({
            getLocale().isO3Language
        }, UNKNOWN_VALUE)
    }

    override fun getCountry(): String {
        return safeException({
            getLocale().country
        }, UNKNOWN_VALUE)
    }
}