package com.fps.android.core.deviceIdProviders

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AndroidIdProviderTest {
    private lateinit var context: Context
    private lateinit var androidIdProvider: AndroidIdProvider

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext

        androidIdProvider = AndroidIdProvider(context)
    }

    @Test
    fun getId() {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        val id = androidIdProvider.getId()

        assertEquals(androidId, id)
    }
}