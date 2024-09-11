package fps.mobile.client
import android.content.Context
import fps.mobile.client.deviceIdProviders.AndroidIdProvider
import fps.mobile.client.deviceIdProviders.DeviceIdProvider
import fps.mobile.client.deviceIdProviders.GoogleAdIdProvider
import fps.mobile.client.deviceIdProviders.GsfIdProvider
import fps.mobile.client.deviceIdProviders.MediaDrmIdProvider
import fps.mobile.client.fingerprintProviders.HardwareFingerprintProvider
import fps.mobile.client.infoProviders.DisplayInfoProvider


data class DeviceInfo(val name:String, val value: String)
object DeviceIdentify {

    fun getDeviceInfo(context: Context): ArrayList<DeviceInfo> {
        val androidIdProvider =  AndroidIdProvider(context.contentResolver)
        val mediaDrmIdProvider = MediaDrmIdProvider()
        val gsfIdProvider = GsfIdProvider(context.contentResolver)
        val deviceIdProvider = DeviceIdProvider(context)
        val googleAdIdProvider = GoogleAdIdProvider(context)
//        val batteryInfoProvider = BatteryInfoProvider(context)
//        val cameraInfoProvider = CameraInfoProvider(context)
//        val cpuInfoProvider = CpuInfoProvider(context)
//        val gpuInfoProvider = GpuInfoProvider(context)
//        val memoryInfoProvider = MemoryInfoProvider(context)
//        val sensorInfoProvider = SensorInfoProvider(context)
        val displayInfoProvider = DisplayInfoProvider(context)
//        val osBuildInfoProvider = OsBuildInfoProvider()

        val hardwareFingerprintProvider = HardwareFingerprintProvider(context)

        val deviceInfoList: ArrayList<DeviceInfo> = ArrayList()
        deviceInfoList.add(DeviceInfo("AndroidID", androidIdProvider.getAndroidId()))
        deviceInfoList.add(DeviceInfo("Media DRM ID", mediaDrmIdProvider.getMediaDrmId()))
        deviceInfoList.add(DeviceInfo("GSF ID", gsfIdProvider.getGsfId() ?: "unknown"))
        deviceInfoList.add(DeviceInfo("Device ID", deviceIdProvider.getDeviceId() ?: "unknown"))
        deviceInfoList.add(DeviceInfo("Google Ad ID", googleAdIdProvider.getGoogleAdId() ?: "unknown"))

//        deviceInfoList.add(DeviceInfo("Battery Health", batteryInfoProvider.getBatteryHealth()))
//        deviceInfoList.add(DeviceInfo("Battery Total Capacity", batteryInfoProvider.getBatteryTotalCapacity()))
//        deviceInfoList.add(DeviceInfo("Battery Level", batteryInfoProvider.getBatteryLevel()))
//
//        deviceInfoList.add(DeviceInfo("Is Camera Present", cameraInfoProvider.isCameraPresent().toString()))
//        val cameraList = cameraInfoProvider.getCameraList()
//        cameraList.forEach {
//            deviceInfoList.add(DeviceInfo("Camera ${it.cameraName}", it.cameraType + "x" + it.cameraOrientation))
//        }
//
//        deviceInfoList.add(DeviceInfo("CPU Cores", cpuInfoProvider.getNumberOfCores().toString()))
//        deviceInfoList.add(DeviceInfo("CPU Abi", cpuInfoProvider.getAbiType()))
//        deviceInfoList.add(DeviceInfo("CPU Info", cpuInfoProvider.getCpuInfo().toString()))
//
//        deviceInfoList.add(DeviceInfo("Gles Version", gpuInfoProvider.getGlesVersion()))
//        deviceInfoList.add(DeviceInfo("Total RAM", memoryInfoProvider.getTotalRaminMB().toString()))
//        deviceInfoList.add(DeviceInfo("Internal Storage", memoryInfoProvider.getInternalStorage().toString()))
//        deviceInfoList.add(DeviceInfo("External Storage", memoryInfoProvider.getExternalStorage().toString()))
//
//        val sensors = sensorInfoProvider.getSensorList()
//        sensors.forEach {
//            deviceInfoList.add(DeviceInfo("Sensor ${it.sensorName}", it.vendorName))
//        }
        val displayInfo = displayInfoProvider.getDisplayInfo()
        deviceInfoList.add(DeviceInfo("Display Info", displayInfo.heightPixel.toString() + "x" + displayInfo.widthPixel + ":" + displayInfo.densityDpi))
//        deviceInfoList.add(DeviceInfo("Manufacturer", osBuildInfoProvider.getManufacturerName()))
//        deviceInfoList.add(DeviceInfo("Model", osBuildInfoProvider.getModelName()))
        deviceInfoList.add(DeviceInfo("Hardware Fingerprint", hardwareFingerprintProvider.getFingerprint()))

        return deviceInfoList
    }
}