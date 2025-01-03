package com.fps.android.core.deviceIdProviders

import android.content.ContentResolver
import android.content.Context
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AndroidIdProviderTest {

    private lateinit var context: Context
    private lateinit var contentResolver: ContentResolver
    private lateinit var androidIdProvider: AndroidIdProvider

    companion object {
        const val TEST_ANDROID_ID = "1234567890abcdef"
        const val UNKNOWN_VALUE = "unknown"
    }

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun `getId should return Android ID when available`() {
        // When
        val id = androidIdProvider.getId()

        // Then
        assertEquals(TEST_ANDROID_ID, id)
    }
}
