package com.fps.android.core.deviceIdProviders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.Settings

internal class AndroidIdProvider(
    context: Context
) : IDeviceIdProvider {

    private val contentResolver: ContentResolver = context.contentResolver

    @SuppressLint("HardwareIds")
    override fun getId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

}