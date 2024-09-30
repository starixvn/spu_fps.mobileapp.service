package fps.mobile.app.data

import android.content.Context
import fps.mobile.app.constants.DEVICE_STATE_DEVICE_INFO
import fps.mobile.app.constants.HARDWARE_DEVICE_INFO
import fps.mobile.app.constants.INSTALLED_APPS_DEVICE_INFO
import fps.mobile.app.constants.OS_DEVICE_INFO
import fps.mobile.client.DeviceIdentify
import fps.mobile.client.DeviceInfo
import kotlin.time.Duration
import kotlin.time.TimeSource

class DataLoader(context: Context) {
    companion object {
        private var fingerprints: List<DeviceInfo>? = null
        private var deviceId: List<DeviceInfo>? = null
        private var deviceInfo: List<DeviceInfo>? = null
        private var dataPackage: String? = null
        private var loadingTime: Duration? = null

        fun getLoadingTime(): Duration {
            return loadingTime!!
        }

        fun getFingerprints(): List<DeviceInfo> {
            if(fingerprints == null)
                fingerprints = DeviceIdentify.getFingerprints()
            return fingerprints!!
        }

        fun getDeviceId(): List<DeviceInfo> {
            if(deviceId == null)
                deviceId = DeviceIdentify.getDeviceIds()
            return deviceId!!
        }

        fun getDeviceInfo(fingerprint: String? = null): List<DeviceInfo> {
            if(deviceInfo == null)
                deviceInfo = DeviceIdentify.getDeviceInfoList()

            var result = deviceInfo!!
            if(fingerprint != null) {
                result = when(fingerprint) {
                    "Hardware Fingerprint" -> result.filter { info -> HARDWARE_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
                    "OS Fingerprint" -> result.filter { info -> OS_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
                    "Installed Apps FP" -> result.filter { info -> INSTALLED_APPS_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
                    "Device State FP" -> result.filter { info -> DEVICE_STATE_DEVICE_INFO.any { info.name.contains(it) } } as ArrayList<DeviceInfo>
                    else -> result
                }
            }

            return result
        }

        fun getDeviceInfoByGroup(group: String): List<DeviceInfo> {
            val deviceInfo = getDeviceInfo()
            return deviceInfo.filter { it.name.startsWith(group) };
        }

        fun getDeviceInfoGroups(deviceInfoList: List<DeviceInfo>? = null): List<String> {
            var deviceInfo = deviceInfoList

            if(deviceInfoList == null)
                deviceInfo = getDeviceInfo()

            return deviceInfo!!.map { it.name.split('|').first() }.distinct()
        }

        fun getDataPackage(userName: String): String {
            if(dataPackage == null)
                dataPackage = DeviceIdentify.getDataPackage(userName)
            return dataPackage!!
        }
    }

    init {
        val timeSource = TimeSource.Monotonic
        val start = timeSource.markNow()
        fingerprints = DeviceIdentify.getFingerprints()
        deviceId = DeviceIdentify.getDeviceIds()
        deviceInfo = DeviceIdentify.getDeviceInfoList()
        val end = timeSource.markNow()
        loadingTime = end - start
    }
}