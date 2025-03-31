package com.fps.reactNative.core.deviceIdProviders

import android.media.MediaDrm
import android.os.Build
import com.fps.reactNative.core.DeviceInfoFactory
import com.fps.reactNative.core.utils.ByteArrayExtension.toHexString
import com.fps.reactNative.core.utils.safeException
import java.security.MessageDigest
import java.util.UUID

private const val WIDEWINE_UUID_MOST_SIG_BITS = -0x121074568629b532L
private const val WIDEWINE_UUID_LEAST_SIG_BITS = -0x5c37d8232ae2de13L

internal class MediaDrmIdProvider : IDeviceIdProvider{
    override fun getId(): String? {
        return safeException({
            getMediaDrmIdWithModel()
        }, null)
    }

    private fun getMediaDrmId(): String {
        val widevineUUID = UUID(WIDEWINE_UUID_MOST_SIG_BITS, WIDEWINE_UUID_LEAST_SIG_BITS)
        val wvDrm: MediaDrm?

        wvDrm = MediaDrm(widevineUUID)
        val mivevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            wvDrm.close()
        } else
            wvDrm.release()

        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        md.update(mivevineId)

        return md.digest().toHexString()
    }

    private fun getMediaDrmIdWithModel(): String {
        val mediaDrmId = getMediaDrmId()
        val modelName = DeviceInfoFactory.ModelName

        return "$mediaDrmId+$modelName"
    }
}