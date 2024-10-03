package com.fps.android.core

import android.content.Context
import com.fps.android.core.deviceIdProviders.AndroidIdProvider
import com.fps.android.core.deviceIdProviders.IDeviceIdProvider
import com.fps.android.core.deviceIdProviders.MediaDrmIdProvider
import com.fps.android.core.deviceIdProviders.UuidProvider

class DeviceIdFactory(
    context: Context
) {
    companion object {
        private lateinit var androidIdProvider : IDeviceIdProvider
        private lateinit var mediaDrmIdProvider : IDeviceIdProvider
        private lateinit var uuidProvider : IDeviceIdProvider

        private var androidId: String? = null
        private var mediaDrmId: String? = null
        private var uuid: String? = null

        val AndroidId: String
            get() {
                if(androidId == null)
                    androidId = androidIdProvider.getId()

                return androidId!!
            }

        val MediaDrmId: String
            get() {
                if(mediaDrmId == null)
                    mediaDrmId = mediaDrmIdProvider.getId()

                return mediaDrmId!!
            }

        val UUID: String
            get() {
                if(uuid == null)
                    uuid = uuidProvider.getId()

                return uuid!!
            }
    }

    init {
        androidIdProvider = AndroidIdProvider(context)
        mediaDrmIdProvider = MediaDrmIdProvider()
        uuidProvider = UuidProvider(context)
    }
}