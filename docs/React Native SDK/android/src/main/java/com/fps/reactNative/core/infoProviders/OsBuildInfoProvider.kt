package com.fps.reactNative.core.infoProviders

import android.annotation.SuppressLint
import android.os.Build
import com.fps.reactNative.core.constants.UNKNOWN_VALUE
import com.fps.reactNative.core.utils.safeException

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
    fun getSdkVersion(): String
}

internal class OsBuildInfoProvider : IOsBuildInfoProvider {
    override fun getModelName(): String {
        return safeException({
            Build.MODEL!!
        }, UNKNOWN_VALUE)
    }

    override fun getManufacturerName(): String {
        return safeException({
            Build.MANUFACTURER!!
        }, UNKNOWN_VALUE)
    }

    override fun getFingerprint(): String {
        return safeException({
            Build.FINGERPRINT!!
        }, UNKNOWN_VALUE)
    }

    override fun getHardware(): String {
        return safeException({
            Build.HARDWARE!!
        }, UNKNOWN_VALUE)
    }

    override fun getBuildProduct(): String {
        return safeException({
            Build.PRODUCT!!
        }, UNKNOWN_VALUE)
    }

    override fun getBoard(): String {
        return safeException({
            Build.BOARD!!
        }, UNKNOWN_VALUE)
    }

    override fun getBootLoader(): String {
        return safeException({
            Build.BOOTLOADER!!
        }, UNKNOWN_VALUE)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("HardwareIds")
    override fun getSerial(): String {
        return safeException({
            Build.SERIAL!!
        }, UNKNOWN_VALUE)
    }

    override fun getDevice(): String {
        return safeException({
            Build.DEVICE!!
        }, UNKNOWN_VALUE)
    }

    override fun getBrand(): String {
        return safeException({
            Build.BRAND!!
        }, UNKNOWN_VALUE)
    }

    override fun getSdkVersion(): String {
        return safeException({
            Build.VERSION.SDK_INT.toString()
        }, UNKNOWN_VALUE)
    }

}