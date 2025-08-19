package com.fps.flutter.core

import android.content.Context
import com.fps.flutter.core.constants.OS_VALUE
import com.fps.flutter.core.constants.TIME_FREEZE_VALUE
import com.fps.flutter.core.utils.DateTimeHelper
import com.fps.flutter.core.utils.StringExtension.encode
import com.fps.flutter.core.utils.StringExtension.hashingMd5
import com.fps.flutter.core.utils.ThrowableHandler
import com.fps.flutter.core.utils.safeException
import org.json.JSONObject
import java.util.Date

class FPSModule {
    companion object {
        private var lastLiveTime: Date? = null

        fun onCheckFPSTrans(
            context: Context,
            subscriberName: String,
            userName: String,
            action: String,
            isOnStart: Boolean = false
        ): String? {

            require(userName.isNotEmpty()) { "userName must be non-empty!"}
            require(subscriberName.isNotEmpty()) { "subscriberName must be non-empty!"}

            return safeException({
                if(isOnStart && lastLiveTime != null) {
                    val freezeTimeInSecond = (Date().time - lastLiveTime!!.time) / 1000

                    if(freezeTimeInSecond > TIME_FREEZE_VALUE) {
                        return@safeException getDataPackage(context, subscriberName, userName, action)
                    }

                    return@safeException null
                }

                return@safeException getDataPackage(context, subscriberName, userName, action)
            }, null)
        }

        fun onResetFPSLastLiveTime() {
            return safeException({
                lastLiveTime = Date()
            }, Unit)
        }

        private fun getDataPackage(
            context: Context,
            subscriberName: String,
            userName: String,
            action: String
        ): String? {
            return safeException({
                    DeviceIdFactory(context)
                    DeviceInfoFactory(context)
                    val dataPackage = JSONObject()

                    dataPackage.put("Account", userName)
                    dataPackage.put("Subscriber", subscriberName)
                    dataPackage.put("LoginTime", DateTimeHelper.getCurrent())

                    dataPackage.put("Action", action)
                    dataPackage.put("ActionResult", "Successfully")

                    dataPackage.put("AndroidCode", DeviceIdFactory.AndroidId?.hashingMd5())
                    dataPackage.put("CustomCode", DeviceIdFactory.UUID?.hashingMd5())
                    dataPackage.put("MediaDRMCode", DeviceIdFactory.MediaDrmId?.hashingMd5())

                    dataPackage.put("IsEmulator", DeviceInfoFactory.IsEmulator)
                    dataPackage.put("ModelName", DeviceInfoFactory.ModelName)
                    dataPackage.put("Manufacturer", DeviceInfoFactory.Manufacturer)
                    dataPackage.put("Brand", DeviceInfoFactory.Brand)
                    dataPackage.put("SdkVersion", DeviceInfoFactory.SdkVersion)
                    dataPackage.put("OS", OS_VALUE)

                    dataPackage.put("Timezone", DeviceInfoFactory.Timezone)
                    dataPackage.put("SystemLanguage", DeviceInfoFactory.SystemLanguage)
                    dataPackage.put("Country", DeviceInfoFactory.Country)

                    dataPackage.put("DeviceDetails", createAdditionalDeviceDetailsFromInfoProviders().toString())

                    dataPackage.put("Exceptions", ThrowableHandler.get())

                    return@safeException dataPackage.toString().encode()
                }, null)

        }

        private fun createAdditionalDeviceDetailsFromInfoProviders(): JSONObject {
            val deviceDetails = JSONObject()

            // Additional OS Build Information (from OsBuildInfoProvider) not already in the package
            deviceDetails.put("Fingerprint", DeviceInfoFactory.Fingerprint)
            deviceDetails.put("Hardware", DeviceInfoFactory.Hardware)
            deviceDetails.put("BuildProduct", DeviceInfoFactory.BuildProduct)
            deviceDetails.put("Board", DeviceInfoFactory.Board)
            deviceDetails.put("BootLoader", DeviceInfoFactory.BootLoader)
            deviceDetails.put("Serial", DeviceInfoFactory.Serial)
            deviceDetails.put("Device", DeviceInfoFactory.Device)

            // Emulator detection results - shows which detection methods returned true
            deviceDetails.put("EmulatorDetectionResults", createEmulatorDetectionResults())

            // Additional runtime information that can be gathered directly
            deviceDetails.put("RuntimeInfo", createRuntimeInfo())

            return deviceDetails
        }

        private fun createRuntimeInfo(): JSONObject {
            return safeException({
                val runtimeInfo = JSONObject()
                
                // Build Information
                runtimeInfo.put("BuildId", android.os.Build.ID)
                runtimeInfo.put("BuildType", android.os.Build.TYPE)
                runtimeInfo.put("BuildTags", android.os.Build.TAGS)
                runtimeInfo.put("BuildHost", android.os.Build.HOST)
                runtimeInfo.put("BuildUser", android.os.Build.USER)
                runtimeInfo.put("BuildTime", android.os.Build.TIME)
                
                // Radio and Architecture Information
                runtimeInfo.put("RadioVersion", android.os.Build.getRadioVersion() ?: "")
                runtimeInfo.put("SupportedAbis", android.os.Build.SUPPORTED_ABIS.joinToString(","))
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    runtimeInfo.put("Supported32BitAbis", android.os.Build.SUPPORTED_32_BIT_ABIS.joinToString(","))
                    runtimeInfo.put("Supported64BitAbis", android.os.Build.SUPPORTED_64_BIT_ABIS.joinToString(","))
                }
                
                // Security patch level
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    runtimeInfo.put("SecurityPatch", android.os.Build.VERSION.SECURITY_PATCH)
                }
                
                // Memory information
                val runtime = Runtime.getRuntime()
                val memoryInfo = JSONObject()
                memoryInfo.put("MaxMemory", runtime.maxMemory())
                memoryInfo.put("TotalMemory", runtime.totalMemory())
                memoryInfo.put("FreeMemory", runtime.freeMemory())
                memoryInfo.put("UsedMemory", runtime.totalMemory() - runtime.freeMemory())
                runtimeInfo.put("MemoryInfo", memoryInfo)
                
                // CPU information
                runtimeInfo.put("ProcessorCount", runtime.availableProcessors())
                
                runtimeInfo
            }, JSONObject())
        }

        private fun createEmulatorDetectionResults(): JSONObject {
            return safeException({
                val detectionResults = DeviceInfoFactory.EmulatorDetectionResults
                val resultJson = JSONObject()
                
                // Add individual detection results
                detectionResults.forEach { (method, result) ->
                    resultJson.put(method, result)
                }
                
                // Add summary information
                val positiveDetections = detectionResults.filter { it.value }.keys.toList()
                resultJson.put("positiveDetections", positiveDetections.joinToString(","))
                resultJson.put("positiveDetectionCount", positiveDetections.size)
                resultJson.put("totalDetectionMethods", detectionResults.size)
                
                resultJson
            }, JSONObject())
        }
    }
}