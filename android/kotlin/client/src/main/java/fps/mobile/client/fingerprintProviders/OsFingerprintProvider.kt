package fps.mobile.client.fingerprintProviders

import fps.mobile.client.infoProviders.CodecInfoProvider
import fps.mobile.client.infoProviders.ICodecInfoProvider
import fps.mobile.client.infoProviders.IOsBuildInfoProvider
import fps.mobile.client.infoProviders.OsBuildInfoProvider
import fps.mobile.client.utils.StringExtension.hashingMd5

interface IOsFingerprintProvider {
    fun getFingerprint(): String
}

class OsFingerprintProvider : IOsFingerprintProvider {

    private val osBuildInfoProvider : IOsBuildInfoProvider = OsBuildInfoProvider()
    private val codecInfoProvider: ICodecInfoProvider = CodecInfoProvider()

    override fun getFingerprint(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(osBuildInfoProvider.getFingerprint())
        stringBuilder.append(osBuildInfoProvider.getAndroidVersion())
        stringBuilder.append(osBuildInfoProvider.getSdkVersion())
        stringBuilder.append(osBuildInfoProvider.getKernelVersion())

        val codecList = codecInfoProvider.getCodecList()
        codecList.forEach {
            stringBuilder.append(it.name)
            it.capabilities.forEach { capability ->
                stringBuilder.append(capability)
            }
        }

        return stringBuilder.toString().hashingMd5()
    }

}