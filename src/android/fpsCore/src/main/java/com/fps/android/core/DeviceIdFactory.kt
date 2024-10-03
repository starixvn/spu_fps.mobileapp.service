package com.fps.android.core

import android.content.Context
import com.fps.android.core.constants.UNKNOWN_VALUE
import com.fps.android.core.deviceIdProviders.AndroidIdProvider
import com.fps.android.core.deviceIdProviders.MediaDrmIdProvider
import com.fps.android.core.deviceIdProviders.UuidProvider

class DeviceIdFactory(
    private val context: Context
) {

    companion object {
        private var androidId: String = UNKNOWN_VALUE
        private var mediaDrmId: String = UNKNOWN_VALUE
        private var uuid: String = UNKNOWN_VALUE

        val AndroidId: String
            get() = androidId

        val MediaDrmId: String
            get() = mediaDrmId

        val UUID: String
            get() = uuid
    }

    init {
        androidId = AndroidIdProvider(context).getId()
        mediaDrmId = MediaDrmIdProvider().getId()
        uuid = UuidProvider(context).getId()
    }
}