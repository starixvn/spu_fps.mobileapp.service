package com.fps.android.core.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Base64

internal object StringExtension {

    fun String.hashingMd5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')
    }

    @SuppressLint("NewApi")
    fun String.encode(): String {
        return Base64.getEncoder().encodeToString(this.toByteArray())
    }
}