package com.fps.android.core.deviceIdProviders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import com.fps.android.core.utils.safeException

internal class AndroidIdProvider(
    context: Context
) : IDeviceIdProvider {

    private val contentResolver: ContentResolver = context.contentResolver

    @SuppressLint("HardwareIds")
    override fun getId(): String? {
        return safeException({
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        }, null)
    }

}