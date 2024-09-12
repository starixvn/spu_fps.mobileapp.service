package fps.mobile.client.fingerprintProviders

import android.content.Context
import fps.mobile.client.infoProviders.IPackageInfoProvider
import fps.mobile.client.infoProviders.PackageInfoProvider
import fps.mobile.client.utils.StringExtension.hashingMd5

class InstalledAppsFingerprintProvider(
    context: Context
): IFingerprintProvider {

    private val packageInfoProvider: IPackageInfoProvider = PackageInfoProvider(context)

    override fun getFingerprint(): String {
        val stringBuilder = StringBuilder()

        val applications = packageInfoProvider.getApplications()
        applications
            .sortedBy { it.packageName }
            .forEach { stringBuilder.append(it.packageName) }

        val systemApplications = packageInfoProvider.getSystemApplications()
        systemApplications.sortedBy { it.packageName }
            .forEach { stringBuilder.append(it.packageName) }

        return stringBuilder.toString().hashingMd5()
    }

}