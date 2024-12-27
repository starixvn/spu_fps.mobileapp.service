package com.fps.android.core.infoProviders

import android.content.res.Resources
import android.os.Build
import android.util.Log
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.ThrowableHandler
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
        Log.d("FPS", "getLocale: ${Resources.getSystem().configuration.locales.get(0)}")
        ThrowableHandler.add("getLocale: ${Resources.getSystem().configuration.locales.get(0)}")

        return Resources.getSystem().configuration.locales.get(0)
    }

    override fun getSystemLanguage(): String {

        Log.d("FPS", "Resources.getSystem().configuration.locales.get(0): ${getLocale().isO3Language}")
        ThrowableHandler.add("Resources.getSystem().configuration.locales.get(0): ${getLocale().isO3Language}")


        Log.d("FPS", "Locale Default: ${Locale.getDefault().isO3Language}")
        ThrowableHandler.add("Locale Default: ${Locale.getDefault().isO3Language}")

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