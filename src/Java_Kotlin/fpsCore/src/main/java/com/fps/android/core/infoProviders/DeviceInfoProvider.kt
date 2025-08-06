package com.fps.android.core.infoProviders

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import com.fps.android.core.DeviceInfoFactory
import com.fps.android.core.utils.safeException
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
    "Genymotion",
    "Andy",
    "nox"
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

// Additional detection arrays for enhanced emulator detection
private val DEVICE_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "generic",
    "emulator",
    "simulator"
)

private val BRAND_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "generic",
    "android",
    "genymotion"
)

private val RADIO_VERSION_EMULATOR_VALUES: Array<String> = arrayOf(
    "1.0.0.0"
)

// Enhanced known emulator characteristics
private val KNOWN_PIPES: Array<String> = arrayOf(
    "/dev/socket/qemud",
    "/dev/qemu_pipe"
)

private val KNOWN_FILES: Array<String> = arrayOf(
    "/system/lib/libc_malloc_debug_qemu.so",
    "/sys/qemu_trace",
    "/system/bin/qemu-props",
    "/dev/socket/genyd",
    "/dev/socket/baseband_genyd"
)

private val KNOWN_GENY_FILES: Array<String> = arrayOf(
    "/dev/socket/genyd",
    "/dev/socket/baseband_genyd"
)

private val KNOWN_ANDY_FILES: Array<String> = arrayOf(
    "fstab.andy",
    "ueventd.andy.rc"
)

private val KNOWN_NOX_FILES: Array<String> = arrayOf(
    "fstab.nox",
    "init.nox.rc",
    "ueventd.nox.rc"
)

internal interface IDeviceInfoProvider {
    fun isEmulator(): Boolean
    fun getEmulatorDetectionResults(): Map<String, Boolean>
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
                || checkFiles()
                || checkTelephony()
                || checkOperatorName()
                || checkDeviceId()
                || checkQEmuDrivers()
                || checkQEmuProps()
                || checkPipes()
                || checkGenyMotion()
            )
        }, false)
    }

    override fun getEmulatorDetectionResults(): Map<String, Boolean> {
        return safeException({
            mapOf(
                "hardware" to checkHardware(),
                "cpu" to checkCPU(),
                "cpuArchitecture" to checkCPUArchitecture(),
                "mounts" to checkMounts(),
                "files" to checkFiles(),
                "telephony" to checkTelephony(),
                "operatorName" to checkOperatorName(),
                "deviceId" to checkDeviceId(),
                "qemuDrivers" to checkQEmuDrivers(),
                "qemuProps" to checkQEmuProps(),
                "pipes" to checkPipes(),
                "genyMotion" to checkGenyMotion()
            )
        }, emptyMap())
    }

    private fun checkHardware(): Boolean {
        val fingerprint = DeviceInfoFactory.Fingerprint.lowercase()
        val model = DeviceInfoFactory.ModelName.lowercase()
        val manufacturer = DeviceInfoFactory.Manufacturer.lowercase()
        val hardware = DeviceInfoFactory.Hardware.lowercase()
        val product = DeviceInfoFactory.BuildProduct.lowercase()
        val board = DeviceInfoFactory.Board.lowercase()
        val bootLoader = DeviceInfoFactory.BootLoader.lowercase()
        val serial = DeviceInfoFactory.Serial.lowercase()
        val device = DeviceInfoFactory.Device.lowercase()
        val brand = DeviceInfoFactory.Brand.lowercase()
        val radioVersion = Build.getRadioVersion().lowercase()

        return FINGERPRINT_EMULATOR_CONTAINS_VALUES.any { fingerprint.contains(it.lowercase()) }
                || MODEL_EMULATOR_CONTAINS_VALUES.any { model.contains(it.lowercase()) }
                || MANUFACTURER_EMULATOR_CONTAINS_VALUES.any { manufacturer.contains(it.lowercase()) }
                || HARDWARE_EMULATOR_CONTAINS_VALUES.any { hardware.contains(it.lowercase()) }
                || PRODUCT_EMULATOR_CONTAINS_VALUES.any { product.contains(it.lowercase()) }
                || BOARD_EMULATOR_CONTAINS_VALUES.any { board.contains(it.lowercase()) }
                || BOOTLOADER_EMULATOR_CONTAINS_VALUES.any { bootLoader.contains(it.lowercase()) }
                || SERIAL_EMULATOR_CONTAINS_VALUES.any { serial.contains(it.lowercase()) }
                || DEVICE_EMULATOR_CONTAINS_VALUES.any { device.contains(it.lowercase()) }
                || BRAND_EMULATOR_CONTAINS_VALUES.any { brand.contains(it.lowercase()) }
                || RADIO_VERSION_EMULATOR_VALUES.any { radioVersion.contains(it.lowercase()) }
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

    private fun checkFiles(): Boolean {
        return KNOWN_FILES.any { File(it).exists() }
                || KNOWN_GENY_FILES.any { File(it).exists() }
                || KNOWN_ANDY_FILES.any { File(it).exists() }
                || KNOWN_NOX_FILES.any { File(it).exists() }
    }

    private fun checkTelephony(): Boolean {
        return safeException({
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            telephonyManager?.let { tm ->
                val networkOperator = tm.networkOperatorName?.lowercase() ?: ""
                val simOperator = tm.simOperatorName?.lowercase() ?: ""
                
                // Emulators often have specific operator names or empty values
                networkOperator.contains("android") || 
                simOperator.contains("android") ||
                (networkOperator.isEmpty() && simOperator.isEmpty())
            } ?: false
        }, false)
    }

    private fun checkOperatorName(): Boolean {
        return safeException({
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
            telephonyManager?.let { tm ->
                val operatorName = tm.networkOperatorName?.lowercase() ?: ""
                operatorName == "android" || operatorName.isEmpty()
            } ?: false
        }, false)
    }

    private fun checkDeviceId(): Boolean {
        return safeException({
            val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            // Common emulator Android IDs
            androidId == null || 
            androidId == "9774d56d682e549c" || 
            androidId == "0000000000000000" ||
            androidId.length < 8
        }, false)
    }

    private fun checkQEmuDrivers(): Boolean {
        return safeException({
            val driverFile = File("/proc/tty/drivers")
            if (driverFile.exists()) {
                val content = driverFile.readText().lowercase()
                content.contains("goldfish")
            } else false
        }, false)
    }

    private fun checkQEmuProps(): Boolean {
        return safeException({
            val qemuProps = arrayOf(
                "init.svc.qemud",
                "init.svc.qemu-props",
                "qemu.hw.mainkeys",
                "qemu.sf.fake_camera",
                "qemu.sf.lcd_density",
                "ro.bootloader",
                "ro.bootmode",
                "ro.hardware",
                "ro.kernel.android.qemud",
                "ro.kernel.qemu.gles",
                "ro.kernel.qemu",
                "ro.product.device",
                "ro.product.model",
                "ro.product.name",
                "ro.serialno"
            )
            
            // Check system properties for QEMU indicators
            qemuProps.any { prop ->
                val value = System.getProperty(prop) ?: ""
                value.lowercase().contains("qemu") || value.lowercase().contains("emulator") || value.lowercase().contains("goldfish")
            }
        }, false)
    }

    private fun checkPipes(): Boolean {
        return KNOWN_PIPES.any { File(it).exists() }
    }

    private fun checkGenyMotion(): Boolean {
        return safeException({
            val genymotionFiles = arrayOf(
                "/dev/socket/genyd",
                "/dev/socket/baseband_genyd"
            )
            genymotionFiles.any { File(it).exists() } ||
            Build.PRODUCT.contains("vbox86p") ||
            Build.MANUFACTURER.contains("Genymotion") ||
            Build.BRAND.contains("generic")
        }, false)
    }
}