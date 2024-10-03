package com.fps.android.core

import android.content.Context
import com.fps.android.core.constants.TIME_FREEZE_VALUE
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.utils.DateTimeHelper
import com.fps.android.core.utils.StringExtension.hashingMd5
import org.json.JSONObject
import kotlin.time.TimeSource

class FPSModule {
    companion object {
        private var lastLiveTime: TimeSource.Monotonic.ValueTimeMark? = null
        fun login(
            context: Context,
            userName: String,
            subscriberName: String,
            ipV4: String = UNKNOWN_VALUE
        ): String? {

            return try {
                getDataPackage(context, userName, subscriberName, ipV4)
            } catch (ex: Exception){
                null
            }

        }

        fun onStop() {
            try {
                val timeSource = TimeSource.Monotonic
                lastLiveTime = timeSource.markNow()
            } catch (_: Exception){

            }
        }

        fun onStartOrRestart(
            context: Context,
            userName: String,
            subscriberName: String,
            ipV4: String = UNKNOWN_VALUE
        ): String? {

            return try {
                if(lastLiveTime != null) {
                    val timeSource = TimeSource.Monotonic
                    val now = timeSource.markNow()
                    val freezeTimeInSecond = now - lastLiveTime!!

                    if(freezeTimeInSecond.inWholeSeconds > TIME_FREEZE_VALUE) {
                        return getDataPackage(context, userName, subscriberName, ipV4)
                    }

                }
                null
            } catch (_: Exception){
                null
            }

        }

        private fun getDataPackage(context: Context, userName: String, subscriberName: String, ipV4: String): String {
            DeviceIdFactory(context)

            val dataPackage = JSONObject()
            dataPackage.put("LoginName", userName)
            dataPackage.put("SubscriberName", subscriberName)


            dataPackage.put("TransTime", DateTimeHelper.getCurrent())

            dataPackage.put("IP", ipV4)
            dataPackage.put("IPID", ipV4.hashingMd5())

            dataPackage.put("Action", "login")
            dataPackage.put("ActionResult", "Successfully")
            dataPackage.put("FP_MobileAndroidCode", DeviceIdFactory.AndroidId.hashingMd5())
            dataPackage.put("FP_MobileCustomCode", DeviceIdFactory.UUID.hashingMd5())
            dataPackage.put("FP_MobileMediaDRMCode", DeviceIdFactory.MediaDrmId.hashingMd5())
            dataPackage.put("FP_IsMobileApp", 1)

            return dataPackage.toString()
        }
    }
}