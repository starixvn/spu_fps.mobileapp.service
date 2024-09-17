package fps.mobile.client.infoProviders

import android.os.Build

interface IOsBuildInfoProvider {
    fun getManufacturerName(): String
    fun getModelName(): String
    fun getFingerprint(): String
    fun getAndroidVersion(): String
    fun getSdkVersion(): String
    fun getKernelVersion(): String
    fun getBuildTags(): String
    fun getBuildProduct(): String
    fun getHardware(): String
    fun getDisplay(): String
    fun getBoard(): String
    fun getBootLoader(): String
    fun getSerial(): String
    fun getDevice(): String
    fun getBrand(): String
}

class OsBuildInfoProvider : IOsBuildInfoProvider{

    override fun getManufacturerName(): String {
        return Build.MANUFACTURER!!
    }

    override fun getModelName(): String {
        return Build.MODEL!!
    }

    override fun getFingerprint(): String {
        return Build.FINGERPRINT!!
    }

    override fun getAndroidVersion(): String {
        return Build.VERSION.RELEASE!!
    }

    override fun getSdkVersion(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    override fun getKernelVersion(): String {
        return System.getProperty("os.version")!!
    }

    override fun getBuildTags(): String {
        return Build.TAGS
    }

    override fun getBuildProduct(): String {
        return Build.PRODUCT
    }

    override fun getHardware(): String {
        return Build.HARDWARE
    }

    override fun getDisplay(): String {
        return Build.DISPLAY
    }

    override fun getBoard(): String {
        return Build.BOARD
    }

    override fun getBootLoader(): String {
        return Build.BOOTLOADER
    }

    override fun getDevice(): String {
        return Build.DEVICE
    }

    override fun getSerial(): String {
        return Build.SERIAL
    }

    override fun getBrand(): String {
        return Build.BRAND
    }
}