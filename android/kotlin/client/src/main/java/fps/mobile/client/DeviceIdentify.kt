package fps.mobile.client

import android.bluetooth.BluetoothClass.Device
import android.content.Context
import android.util.Log
import fps.mobile.client.fingerprintProviders.DeviceStateFingerprintProvider
import fps.mobile.client.fingerprintProviders.HardwareFingerprintProvider
import fps.mobile.client.fingerprintProviders.InstalledAppsFingerprintProvider
import fps.mobile.client.fingerprintProviders.OsFingerprintProvider
import fps.mobile.client.infoProviders.DeviceInfoProvider
import fps.mobile.client.infoProviders.SensorInfoProvider
import kotlin.time.TimeSource


data class DeviceInfo(val name: String, val value: String)

class DeviceIdentify(context: Context) {

    companion object {
        fun getFingerprints(): ArrayList<DeviceInfo> {
            val timeSource = TimeSource.Monotonic
            val start = timeSource.markNow()

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

            val end = timeSource.markNow()
            Log.d("getFingerprints", "${end - start} ms")

            return deviceInfoList
        }

        fun getDeviceIds(): ArrayList<DeviceInfo> {
            val timeSource = TimeSource.Monotonic
            val start = timeSource.markNow()
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

            val end = timeSource.markNow()
            Log.d("Device Id", "${end - start} ms")
            return deviceInfoList
        }

        fun getDeviceInfoList(): ArrayList<DeviceInfo> {
            val timeSource = TimeSource.Monotonic
            val start = timeSource.markNow()
            val batteryInfoProvider = DeviceIdentifyFactory.getBatteryInfoProvider()
            val cameraInfoProvider = DeviceIdentifyFactory.getCameraInfoProvider()
            val codecInfoProvider = DeviceIdentifyFactory.getCodecInfoProvider()
            val cpuInfoProvider = DeviceIdentifyFactory.getCpuInfoProvider()
            val deviceInfoProvider = DeviceIdentifyFactory.getDeviceInfoProvider()
            val displayInfoProvider = DeviceIdentifyFactory.getDisplayInfoProvider()
            val gpuInfoProvider = DeviceIdentifyFactory.getGpuInfoProvider()
            val memoryInfoProvider = DeviceIdentifyFactory.getMemoryInfoProvider()
            val osBuildInfoProvider = DeviceIdentifyFactory.getOsBuildInfoProvider()
            val packageInfoProvider = DeviceIdentifyFactory.getPackageInfoProvider()
            val securityInfoProvider = DeviceIdentifyFactory.getSecurityInfoProvider()
            val sensorInfoProvider = DeviceIdentifyFactory.getSensorInfoProvider()
            val settingInfoProvider = DeviceIdentifyFactory.getSettingInfoProvider()

            val deviceInfoList: ArrayList<DeviceInfo> = ArrayList()

            deviceInfoList.add(DeviceInfo("Battery | Health", batteryInfoProvider.getBatteryHealth() ?: "unknown"))
            deviceInfoList.add(DeviceInfo("Battery | Total Capacity", batteryInfoProvider.getBatteryTotalCapacity()))
            deviceInfoList.add(DeviceInfo("Battery | Level", batteryInfoProvider.getBatteryLevel()))

            val cameraList = cameraInfoProvider.getCameraList()
            cameraList.forEach{
                deviceInfoList.add(DeviceInfo("Camera | ${it.cameraName}", "${it.cameraType} x ${it.cameraOrientation}"))
            }

            val codecList = codecInfoProvider.getCodecList()
            codecList.forEach {
                deviceInfoList.add(DeviceInfo("Codec | ${it.name}", it.capabilities.joinToString()))
            }

            val cpuInfo = cpuInfoProvider.getCpuInfo()
            deviceInfoList.add(DeviceInfo("CPU | Common Info", cpuInfo.commonInfo.toString()))
            deviceInfoList.add(DeviceInfo("CPU | per Processor Info", cpuInfo.perProcessorInfo.toString()))
            deviceInfoList.add(DeviceInfo("CPU | Abi Type", cpuInfoProvider.getAbiType()))
            deviceInfoList.add(DeviceInfo("CPU | Number Of Cores", cpuInfoProvider.getNumberOfCores().toString()))

            deviceInfoList.add(DeviceInfo("Device Info | Is Rooted Device", deviceInfoProvider.isRootedDevice().toString()))
            deviceInfoList.add(DeviceInfo("Device Info | Is Emulator", deviceInfoProvider.isEmulator().toString()))

            val displayInfo = displayInfoProvider.getDisplayInfo()
            deviceInfoList.add(DeviceInfo("Display Info | Width", displayInfo.widthPixel.toString()))
            deviceInfoList.add(DeviceInfo("Display Info | Height", displayInfo.heightPixel.toString()))
            deviceInfoList.add(DeviceInfo("Display Info | Density Dpi", displayInfo.densityDpi.toString()))

            deviceInfoList.add(DeviceInfo("GPU | Gles Version", gpuInfoProvider.getGlesVersion()))

            deviceInfoList.add(DeviceInfo("Memory | Total Ram in MB", memoryInfoProvider.getTotalRamInMB().toString()))
            deviceInfoList.add(DeviceInfo("Memory | External Storage", memoryInfoProvider.getExternalStorage().toString()))
            deviceInfoList.add(DeviceInfo("Memory | Internal Storage", memoryInfoProvider.getInternalStorage().toString()))

            deviceInfoList.add(DeviceInfo("OSBuild | Device", osBuildInfoProvider.getDevice()))
            deviceInfoList.add(DeviceInfo("OSBuild | Fingerprint", osBuildInfoProvider.getFingerprint()))
            deviceInfoList.add(DeviceInfo("OSBuild | Build Tags", osBuildInfoProvider.getBuildTags()))
            deviceInfoList.add(DeviceInfo("OSBuild | Build Product", osBuildInfoProvider.getBuildProduct()))
            deviceInfoList.add(DeviceInfo("OSBuild | Serial", osBuildInfoProvider.getSerial()))
            deviceInfoList.add(DeviceInfo("OSBuild | Android Version", osBuildInfoProvider.getAndroidVersion()))
            deviceInfoList.add(DeviceInfo("OSBuild | Board", osBuildInfoProvider.getBoard()))
            deviceInfoList.add(DeviceInfo("OSBuild | Boot Loader", osBuildInfoProvider.getBootLoader()))
            deviceInfoList.add(DeviceInfo("OSBuild | Brand", osBuildInfoProvider.getBrand()))
            deviceInfoList.add(DeviceInfo("OSBuild | Display", osBuildInfoProvider.getDisplay()))
            deviceInfoList.add(DeviceInfo("OSBuild | Hardware", osBuildInfoProvider.getHardware()))
            deviceInfoList.add(DeviceInfo("OSBuild | Kernel Version", osBuildInfoProvider.getKernelVersion()))
            deviceInfoList.add(DeviceInfo("OSBuild | Manufacturer", osBuildInfoProvider.getManufacturerName()))
            deviceInfoList.add(DeviceInfo("OSBuild | Model", osBuildInfoProvider.getModelName()))
            deviceInfoList.add(DeviceInfo("OSBuild | Sdk Version", osBuildInfoProvider.getSdkVersion()))

            val installedApps = packageInfoProvider.getApplications()
            installedApps.forEach{
                deviceInfoList.add(DeviceInfo("App | ${it.packageName}", it.uid))
            }

            val systemPackages = packageInfoProvider.getSystemApplications()
            systemPackages.forEach {
                deviceInfoList.add(DeviceInfo("System Package | ${it.packageName}", it.uid))
            }

            val securityProviders = securityInfoProvider.getSecurityProviders()
            securityProviders.forEach {
                deviceInfoList.add(DeviceInfo("Security | ${it.name}", it.info))
            }

            val sensors = sensorInfoProvider.getSensorList()
            sensors.forEach {
                deviceInfoList.add(DeviceInfo("Sensor | ${it.sensorName}", it.vendorName))
            }

            deviceInfoList.add(DeviceInfo("Setting | Timezone", settingInfoProvider.getTimezone()))
            deviceInfoList.add(DeviceInfo("Setting | Font Scale", settingInfoProvider.getFontScale()))
            deviceInfoList.add(DeviceInfo("Setting | Date Format", settingInfoProvider.getDateFormat()))
            deviceInfoList.add(DeviceInfo("Setting | Brightness Mode", settingInfoProvider.getBrightnessMode()))
            deviceInfoList.add(DeviceInfo("Setting | System Language", settingInfoProvider.getSystemLanguage()))
            deviceInfoList.add(DeviceInfo("Setting | Wifi Sleep Policy", settingInfoProvider.getWifiSleepPolicy()))
            deviceInfoList.add(DeviceInfo("Setting | Screen Off Timeout", settingInfoProvider.getScreenOffTimeout().toString()))
            val ringtones = settingInfoProvider.getRingtoneList()
            ringtones.forEach {
                deviceInfoList.add(DeviceInfo("Setting | Ringtone ${it.title}", it.uri))
            }

            val currentRingtone = settingInfoProvider.getRingtoneSource()
            deviceInfoList.add(DeviceInfo("Setting | Ringtone ${currentRingtone.title}", currentRingtone.uri))

            val end = timeSource.markNow()
            Log.d("getDeviceInfoList", "${end - start} ms")
            return deviceInfoList
        }
    }

    init {
        DeviceIdentifyFactory(context)
    }
}