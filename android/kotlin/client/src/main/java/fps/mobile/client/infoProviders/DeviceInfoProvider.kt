package fps.mobile.client.infoProviders

import android.content.Context
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

private val KNOWN_ROOT_APPS_PACKAGES: Array<String> = arrayOf(
    "com.noshufou.android.su",
    "com.noshufou.android.su.elite",
    "eu.chainfire.supersu",
    "com.koushikdutta.superuser",
    "com.thirdparty.superuser",
    "com.yellowes.su",
    "com.zachspong.temprootremovejb",
    "com.ramdroid.appquarantine",
    "eu.chainfire.supersu"
)

private val KNOWN_DANGEROUS_APPS_PACKAGES: Array<String> = arrayOf(
    "com.koushikdutta.rommanager",
    "com.koushikdutta.rommanager.license",
    "com.dimonvideo.luckypatcher",
    "com.chelpus.lackypatch",
    "com.ramdroid.appquarantine",
    "com.ramdroid.appquarantinepro"
)

private val KNOWN_ROOT_CLOAKING_PACKAGES: Array<String> = arrayOf(
    "com.devadvance.rootcloak",
    "com.devadvance.rootcloakplus",
    "de.robv.android.xposed.installer",
    "com.saurik.substrate",
    "com.zachspong.temprootremovejb",
    "com.amphoras.hidemyroot",
    "com.amphoras.hidemyrootadfree",
    "com.formyhm.hiderootPremium",
    "com.formyhm.hideroot"
)

private val SU_PATHS: Array<String> = arrayOf(
    "/data/local/",
    "/data/local/bin/",
    "/data/local/xbin/",
    "/sbin/",
    "/su/bin/",
    "/system/bin/",
    "/system/bin/.ext/",
    "/system/bin/failsafe/",
    "/system/sd/xbin/",
    "/system/usr/we-need-root/",
    "/system/xbin/"
)

private val PATHS_SHOULD_NOT_BE_WRITEABLE = arrayOf(
    "/system",
    "/system/bin",
    "/system/sbin",
    "/system/xbin",
    "/vendor/bin",
    //"/sys",
    "/sbin",
    "/etc",
    //"/proc",
    //"/dev"
)

interface IDeviceInfoProvider {
    fun isRootedDevice(): Boolean
    fun isEmulator(): Boolean
}

class DeviceInfoProvider (
    context: Context
) : IDeviceInfoProvider {

    private val osBuildInfoProvider: IOsBuildInfoProvider = OsBuildInfoProvider()
    private val inputDeviceInfoProvider: IInputDeviceInfoProvider = InputDeviceInfoProvider(context)
    private val packageInfoProvider: IPackageInfoProvider = PackageInfoProvider(context)

    override fun isRootedDevice(): Boolean {
        return detectTestKeys()
                || detectRootManagementApps()
                || detectPotentiallyDangerousApps()
                || detectRootCloakingApps()
                || checkForBinary("busybox")
                || checkForBinary("su")
                || detectSuExist()
                || checkForRWPaths()
                || detectDangerousProps()

    }

    override fun isEmulator(): Boolean {
        val fingerprint = osBuildInfoProvider.getFingerprint().lowercase()
        val model = osBuildInfoProvider.getModelName().lowercase()
        val manufacturer = osBuildInfoProvider.getManufacturerName().lowercase()
        val hardware = osBuildInfoProvider.getHardware().lowercase()
        val product = osBuildInfoProvider.getBuildProduct().lowercase()
        val board = osBuildInfoProvider.getBoard()
        val bootLoader = osBuildInfoProvider.getBootLoader()
        val serial = osBuildInfoProvider.getSerial()
        val device = osBuildInfoProvider.getDevice()
        val brand = osBuildInfoProvider.getBrand()

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

    private fun detectTestKeys(): Boolean {
        val buildTags = osBuildInfoProvider.getBuildTags()
        val fingerprint = osBuildInfoProvider.getFingerprint()
        val product = osBuildInfoProvider.getBuildProduct()
        val hardware = osBuildInfoProvider.getHardware()
        val display = osBuildInfoProvider.getDisplay()

        return (buildTags.contains("test-keys")
                || fingerprint.contains("genric.*test-keys")
                || product.contains("generic")
                || product.contains("sdk")
                || hardware.contains("goldfish")
                || display.contains(".*test-keys"))
    }

    private fun detectRootManagementApps(): Boolean {
        return KNOWN_ROOT_APPS_PACKAGES.any {
            return packageInfoProvider.isPackageInstalled(it)
        }
    }

    private fun detectPotentiallyDangerousApps(): Boolean {
        return KNOWN_DANGEROUS_APPS_PACKAGES.any {
            return packageInfoProvider.isPackageInstalled(it)
        }
    }

    private fun detectRootCloakingApps(): Boolean {
        return KNOWN_ROOT_CLOAKING_PACKAGES.any {
            return packageInfoProvider.isPackageInstalled(it)
        }
    }

    private fun checkForBinary(fileName: String): Boolean {
        return SU_PATHS.any {
            val completePath = it + fileName
            val file = File(completePath)

            return file.exists()
        }
    }

    private fun detectSuExist(): Boolean {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(arrayOf("which", "su"))
            val buffer = BufferedReader(InputStreamReader(process.inputStream))

            return buffer.readLine() != null
        } catch (_: Throwable) {
            return false
        } finally {
            process?.destroy()
        }
    }

    private fun checkForRWPaths(): Boolean {
        val lines = reader("mount")
        for(line in lines) {
            val args = line.split(" ")

            if(args.size < 4)
                continue

            val mountPoint = args[1]
            val mountOptions = args[3]

            val isExist = PATHS_SHOULD_NOT_BE_WRITEABLE.any {
                if(mountPoint.equals(it, ignoreCase = true)){
                    for (option in mountOptions.split(",")) {
                        if(option.equals("rw", ignoreCase = true))
                            return true
                    }
                }

                return false
            }

            if(isExist)
                return true
        }

        return false
    }

    private fun detectDangerousProps(): Boolean {
        val dangerousProps: MutableMap<String, String> = HashMap()
        dangerousProps["ro.debuggable"] = "1"
        dangerousProps["ro.secure"] = "0"

        var result = false

        val lines: Array<String> = reader("getprop")
        for (line in lines) {
            for (key in dangerousProps.keys) {
                if (line.contains(key)) {
                    var badValue = dangerousProps[key]
                    badValue = "[$badValue]"
                    if (line.contains(badValue)) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    private fun reader(command: String): Array<String> {
        var inputstream: InputStream? = null
        try {
            inputstream = Runtime.getRuntime().exec(command).inputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var propval = ""
        try {
            propval = Scanner(inputstream).useDelimiter("\\A").next()
        } catch (_: NoSuchElementException) {

        }

        return propval.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }
}