package com.fps.android.core.deviceIdProviders

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.fps.android.core.constants.UNKNOWN_VALUE
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

    override fun getId(): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var deviceId: String = sharedPreferences.getString(DEVICE_ID, null) ?: UNKNOWN_VALUE

        if (TextUtils.isEmpty(deviceId)) {
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
                return UNKNOWN_VALUE
            }
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