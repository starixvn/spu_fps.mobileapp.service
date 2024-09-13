package fps.mobile.client

import android.content.Context
import fps.mobile.client.deviceIdProviders.AndroidIdProvider
import fps.mobile.client.deviceIdProviders.GoogleAdIdProvider
import fps.mobile.client.deviceIdProviders.GsfIdProvider
import fps.mobile.client.deviceIdProviders.MediaDrmIdProvider
import fps.mobile.client.deviceIdProviders.UuidProvider
import fps.mobile.client.fingerprintProviders.HardwareFingerprintProvider
import fps.mobile.client.fingerprintProviders.InstalledAppsFingerprintProvider
import fps.mobile.client.fingerprintProviders.OsFingerprintProvider
import fps.mobile.client.infoProviders.PackageInfoProvider


data class DeviceInfo(val name: String, val value: String)
object DeviceIdentify {

    fun getDeviceInfo(context: Context): ArrayList<DeviceInfo> {
        val androidIdProvider = AndroidIdProvider(context.contentResolver)
        val mediaDrmIdProvider = MediaDrmIdProvider()
        val gsfIdProvider = GsfIdProvider(context.contentResolver)
        val deviceIdProvider = UuidProvider(context)
        val googleAdIdProvider = GoogleAdIdProvider(context)

        val hardwareFingerprintProvider = HardwareFingerprintProvider(context)
        val osFingerprintProvider = OsFingerprintProvider()
        val installedAppsFingerprintProvider = InstalledAppsFingerprintProvider(context)

        val deviceInfoList: ArrayList<DeviceInfo> = ArrayList()
        deviceInfoList.add(DeviceInfo("AndroidID", androidIdProvider.getAndroidId()))
        deviceInfoList.add(DeviceInfo("Media DRM ID", mediaDrmIdProvider.getMediaDrmId()))
        deviceInfoList.add(
            DeviceInfo(
                "Media DRM ID With Model",
                mediaDrmIdProvider.getMediaDrmIdWithModel()
            )
        )
        deviceInfoList.add(DeviceInfo("GSF ID", gsfIdProvider.getGsfId() ?: "unknown"))
        deviceInfoList.add(DeviceInfo("Device ID", deviceIdProvider.getDeviceUuid() ?: "unknown"))
        deviceInfoList.add(
            DeviceInfo(
                "Google Ad ID",
                googleAdIdProvider.getGoogleAdId() ?: "unknown"
            )
        )

        deviceInfoList.add(
            DeviceInfo(
                "Hardware Fingerprint",
                hardwareFingerprintProvider.getFingerprint()
            )
        )

        deviceInfoList.add(
            DeviceInfo(
                "OS Fingerprint",
                osFingerprintProvider.getFingerprint()
            )
        )

        deviceInfoList.add(
            DeviceInfo("Installed Apps FP", installedAppsFingerprintProvider.getFingerprint())
        )

        return deviceInfoList
    }
}