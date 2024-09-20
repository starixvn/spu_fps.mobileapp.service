package fps.mobile.app.data

import android.content.Context
import fps.mobile.client.DeviceIdentify
import fps.mobile.client.DeviceInfo

class DataLoader(context: Context) {
    companion object {
        private var fingerprints: List<DeviceInfo>? = null
        private var deviceId: List<DeviceInfo>? = null
        private var deviceInfo: List<DeviceInfo>? = null

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

        fun getDeviceInfo(): List<DeviceInfo> {
            if(deviceInfo == null)
                deviceInfo = DeviceIdentify.getDeviceInfoList()
            return deviceInfo!!
        }
    }

    init {
        DeviceIdentify(context = context)
        fingerprints = DeviceIdentify.getFingerprints()
        deviceId = DeviceIdentify.getDeviceIds()
        deviceInfo = DeviceIdentify.getDeviceInfoList()
    }
}