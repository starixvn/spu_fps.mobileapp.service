package fps.mobile.client.fingerprintProviders

import fps.mobile.client.infoProviders.CodecInfoProvider
import fps.mobile.client.infoProviders.ICodecInfoProvider
import fps.mobile.client.infoProviders.IOsBuildInfoProvider
import fps.mobile.client.infoProviders.ISecurityInfoProvider
import fps.mobile.client.infoProviders.OsBuildInfoProvider
import fps.mobile.client.infoProviders.SecurityInfoProvider
import fps.mobile.client.utils.StringExtension.hashingMd5

class OsFingerprintProvider : IFingerprintProvider {

    private val osBuildInfoProvider : IOsBuildInfoProvider = OsBuildInfoProvider()
    private val codecInfoProvider: ICodecInfoProvider = CodecInfoProvider()
    private val securityInfoProvider: ISecurityInfoProvider = SecurityInfoProvider()

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

        val securityProviders = securityInfoProvider.getSecurityProviders()
        securityProviders.forEach {
            stringBuilder.append(it.name).append(it.info)
        }

        return stringBuilder.toString().hashingMd5()
    }

}