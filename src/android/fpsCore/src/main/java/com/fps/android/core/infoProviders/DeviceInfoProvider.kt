package com.fps.android.core.infoProviders

import android.content.Context
import android.webkit.WebView
import com.fps.android.core.DeviceInfoFactory
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.safeException
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Scanner

private val FINGERPRINT_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "generic",
    "unknown"
)

private val MODEL_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "google_sdk",
    "droid4x",
    "Emulator",
    "Android SDK built for x86"
)

private val MANUFACTURER_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "Genymotion"
)

private val HARDWARE_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "goldfish",
    "ranchu",
    "vbox86",
    "nox",
)

private val PRODUCT_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "sdk",
    "google_sdk",
    "sdk_google",
    "sdk_x86",
    "vbox86p",
    "emulator",
    "simulator",
    "nox",
)

private val BOARD_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "nox",
)

private val BOOTLOADER_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "nox",
)

private val SERIAL_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "nox",
)

internal interface IDeviceInfoProvider {
    fun isEmulator(): Boolean
    fun getUserAgent(): String
}

internal class DeviceInfoProvider (
    private val context: Context
) : IDeviceInfoProvider {
    private val inputDeviceInfoProvider = DeviceInfoFactory.InputDeviceInfoProvider

    override fun isEmulator(): Boolean {
        return safeException({
            val fingerprint = DeviceInfoFactory.Fingerprint.lowercase()
            val model = DeviceInfoFactory.ModelName.lowercase()
            val manufacturer = DeviceInfoFactory.Manufacturer.lowercase()
            val hardware = DeviceInfoFactory.Hardware.lowercase()
            val product = DeviceInfoFactory.BuildProduct.lowercase()
            val board = DeviceInfoFactory.Board.lowercase()
            val bootLoader = DeviceInfoFactory.BootLoader
            val serial = DeviceInfoFactory.Serial
            val device = DeviceInfoFactory.Device
            val brand = DeviceInfoFactory.Brand

             (FINGERPRINT_EMULATOR_CONTAINS_VALUES.any { fingerprint.contains(it.lowercase()) }
                    || MODEL_EMULATOR_CONTAINS_VALUES.any { model.contains(it.lowercase()) }
                    || MANUFACTURER_EMULATOR_CONTAINS_VALUES.any { manufacturer.contains(it.lowercase()) }
                    || HARDWARE_EMULATOR_CONTAINS_VALUES.any { hardware.contains(it.lowercase()) }
                    || PRODUCT_EMULATOR_CONTAINS_VALUES.any { product.contains(it.lowercase()) }
                    || BOARD_EMULATOR_CONTAINS_VALUES.any { board.contains(it.lowercase()) }
                    || BOOTLOADER_EMULATOR_CONTAINS_VALUES.any { bootLoader.contains(it.lowercase()) }
                    || SERIAL_EMULATOR_CONTAINS_VALUES.any { serial.contains(it.lowercase()) }
                    || (brand.startsWith("generic") && device.startsWith("generic"))
                    || inputDeviceInfoProvider.hasKeyboard("memuime"))
        }, false)
    }

    override fun getUserAgent(): String {
        return safeException({
            WebView(context).settings.userAgentString
        }, UNKNOWN_VALUE)
    }
}