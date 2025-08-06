package com.fps.flutter.core

import android.content.Context
import com.fps.flutter.core.infoProviders.DeviceInfoProvider
import com.fps.flutter.core.infoProviders.IDeviceInfoProvider
import com.fps.flutter.core.infoProviders.IInputDeviceInfoProvider
import com.fps.flutter.core.infoProviders.IOsBuildInfoProvider
import com.fps.flutter.core.infoProviders.ISettingInfoProvider
import com.fps.flutter.core.infoProviders.InputDeviceInfoProvider
import com.fps.flutter.core.infoProviders.OsBuildInfoProvider
import com.fps.flutter.core.infoProviders.SettingInfoProvider

internal  class DeviceInfoFactory(
    context:Context
) {
    companion object {
        private lateinit var osBuildInfoProvider: IOsBuildInfoProvider
        private lateinit var deviceInfoProvider: IDeviceInfoProvider
        private lateinit var inputDeviceInfoProvider: IInputDeviceInfoProvider
        private lateinit var settingInfoProvider: ISettingInfoProvider

        private var modelName: String? = null
        private var fingerprint: String? = null
        private var manufacturer: String? = null
        private var hardware: String? = null
        private var buildProduct: String? = null
        private var board: String? = null
        private var bootLoader: String? = null
        private var serial: String? = null
        private var device: String? = null
        private var brand: String? = null
        private var sdkVersion: String? = null
        private var isEmulator: Boolean? = null

        val ModelName: String
            get() {
                if(modelName == null)
                    modelName = osBuildInfoProvider.getModelName()

                return modelName!!
            }

        val Fingerprint: String
            get() {
                if(fingerprint == null)
                    fingerprint = osBuildInfoProvider.getFingerprint()

                return fingerprint!!
            }

        val Manufacturer: String
            get() {
                if(manufacturer == null)
                    manufacturer = osBuildInfoProvider.getManufacturerName()

                return manufacturer!!
            }

        val Hardware: String
            get() {
                if(hardware == null)
                    hardware = osBuildInfoProvider.getHardware()

                return hardware!!
            }

        val BuildProduct: String
            get() {
                if(buildProduct == null)
                    buildProduct = osBuildInfoProvider.getBuildProduct()

                return buildProduct!!
            }

        val Board: String
            get() {
                if(board == null)
                    board = osBuildInfoProvider.getBoard()

                return board!!
            }

        val BootLoader: String
            get() {
                if(bootLoader == null)
                    bootLoader = osBuildInfoProvider.getBootLoader()

                return bootLoader!!
            }

        val Serial: String
            get() {
                if(serial == null)
                    serial = osBuildInfoProvider.getSerial()

                return serial!!
            }

        val Device: String
            get() {
                if(device == null)
                    device = osBuildInfoProvider.getDevice()

                return device!!
            }

        val Brand: String
            get() {
                if(brand == null)
                    brand = osBuildInfoProvider.getBrand()

                return brand!!
            }

        val SdkVersion: String
            get() {
                if(sdkVersion == null)
                    sdkVersion = osBuildInfoProvider.getSdkVersion()

                return sdkVersion!!
            }

        val IsEmulator: Boolean
            get() {
                if(isEmulator == null)
                    isEmulator = deviceInfoProvider.isEmulator()

                return isEmulator!!
            }

        val EmulatorDetectionResults: Map<String, Boolean>
            get() {
                return deviceInfoProvider.getEmulatorDetectionResults()
            }

        val Timezone: String
            get() {
                return settingInfoProvider.getTimezone()
            }

        val SystemLanguage: String
            get() {
                return settingInfoProvider.getSystemLanguage()
            }

        val Country: String
            get() {
                return settingInfoProvider.getCountry()
            }

        val InputDeviceInfoProvider: IInputDeviceInfoProvider
            get() = inputDeviceInfoProvider
    }

    init {
        osBuildInfoProvider = OsBuildInfoProvider()
        inputDeviceInfoProvider = InputDeviceInfoProvider(context)
        settingInfoProvider = SettingInfoProvider()

        deviceInfoProvider = DeviceInfoProvider(context)
    }
}