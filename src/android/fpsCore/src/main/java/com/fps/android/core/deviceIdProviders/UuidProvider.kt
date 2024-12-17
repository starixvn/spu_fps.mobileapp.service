package com.fps.android.core.deviceIdProviders

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    override fun getId(): String? {
        return safeException({
            getUUID()
        }, null)
    }

    private fun getUUID(): String {
        var deviceId = getUUIDFromSharedPreferences()

        if (!deviceId.isNullOrEmpty()) {
            return deviceId
        }

        // If not found in SharedPreferences, try installationFile
        deviceId = getUUIDFromInstallationFile()
        if (!deviceId.isNullOrEmpty()) {
            saveUUIDToSharedPreferences(deviceId)
            return deviceId
        }

        // If not found in all, try generate random UUID
        deviceId = getRandomId()

        saveUUIDToSharedPreferences(deviceId)
        saveUUIDToInstallationFile(deviceId)
        return deviceId
    }

    private fun getUUIDFromSharedPreferences(): String? {
        return sharedPreferences.getString(DEVICE_ID, null)
    }

    private fun saveUUIDToSharedPreferences(deviceId: String) {
        sharedPreferences.edit().putString(DEVICE_ID, deviceId).apply()
    }

    private fun getUUIDFromInstallationFile(): String? {
        val installationFile = File(context.filesDir, INSTALLATION)
        return if (installationFile.exists()) {
            try {
                val deviceId = readInstallationFile(installationFile)
                deviceId
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    private fun saveUUIDToInstallationFile(deviceId: String) {
        val installationFile = File(context.filesDir, INSTALLATION)
        writeInstallationFile(installationFile, deviceId)
    }

    private fun getRandomId(): String {
        Log.d("UUID", "granted Random UUID")
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