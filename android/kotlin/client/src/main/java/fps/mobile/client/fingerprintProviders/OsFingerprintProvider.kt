package fps.mobile.client.fingerprintProviders

import fps.mobile.client.DeviceIdentifyFactory
import fps.mobile.client.utils.StringExtension.hashingMd5

internal class OsFingerprintProvider : IFingerprintProvider {

    private val osBuildInfoProvider = DeviceIdentifyFactory.getOsBuildInfoProvider()
    private val codecInfoProvider = DeviceIdentifyFactory.getCodecInfoProvider()
    private val securityInfoProvider= DeviceIdentifyFactory.getSecurityInfoProvider()

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