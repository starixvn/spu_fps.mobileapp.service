package com.fps.flutter.core.infoProviders

import android.content.Context
import android.os.Build
import com.fps.flutter.core.DeviceInfoFactory
import com.fps.flutter.core.utils.safeException
import java.io.File

private val FINGERPRINT_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "generic",
    "unknown"
)

private val MODEL_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "google_sdk",
    "droid4x",
    "Emulator",
    "Android SDK built for x86",
    "vmos"
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

private const val CPU_INFO_PATH = "/proc/cpuinfo"
private const val MOUNT_INFO_PATH = "/proc/mounts"

private val CPU_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "hypervisor", "amd", "intel"
)

private val ABI_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "x86"
)

private val MOUNT_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "vboxsf"
)

internal interface IDeviceInfoProvider {
    fun isEmulator(): Boolean
}

internal class DeviceInfoProvider (
    private val context: Context
) : IDeviceInfoProvider {
    private val inputDeviceInfoProvider = DeviceInfoFactory.InputDeviceInfoProvider

    override fun isEmulator(): Boolean {
        return safeException({
            (checkHardware()
                || checkCPU()
                || checkCPUArchitecture()
                || checkMounts()
            )
        }, false)
    }

    private fun checkHardware(): Boolean {
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

        return FINGERPRINT_EMULATOR_CONTAINS_VALUES.any { fingerprint.contains(it.lowercase()) }
                || MODEL_EMULATOR_CONTAINS_VALUES.any { model.contains(it.lowercase()) }
                || MANUFACTURER_EMULATOR_CONTAINS_VALUES.any { manufacturer.contains(it.lowercase()) }
                || HARDWARE_EMULATOR_CONTAINS_VALUES.any { hardware.contains(it.lowercase()) }
                || PRODUCT_EMULATOR_CONTAINS_VALUES.any { product.contains(it.lowercase()) }
                || BOARD_EMULATOR_CONTAINS_VALUES.any { board.contains(it.lowercase()) }
                || BOOTLOADER_EMULATOR_CONTAINS_VALUES.any { bootLoader.contains(it.lowercase()) }
                || SERIAL_EMULATOR_CONTAINS_VALUES.any { serial.contains(it.lowercase()) }
                || (brand.startsWith("generic") && device.startsWith("generic"))
                || inputDeviceInfoProvider.hasKeyboard("memuime")
    }

    private fun checkCPU(): Boolean {
        val cpuInfoContents = File(CPU_INFO_PATH).readText().lowercase()
        return CPU_EMULATOR_CONTAINS_VALUES.any { cpuInfoContents.contains(it.lowercase()) }
    }

    private fun checkCPUArchitecture(): Boolean {
        val abi = Build.SUPPORTED_ABIS.joinToString(";").lowercase()
        return ABI_EMULATOR_CONTAINS_VALUES.any { abi.contains(it.lowercase()) }
    }

    private fun checkMounts(): Boolean {
        val mountInfo = File(MOUNT_INFO_PATH).readText().lowercase()
        return MOUNT_EMULATOR_CONTAINS_VALUES.any { mountInfo.contains(it.lowercase()) }
    }
}