package com.fps.android.core.utils

import android.os.Build
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Base64

internal object StringExtension {

    fun String.hashingMd5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }

    fun String.encode(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(this.toByteArray())
        } else {
            String(android.util.Base64.decode(this, android.util.Base64.DEFAULT))
        }
    }
}