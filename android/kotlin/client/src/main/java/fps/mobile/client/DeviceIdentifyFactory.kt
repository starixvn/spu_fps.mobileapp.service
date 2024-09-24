package fps.mobile.client

import android.content.Context
import fps.mobile.client.deviceIdProviders.AndroidIdProvider
import fps.mobile.client.deviceIdProviders.GoogleAdIdProvider
import fps.mobile.client.deviceIdProviders.GsfIdProvider
import fps.mobile.client.deviceIdProviders.IDeviceIdProvider
import fps.mobile.client.deviceIdProviders.MediaDrmIdProvider
import fps.mobile.client.deviceIdProviders.UuidProvider
import fps.mobile.client.infoProviders.*

internal class DeviceIdentifyFactory(context: Context) {

    companion object {

        private var batteryInfoProvider: IBatteryInfoProvider? = null
        private var cameraInfoProvider: ICameraInfoProvider? = null
        private var codecInfoProvider: ICodecInfoProvider? = null
        private var cpuInfoProvider: ICpuInfoProvider? = null
        private var deviceInfoProvider: IDeviceInfoProvider? = null
        private var displayInfoProvider: IDisplayInfoProvider? = null
        private var gpuInfoProvider: IGpuInfoProvider? = null
        private var inputDeviceInfoProvider: IInputDeviceInfoProvider? = null
        private var memoryInfoProvider: IMemoryInfoProvider? = null
        private var osBuildInfoProvider: IOsBuildInfoProvider? = null
        private var packageInfoProvider: IPackageInfoProvider? = null
        private var securityInfoProvider: ISecurityInfoProvider? = null
        private var sensorInfoProvider: ISensorInfoProvider? = null
        private var settingInfoProvider: ISettingInfoProvider? = null

        private var androidIdProvider: IDeviceIdProvider? = null
        private var mediaDrmIdProvider: MediaDrmIdProvider? = null
        private var gsfIdProvider: IDeviceIdProvider? = null
        private var uuidProvider: IDeviceIdProvider? = null
        private var googleAdIdProvider: IDeviceIdProvider? = null

        internal fun getBatteryInfoProvider(): IBatteryInfoProvider {
            return batteryInfoProvider!!
        }

        internal fun getCameraInfoProvider(): ICameraInfoProvider {
            return cameraInfoProvider!!
        }

        internal fun getCodecInfoProvider(): ICodecInfoProvider {
            return codecInfoProvider!!
        }

        internal fun getCpuInfoProvider(): ICpuInfoProvider {
            return cpuInfoProvider!!
        }

        internal fun getDeviceInfoProvider(): IDeviceInfoProvider {
            return deviceInfoProvider!!
        }

        internal fun getDisplayInfoProvider(): IDisplayInfoProvider {
            return displayInfoProvider!!
        }

        internal fun getGpuInfoProvider(): IGpuInfoProvider {
            return gpuInfoProvider!!
        }

        internal fun getInputDeviceInfoProvider(): IInputDeviceInfoProvider {
            return inputDeviceInfoProvider!!
        }

        internal fun getMemoryInfoProvider(): IMemoryInfoProvider {
            return memoryInfoProvider!!
        }

        internal fun getOsBuildInfoProvider(): IOsBuildInfoProvider {
            return osBuildInfoProvider!!
        }

        internal fun getPackageInfoProvider(): IPackageInfoProvider {
            return packageInfoProvider!!
        }

        internal fun getSecurityInfoProvider(): ISecurityInfoProvider {
            return securityInfoProvider!!
        }

        internal fun getSensorInfoProvider(): ISensorInfoProvider {
            return sensorInfoProvider!!
        }

        internal fun getSettingInfoProvider(): ISettingInfoProvider {
            return settingInfoProvider!!
        }

        internal fun getAndroidIdProvider(): IDeviceIdProvider {
            return androidIdProvider!!
        }

        internal fun getMediaDrmIdProvider(): MediaDrmIdProvider {
            return mediaDrmIdProvider!!
        }

        internal fun getGsfIdProvider(): IDeviceIdProvider {
            return gsfIdProvider!!
        }

        internal fun getUuidProvider(): IDeviceIdProvider {
            return uuidProvider!!
        }

        internal fun getGoogleAdIdProvider(): IDeviceIdProvider {
            return googleAdIdProvider!!
        }
    }

    init {
        if (batteryInfoProvider == null) {
            batteryInfoProvider = BatteryInfoProvider(context)
        }

        if (cameraInfoProvider == null) {
            cameraInfoProvider = CameraInfoProvider(context)
        }

        if (codecInfoProvider == null) {
            codecInfoProvider = CodecInfoProvider()
        }

        if (cpuInfoProvider == null) {
            cpuInfoProvider = CpuInfoProvider()
        }

        if (deviceInfoProvider == null) {
            deviceInfoProvider = DeviceInfoProvider(context)
        }

        if (displayInfoProvider == null) {
            displayInfoProvider = DisplayInfoProvider(context)
        }

        if (gpuInfoProvider == null) {
            gpuInfoProvider = GpuInfoProvider(context)
        }

        if (inputDeviceInfoProvider == null) {
            inputDeviceInfoProvider = InputDeviceInfoProvider(context)
        }

        if (memoryInfoProvider == null) {
            memoryInfoProvider = MemoryInfoProvider(context)
        }

        if (osBuildInfoProvider == null) {
            osBuildInfoProvider = OsBuildInfoProvider()
        }

        if (packageInfoProvider == null) {
            packageInfoProvider = PackageInfoProvider(context)
        }

        if (securityInfoProvider == null) {
            securityInfoProvider = SecurityInfoProvider()
        }

        if (sensorInfoProvider == null) {
            sensorInfoProvider = SensorInfoProvider(context)
        }

        if (settingInfoProvider == null) {
            settingInfoProvider = SettingInfoProvider(context)
        }

        if (androidIdProvider == null) {
            androidIdProvider = AndroidIdProvider(context.contentResolver)
        }

        if (mediaDrmIdProvider == null) {
            mediaDrmIdProvider = MediaDrmIdProvider()
        }

        if (gsfIdProvider == null) {
            gsfIdProvider = GsfIdProvider(context.contentResolver)
        }

        if (uuidProvider == null) {
            uuidProvider = UuidProvider(context)
        }

        if (googleAdIdProvider == null) {
            googleAdIdProvider = GoogleAdIdProvider(context)
        }
    }
}