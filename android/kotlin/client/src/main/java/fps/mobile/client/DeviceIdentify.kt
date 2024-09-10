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
import fps.mobile.client.InfoProviders.BatteryInfoProvider
import fps.mobile.client.InfoProviders.CameraInfoProvider
import fps.mobile.client.InfoProviders.CpuInfoProvider
import fps.mobile.client.InfoProviders.GpuInfoProvider
import fps.mobile.client.InfoProviders.MemoryInfoProvider
import fps.mobile.client.InfoProviders.SensorInfoProvider
import java.util.Locale
import java.util.TimeZone
import java.util.UUID


data class DeviceInfo(val name:String, val value: String)
object DeviceIdentify {
    fun getDeviceInfo(context: Context): ArrayList<DeviceInfo> {
        // Device Info
        val deviceModel = Build.MODEL
        val deviceManufacturer = Build.MANUFACTURER
        val androidVersion = Build.VERSION.RELEASE
        val apiLevel = Build.VERSION.SDK_INT
        val deviceName = "$deviceManufacturer $deviceModel"
        val baseOs = Build.VERSION.BASE_OS
        val hardware = Build.HARDWARE
        val board = Build.BOARD
        val timeZone = TimeZone.getDefault().id
        val language = Locale.getDefault().language
        val country = Locale.getDefault().country

        // Display Metrics
        val displayMetrics = getDisplayMetrics(context)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val densityDpi = displayMetrics.densityDpi

        // RAM Info
        val totalRam = getTotalRam(context)
        val userID = getUUID(context)

        val androidIdProvider =  AndroidIdProvider(context.contentResolver)
        val mediaDrmIdProvider = MediaDrmIdProvider(context.contentResolver)
        val gsfIdProvider = GsfIdProvider(context.contentResolver)
        val deviceIdProvider = DeviceIdProvider(context)
        val googleAdIdProvider = GoogleAdIdProvider(context)
        val batteryInfoProvider = BatteryInfoProvider(context)
        val cameraInfoProvider = CameraInfoProvider(context)
        val cpuInfoProvider = CpuInfoProvider(context)
        val gpuInfoProvider = GpuInfoProvider(context)
        val memoryInfoProvider = MemoryInfoProvider(context)
        val sensorInfoProvider = SensorInfoProvider(context)

        val deviceInfoList: ArrayList<DeviceInfo> = ArrayList<DeviceInfo>()
        deviceInfoList.add(DeviceInfo("AndroidID", androidIdProvider.getAndroidId()))
        deviceInfoList.add(DeviceInfo("Media DRM ID", mediaDrmIdProvider.getMediaDrmId()))
        deviceInfoList.add(DeviceInfo("GSF ID", gsfIdProvider.getGsfId() ?: "unknown"))
        deviceInfoList.add(DeviceInfo("Device ID", deviceIdProvider.getDeviceId() ?: "unknown"))
        deviceInfoList.add(DeviceInfo("Google Ad ID", googleAdIdProvider.getGoogleAdId() ?: "unknown"))

        deviceInfoList.add(DeviceInfo("Battery Health", batteryInfoProvider.getBatteryHealth()))
        deviceInfoList.add(DeviceInfo("Battery Total Capacity", batteryInfoProvider.getBatteryTotalCapacity()))
        deviceInfoList.add(DeviceInfo("Battery Level", batteryInfoProvider.getBatteryLevel()))

        deviceInfoList.add(DeviceInfo("Is Camera Present", cameraInfoProvider.isCameraPresent().toString()))
        val cameraList = cameraInfoProvider.getCameraList()
        cameraList.forEach {
            deviceInfoList.add(DeviceInfo("Camera ${it.cameraName}", it.cameraType + "x" + it.cameraOrientation))
        }

        deviceInfoList.add(DeviceInfo("CPU Cores", cpuInfoProvider.getNumberOfCores().toString()))
        deviceInfoList.add(DeviceInfo("CPU Abi", cpuInfoProvider.getAbiType()))
//        deviceInfoList.add(DeviceInfo("CPU Info", cpuInfoProvider.getCpuInfo().toString()))

        deviceInfoList.add(DeviceInfo("Gles Version", gpuInfoProvider.getGlesVersion()))
        deviceInfoList.add(DeviceInfo("Total RAM", memoryInfoProvider.getTotalRam().toString()))
        deviceInfoList.add(DeviceInfo("Internal Storage", memoryInfoProvider.getInternalStorage().toString()))
        deviceInfoList.add(DeviceInfo("External Storage", memoryInfoProvider.getExternalStorage().toString()))

        val sensors = sensorInfoProvider.getSensorList()
        sensors.forEach {
            deviceInfoList.add(DeviceInfo("Sensor ${it.sensorName}", it.vendorName))
        }
//        deviceInfoList.add(DeviceInfo("DeviceName", deviceName))
//        deviceInfoList.add(DeviceInfo("Build Info", getBuildInfo()))
//        deviceInfoList.add(DeviceInfo("Android Version", "$androidVersion (API $apiLevel)"))
//        deviceInfoList.add(DeviceInfo("Base OS", baseOs))
//        deviceInfoList.add(DeviceInfo("Hardware", hardware))
//        deviceInfoList.add(DeviceInfo("Board", board))
//        deviceInfoList.add(DeviceInfo("Timezone", timeZone))
//        deviceInfoList.add(DeviceInfo("Language", "$language ($country)"))
//        deviceInfoList.add(DeviceInfo("Screen Resolution", "${screenWidth}x$screenHeight (Density: $densityDpi dpi)"))
//        deviceInfoList.add(DeviceInfo("Battery Level", "$batteryLevel%"))
//        deviceInfoList.add(DeviceInfo("Total RAM", "$totalRam MB"))
//        deviceInfoList.add(DeviceInfo("Available Sensors", "${sensors.size}"))
//        deviceInfoList.add(DeviceInfo("UUID", "${userID}"))
        //deviceInfoList.add(DeviceInfo("MAC wifi address from file", "${getMacAddressFromFile()}"))

        return deviceInfoList
        //return
    }
    private fun getAndroidId(context: Context): String{
        return DeviceIdentify.getAndroidId(context)
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    private fun getAvailableSensors(context: Context): List<Sensor> {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return sensorManager.getSensorList(Sensor.TYPE_ALL)
    }

    private fun getTotalRam(context: Context): Long {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem / (1024 * 1024)  // Convert bytes to MB
    }

    private fun getBuildInfo(): String {
        return "Manufacturer: ${Build.MANUFACTURER}, Model: ${Build.MODEL}, Device: ${Build.DEVICE}"
    }

    private fun getUUID(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var uuid = sharedPreferences.getString("unique_id", null)
        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("unique_id", uuid).apply()
        }
        return uuid
    }
}