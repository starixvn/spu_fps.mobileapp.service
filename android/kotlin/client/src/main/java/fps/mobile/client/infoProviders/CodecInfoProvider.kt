package fps.mobile.client.infoProviders

import android.media.MediaCodecList

data class MediaCodecInfo(
    val name: String,
    val capabilities: List<String>
)

interface ICodecInfoProvider {
    fun getCodecList(): List<MediaCodecInfo>
}

class CodecInfoProvider : ICodecInfoProvider {

    override fun getCodecList(): List<MediaCodecInfo> {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)

        return codecList!!.codecInfos.map {
            MediaCodecInfo(
                it!!.name!!,
                it.supportedTypes!!.map { type: String? -> type.toString() },
            )
        }
    }

}