package fps.mobile.client.deviceIdProviders

import android.media.MediaDrm
import fps.mobile.client.infoProviders.OsBuildInfoProvider
import fps.mobile.client.utils.ByteArrayExtension.toHexString
import fps.mobile.client.utils.StringExtension.hashingMd5
import java.security.MessageDigest
import java.util.UUID

private const val WIDEWINE_UUID_MOST_SIG_BITS = -0x121074568629b532L
private const val WIDEWINE_UUID_LEAST_SIG_BITS = -0x5c37d8232ae2de13L

internal class MediaDrmIdProvider {
    fun getMediaDrmId(): String {
        val widevineUUID = UUID(WIDEWINE_UUID_MOST_SIG_BITS, WIDEWINE_UUID_LEAST_SIG_BITS)
        val wvDrm: MediaDrm?

        wvDrm = MediaDrm(widevineUUID)
        val mivevineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
        wvDrm.close()
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        md.update(mivevineId)

        return md.digest().toHexString()
    }

    fun getMediaDrmIdWithModel(): String {
        val mediaDrmId = getMediaDrmId()
        val modelName = OsBuildInfoProvider().getModelName()

        return "$mediaDrmId+$modelName".hashingMd5()
    }
}