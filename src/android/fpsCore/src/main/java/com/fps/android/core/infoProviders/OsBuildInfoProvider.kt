package com.fps.android.core.infoProviders

import android.annotation.SuppressLint
import android.os.Build

internal interface IOsBuildInfoProvider {
    fun getModelName(): String
    fun getManufacturerName(): String
    fun getFingerprint(): String
    fun getHardware(): String
    fun getBuildProduct(): String
    fun getBoard(): String
    fun getBootLoader(): String
    fun getSerial(): String
    fun getDevice(): String
    fun getBrand(): String
    fun getDisplay(): String
    fun getSdkVersion(): String
    fun getBuildTags(): String
}

internal class OsBuildInfoProvider : IOsBuildInfoProvider {
    override fun getModelName(): String {
        return Build.MODEL!!
    }

    override fun getManufacturerName(): String {
        return Build.MANUFACTURER!!
    }

    override fun getFingerprint(): String {
        return Build.FINGERPRINT!!
    }

    override fun getHardware(): String {
        return Build.HARDWARE
    }

    override fun getBuildProduct(): String {
        return Build.PRODUCT
    }

    override fun getBoard(): String {
        return Build.BOARD
    }

    override fun getBootLoader(): String {
        return Build.BOOTLOADER
    }

    @Suppress("DEPRECATION")
    @SuppressLint("HardwareIds")
    override fun getSerial(): String {
        return Build.SERIAL
    }

    override fun getDevice(): String {
        return Build.DEVICE
    }

    override fun getBrand(): String {
        return Build.BRAND
    }

    override fun getDisplay(): String {
        return Build.DISPLAY
    }

    override fun getSdkVersion(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    override fun getBuildTags(): String {
        return Build.TAGS
    }
}