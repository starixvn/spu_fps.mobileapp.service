package com.fps.android.core

import android.content.Context
import com.fps.android.core.constants.OS_VALUE
import com.fps.android.core.constants.TIME_FREEZE_VALUE
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.DateTimeHelper
import com.fps.android.core.utils.StringExtension.encode
import com.fps.android.core.utils.StringExtension.hashingMd5
import org.json.JSONObject
import kotlin.time.TimeSource

class FPSModule {
    companion object {

        private var lastLiveTime: TimeSource.Monotonic.ValueTimeMark? = null

        fun onCheckFPSTrans(
            context: Context,
            userName: String,
            subscriberName: String,
            action: String
        ): String? {

            return try {
                if(lastLiveTime != null) {
                    val timeSource = TimeSource.Monotonic
                    val now = timeSource.markNow()
                    val freezeTimeInSecond = now - lastLiveTime!!

                    if(freezeTimeInSecond.inWholeSeconds > TIME_FREEZE_VALUE) {
                        return getDataPackage(context, userName, subscriberName, action)
                    }

                    return null
                }

                return getDataPackage(context, userName, subscriberName, action)
            } catch (_: Exception){
                null
            }
        }

        fun onResetFPSLastLiveTime() {
            try {
                val timeSource = TimeSource.Monotonic
                lastLiveTime = timeSource.markNow()
            } catch (_: Exception){

            }
        }

        private fun getDataPackage(
            context: Context,
            userName: String,
            subscriberName: String,
            action: String
        ): String {

            DeviceIdFactory(context)
            DeviceInfoFactory(context)

            val dataPackage = JSONObject()

            dataPackage.put("LoginName", userName)
            dataPackage.put("SubscriberName", subscriberName)
            dataPackage.put("TransTime", DateTimeHelper.getCurrent())

            dataPackage.put("UserAgent", DeviceInfoFactory.UserAgent)

            dataPackage.put("Action", action)
            dataPackage.put("ActionResult", "Successfully")

            dataPackage.put("FP_MobileAndroidCode", DeviceIdFactory.AndroidId.hashingMd5())
            dataPackage.put("FP_MobileCustomCode", DeviceIdFactory.UUID.hashingMd5())
            dataPackage.put("FP_MobileMediaDRMCode", DeviceIdFactory.MediaDrmId.hashingMd5())
            dataPackage.put("FP_IsMobileApp", 1)

            dataPackage.put("IsEmulator", DeviceInfoFactory.IsEmulator)

            dataPackage.put("ModelName", DeviceInfoFactory.ModelName)
            dataPackage.put("Manufacturer", DeviceInfoFactory.Manufacturer)
            dataPackage.put("Brand", DeviceInfoFactory.Brand)
            dataPackage.put("SdkVersion", DeviceInfoFactory.SdkVersion)
            dataPackage.put("OS", OS_VALUE)

            dataPackage.put("Timezone", DeviceInfoFactory.Timezone)
            dataPackage.put("SystemLanguage", DeviceInfoFactory.SystemLanguage)
            dataPackage.put("Country", DeviceInfoFactory.Country)

            return dataPackage.toString()//.encode()
        }
    }
}