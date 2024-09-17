package fps.mobile.client.infoProviders

import android.content.Context
import android.view.inputmethod.InputMethodInfo
import java.util.Locale


val FINGERPRINT_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "generic",
    "unknown"
)

val MODEL_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "google_sdk",
    "droid4x",
    "Emulator",
    "Android SDK built for x86"
)

val MANUFACTURER_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "Genymotion"
)

val HARDWARE_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "goldfish",
    "ranchu",
    "vbox86",
    "nox",
)

val PRODUCT_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "sdk",
    "google_sdk",
    "sdk_google",
    "sdk_x86",
    "vbox86p",
    "emulator",
    "simulator",
    "nox",
)

val BOARD_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "nox",
)

val BOOTLOADER_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "nox",
)

val SERIAL_EMULATOR_CONTAINS_VALUES: Array<String> = arrayOf(
    "nox",
)

interface IDeviceInfoProvider {
    fun isRootedDevice(): Boolean
    fun isEmulator(): Boolean
}

class DeviceInfoProvider (
    private val context: Context
) : IDeviceInfoProvider {

    private val osBuildInfoProvider: IOsBuildInfoProvider = OsBuildInfoProvider()
    private val inputDeviceInfoProvider: IInputDeviceInfoProvider = InputDeviceInfoProvider(context)

    override fun isRootedDevice(): Boolean {
        return detectTestKeys();
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
}