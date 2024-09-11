package fps.mobile.client.infoProviders

import android.os.Build

interface IOsBuildInfoProvider {
    fun getManufacturerName(): String
    fun getModelName(): String
    fun getFingerprint(): String
    fun getAndroidVersion(): String
    fun getSdkVersion(): String
    fun getKernelVersion(): String
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

}