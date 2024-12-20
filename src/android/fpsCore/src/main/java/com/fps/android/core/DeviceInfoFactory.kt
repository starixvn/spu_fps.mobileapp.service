package com.fps.android.core

import android.content.Context
import com.fps.android.core.infoProviders.DeviceInfoProvider
import com.fps.android.core.infoProviders.IDeviceInfoProvider
import com.fps.android.core.infoProviders.IInputDeviceInfoProvider
import com.fps.android.core.infoProviders.IOsBuildInfoProvider
import com.fps.android.core.infoProviders.ISettingInfoProvider
import com.fps.android.core.infoProviders.InputDeviceInfoProvider
import com.fps.android.core.infoProviders.OsBuildInfoProvider
import com.fps.android.core.infoProviders.SettingInfoProvider

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
        private var timezone: String? = null
        private var systemLanguage: String? = null
        private var country: String? = null

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

        val Timezone: String
            get() {
                if(timezone == null)
                    timezone = settingInfoProvider.getTimezone()

                return timezone!!
            }

        val SystemLanguage: String
            get() {
                if(systemLanguage == null)
                    systemLanguage = settingInfoProvider.getSystemLanguage()

                return systemLanguage!!
            }

        val Country: String
            get() {
                if(country == null)
                    country = settingInfoProvider.getCountry()

                return country!!
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