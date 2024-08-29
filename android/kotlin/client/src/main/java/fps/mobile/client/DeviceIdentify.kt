package fps.mobile.client
import android.app.ActivityManager
import android.bluetooth.BluetoothClass.Device
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import java.util.Locale
import java.util.TimeZone

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

        // Battery Level
        val batteryLevel = getBatteryLevel(context)

        // Sensors
        val sensors = getAvailableSensors(context)

        // RAM Info
        val totalRam = getTotalRam(context)

        val androidID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);

        val deviceInfoList: ArrayList<DeviceInfo> = ArrayList<DeviceInfo>()

        deviceInfoList.add(DeviceInfo("AndroidID", androidID))
        deviceInfoList.add(DeviceInfo("DeviceName", deviceName))
        deviceInfoList.add(DeviceInfo("Build Info", getBuildInfo()))
        deviceInfoList.add(DeviceInfo("Android Version", "$androidVersion (API $apiLevel)"))
        deviceInfoList.add(DeviceInfo("Base OS", baseOs))
        deviceInfoList.add(DeviceInfo("Hardware", hardware))
        deviceInfoList.add(DeviceInfo("Board", board))
        deviceInfoList.add(DeviceInfo("Timezone", timeZone))
        deviceInfoList.add(DeviceInfo("Language", "$language ($country)"))
        deviceInfoList.add(DeviceInfo("Screen Resolution", "${screenWidth}x$screenHeight (Density: $densityDpi dpi)"))
        deviceInfoList.add(DeviceInfo("Battery Level", "$batteryLevel%"))
        deviceInfoList.add(DeviceInfo("Total RAM", "$totalRam MB"))
        deviceInfoList.add(DeviceInfo("Available Sensors", "${sensors.size}"))

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

    private fun getBatteryLevel(context: Context): Int {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)
        val level = batteryStatus?.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(android.os.BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level != -1 && scale != -1) {
            (level.toFloat() / scale.toFloat() * 100).toInt()
        } else {
            -1
        }
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
}