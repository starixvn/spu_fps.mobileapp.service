package fps.mobile.client
import android.app.ActivityManager
import android.content.Context
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.util.DisplayMetrics
import fps.mobile.client.DeviceIdProviders.AndroidIdProvider
import fps.mobile.client.DeviceIdProviders.DeviceIdProvider
import fps.mobile.client.DeviceIdProviders.GoogleAdIdProvider
import fps.mobile.client.DeviceIdProviders.GsfIdProvider
import fps.mobile.client.DeviceIdProviders.MediaDrmIdProvider
import fps.mobile.client.FingerprintProviders.HardwareFingerprintProvider
import fps.mobile.client.InfoProviders.BatteryInfoProvider
import fps.mobile.client.InfoProviders.CameraInfoProvider
import fps.mobile.client.InfoProviders.CpuInfoProvider
import fps.mobile.client.InfoProviders.DisplayInfoProvider
import fps.mobile.client.InfoProviders.GpuInfoProvider
import fps.mobile.client.InfoProviders.MemoryInfoProvider
import fps.mobile.client.InfoProviders.SensorInfoProvider
import java.util.Locale
import java.util.TimeZone
import java.util.UUID


data class DeviceInfo(val name:String, val value: String)
object DeviceIdentify {

    fun getDeviceInfo(context: Context): ArrayList<DeviceInfo> {
        val androidIdProvider =  AndroidIdProvider(context.contentResolver)
        val mediaDrmIdProvider = MediaDrmIdProvider(context.contentResolver)
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
        val hardwareFingerprintProvider = HardwareFingerprintProvider(context)

        val deviceInfoList: ArrayList<DeviceInfo> = ArrayList<DeviceInfo>()
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
        deviceInfoList.add(DeviceInfo("Hardware Fingerprint", hardwareFingerprintProvider.getFingerprint()))

        return deviceInfoList
    }
}