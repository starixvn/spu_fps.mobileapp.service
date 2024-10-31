package com.fps.android.core.utils

import android.util.Base64
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


internal object StringExtension {

    fun String.hashingMd5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }

    fun String.encode(): String {
        val encodedBytes: ByteArray = Base64.encode(this.toByteArray(), Base64.DEFAULT)
        return String(encodedBytes, StandardCharsets.UTF_8)
    }
}