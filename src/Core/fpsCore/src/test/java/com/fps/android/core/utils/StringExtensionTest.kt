package com.fps.android.core.utils

import com.fps.android.core.utils.StringExtension.encode
import com.fps.android.core.utils.StringExtension.hashingMd5
import org.junit.Assert.assertEquals
import org.junit.Test

class StringExtensionTest {

    @Test
    fun hashingMd5_isCorrect() {
        val stringInput1 = "AndroidDevelopmentIsGreatBut"
        val stringInput2 = "You should be okay with Activity Lifecycle"
        assertEquals("99e7319722588bedfeb7a4a0eb5bb052", stringInput1.hashingMd5())
        assertEquals("e91298ab3a7a70392dcdff49e82e5241", stringInput2.hashingMd5())
        assertEquals("9c962d790ce79a7365508db4505d4cf9", (stringInput1 + stringInput2).hashingMd5())
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", "".hashingMd5())
    }

    @Test
    fun encode_isCorrect() {
        val stringInput1 = "AndroidDevelopmentIsGreatBut"
        val stringInput2 = "You should be okay with Activity Lifecycle"
        assertEquals("QW5kcm9pZERldmVsb3BtZW50SXNHcmVhdEJ1dA==", stringInput1.encode())
        assertEquals("WW91IHNob3VsZCBiZSBva2F5IHdpdGggQWN0aXZpdHkgTGlmZWN5Y2xl", stringInput2.encode())
        assertEquals("QW5kcm9pZERldmVsb3BtZW50SXNHcmVhdEJ1dFlvdSBzaG91bGQgYmUgb2theSB3aXRoIEFjdGl2aXR5IExpZmVjeWNsZQ==", (stringInput1 + stringInput2).encode())
        assertEquals("", "".encode())
    }
}