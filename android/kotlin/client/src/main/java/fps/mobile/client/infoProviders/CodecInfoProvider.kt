package fps.mobile.client.infoProviders

import android.media.MediaCodecList

internal data class MediaCodecInfo(
    val name: String,
    val capabilities: List<String>
)

internal interface ICodecInfoProvider {
    fun getCodecList(): List<MediaCodecInfo>
}

internal class CodecInfoProvider : ICodecInfoProvider {

    override fun getCodecList(): List<MediaCodecInfo> {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)

        return codecList.codecInfos.map {
            MediaCodecInfo(
                it!!.name,
                it.supportedTypes!!.map { type: String? -> type.toString() },
            )
        }
    }

}