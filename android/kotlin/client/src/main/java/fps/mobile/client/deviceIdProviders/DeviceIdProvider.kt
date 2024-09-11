package fps.mobile.client.deviceIdProviders

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.util.UUID

private const val FIXED_ANDROID_ID = "ID_GOES_HERE"
private const val INSTALLATION = "CONSTANT_INSTALLATION_APP_GENERATED_ID"
private const val DEVICE_ID = "UNIQUE_ID"

class DeviceIdProvider(
    private val context: Context
) {
    fun getDeviceId(): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var deviceId: String? = sharedPreferences.getString(DEVICE_ID, null)

        if (TextUtils.isEmpty(deviceId)) {
            val installation = File(context.filesDir, INSTALLATION)
            try {
                if (!installation.exists()) {
                    val deviceUuid = UUID(
                        getDeviceUniqueId(context).hashCode().toLong(), context.applicationContext
                            .packageName.hashCode().toLong()
                    )
                    deviceId = deviceUuid.toString()
                    writeInstallationFile(installation, deviceId)
                    sharedPreferences.edit().putString(DEVICE_ID, deviceId).apply()
                    return deviceId
                }
                deviceId = readInstallationFile(installation)
                sharedPreferences.edit().putString(DEVICE_ID, deviceId).apply()
            } catch (e: Exception) {
                return null
            }
        }
        return deviceId
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    private fun getDeviceUniqueId(context: Context): String {
        var deviceIMEI: String? = null
        val mTelephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            @Suppress("DEPRECATION")
            deviceIMEI = mTelephonyManager.deviceId
        }

        if(deviceIMEI != null && !TextUtils.isEmpty(deviceIMEI))
            return deviceIMEI

        val androidIdProvider = AndroidIdProvider(context.contentResolver)
        val androidId = androidIdProvider.getAndroidId()

        if(!TextUtils.isEmpty(androidId) && !androidId.equals(FIXED_ANDROID_ID, ignoreCase = true))
            return androidId

        @Suppress("DEPRECATION")
        val serialBuildId = Build.SERIAL
        if (!TextUtils.isEmpty(serialBuildId))
            return serialBuildId

        return getRandomId()
    }

    private fun getRandomId(): String { //If nothing is found for this device, generate a random number, although this case is not possible.
        return UUID.randomUUID().toString()
    }

    @Throws(IOException::class)
    private fun readInstallationFile(installation: File): String {
        val f = RandomAccessFile(installation, "r")
        val bytes = ByteArray(f.length().toInt())
        f.readFully(bytes)
        f.close()
        return String(bytes)
    }

    @Throws(IOException::class)
    private fun writeInstallationFile(installation: File, id: String) {
        val out = FileOutputStream(installation)
        out.write(id.toByteArray())
        out.close()
    }
}