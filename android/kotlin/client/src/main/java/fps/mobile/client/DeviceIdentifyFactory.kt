package fps.mobile.client

import android.content.Context
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

        internal fun getBatteryInfoProvider(): IBatteryInfoProvider {
            return batteryInfoProvider!!;
        }

        internal fun getCameraInfoProvider(): ICameraInfoProvider {
            return cameraInfoProvider!!;
        }

        internal fun getCodecInfoProvider(): ICodecInfoProvider {
            return codecInfoProvider!!;
        }

        internal fun getCpuInfoProvider(): ICpuInfoProvider {
            return cpuInfoProvider!!;
        }

        internal fun getDeviceInfoProvider(): IDeviceInfoProvider {
            return deviceInfoProvider!!;
        }

        internal fun getDisplayInfoProvider(): IDisplayInfoProvider {
            return displayInfoProvider!!;
        }

        internal fun getGpuInfoProvider(): IGpuInfoProvider {
            return gpuInfoProvider!!;
        }

        internal fun getInputDeviceInfoProvider(): IInputDeviceInfoProvider {
            return inputDeviceInfoProvider!!;
        }

        internal fun getMemoryInfoProvider(): IMemoryInfoProvider {
            return memoryInfoProvider!!;
        }

        internal fun getOsBuildInfoProvider(): IOsBuildInfoProvider {
            return osBuildInfoProvider!!;
        }

        internal fun getPackageInfoProvider(): IPackageInfoProvider {
            return packageInfoProvider!!;
        }

        internal fun getSecurityInfoProvider(): ISecurityInfoProvider {
            return securityInfoProvider!!;
        }

        internal fun getSensorInfoProvider(): ISensorInfoProvider {
            return sensorInfoProvider!!;
        }

        internal fun getSettingInfoProvider(): ISettingInfoProvider {
            return settingInfoProvider!!;
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
    }
}