package fps.mobile.client

import android.content.Context
import fps.mobile.client.deviceIdProviders.AndroidIdProvider
import fps.mobile.client.deviceIdProviders.GoogleAdIdProvider
import fps.mobile.client.deviceIdProviders.GsfIdProvider
import fps.mobile.client.deviceIdProviders.MediaDrmIdProvider
import fps.mobile.client.deviceIdProviders.UuidProvider
import fps.mobile.client.fingerprintProviders.DeviceStateFingerprintProvider
import fps.mobile.client.fingerprintProviders.HardwareFingerprintProvider
import fps.mobile.client.fingerprintProviders.InstalledAppsFingerprintProvider
import fps.mobile.client.fingerprintProviders.OsFingerprintProvider
import fps.mobile.client.infoProviders.DeviceInfoProvider
import fps.mobile.client.infoProviders.SettingInfoProvider


data class DeviceInfo(val name: String, val value: String)

class DeviceIdentify(context: Context) {

    companion object {
        fun getFingerprints(): ArrayList<DeviceInfo> {
            val hardwareFingerprintProvider = HardwareFingerprintProvider()
            val osFingerprintProvider = OsFingerprintProvider()
            val installedAppsFingerprintProvider = InstalledAppsFingerprintProvider()
            val deviceStateFingerprintProvider = DeviceStateFingerprintProvider()

            val deviceInfoList: ArrayList<DeviceInfo> = ArrayList()

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

            deviceInfoList.add(
                DeviceInfo("Device State FP", deviceStateFingerprintProvider.getFingerprint())
            )

            return deviceInfoList
        }

        fun getDeviceIds(): ArrayList<DeviceInfo> {
            val androidIdProvider = DeviceIdentifyFactory.getAndroidIdProvider()
            val mediaDrmIdProvider = DeviceIdentifyFactory.getMediaDrmIdProvider()
            val gsfIdProvider = DeviceIdentifyFactory.getGsfIdProvider()
            val uuIdProvider = DeviceIdentifyFactory.getUuidProvider()
            val googleAdIdProvider = DeviceIdentifyFactory.getGoogleAdIdProvider()

            val deviceInfoList: ArrayList<DeviceInfo> = ArrayList()

            deviceInfoList.add(DeviceInfo("AndroidID", androidIdProvider.getId() ?: "unknown"))

            deviceInfoList.add(DeviceInfo("Media DRM ID", mediaDrmIdProvider.getId() ?: "unknown"))

            deviceInfoList.add(DeviceInfo("GSF ID", gsfIdProvider.getId() ?: "unknown"))

            deviceInfoList.add(
                DeviceInfo(
                    "UUID",
                    uuIdProvider.getId() ?: "unknown"
                )
            )

            deviceInfoList.add(
                DeviceInfo(
                    "Google Ad ID",
                    googleAdIdProvider.getId() ?: "unknown"
                )
            )

            return deviceInfoList
        }
    }

    init {
        DeviceIdentifyFactory(context)
    }
}