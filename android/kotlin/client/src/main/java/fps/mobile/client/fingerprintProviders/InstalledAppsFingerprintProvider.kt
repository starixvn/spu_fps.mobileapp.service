package fps.mobile.client.fingerprintProviders

import fps.mobile.client.DeviceIdentifyFactory
import fps.mobile.client.utils.StringExtension.hashingMd5

internal class InstalledAppsFingerprintProvider: IFingerprintProvider {

    private val packageInfoProvider = DeviceIdentifyFactory.getPackageInfoProvider()

    override fun getFingerprint(): String {
        val stringBuilder = StringBuilder()

        val applications = packageInfoProvider.getApplications()
        applications
            .sortedBy { it.packageName }
            .forEach { stringBuilder.append(it.packageName).append(it.uid) }

        val systemApplications = packageInfoProvider.getSystemApplications()
        systemApplications.sortedBy { it.packageName }
            .forEach { stringBuilder.append(it.packageName).append(it.uid) }

        return stringBuilder.toString().hashingMd5()
    }

}