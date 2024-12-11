package com.fps.android.core.deviceIdProviders

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Environment
import android.text.TextUtils
import androidx.core.content.ContextCompat
import com.fps.android.core.utils.safeException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.util.UUID


private const val INSTALLATION = "CONSTANT_INSTALLATION_APP_GENERATED_ID"
private const val DEVICE_ID = "UNIQUE_ID"

internal class UuidProvider(
    private val context: Context
) : IDeviceIdProvider {

    override fun getId(): String? {
        return safeException({
            getUUID()
        }, null)
    }

    private fun getUUID(): String? {
        var deviceId = ""

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        deviceId = sharedPreferences.getString(DEVICE_ID, null) ?: ""

        if (!TextUtils.isEmpty(deviceId))
            return deviceId

        val installation = File(context.filesDir, INSTALLATION)
        try {
            if (!installation.exists()) {
                val deviceUuid = UUID(
                    getRandomId().hashCode().toLong(), context.applicationContext
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

        return deviceId
    }

    private fun getRandomId(): String {
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