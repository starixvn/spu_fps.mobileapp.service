package com.fps.android.core.utils

import com.fps.android.core.utils.ByteArrayExtension.toHexString
import org.junit.Assert.assertEquals
import org.junit.Test

class ByteArrayExtensionTest {

    @Test
    fun `convert byte array to hex string`() {
        val byteArray = byteArrayOf(0x0F, 0x1A, 0x2B)
        val hexString = byteArray.toHexString()
        assertEquals("0f1a2b", hexString)
    }

    @Test
    fun `convert empty byte array to empty hex string`() {
        val byteArray = byteArrayOf()

        val hexString = byteArray.toHexString()

        assertEquals("", hexString)
    }

    @Test
    fun `convert byte array with negative values to hex string`() {
        val byteArray = byteArrayOf(-0x01, -0x80)

        val hexString = byteArray.toHexString()

        assertEquals("ff80", hexString)
    }
}