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

                    dataPackage.put("Exceptions", ThrowableHandler.get())

                    return@safeException dataPackage.toString().encode()
                }, null)

        }
    }
}