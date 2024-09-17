package fps.mobile.client.infoProviders

import android.content.Context
import android.content.pm.PackageManager

data class PackageInfo(
    val packageName: String,
    val uid: String,
)

interface IPackageInfoProvider {
    fun getApplications(): List<PackageInfo>
    fun getSystemApplications(): List<PackageInfo>

    fun isPackageInstalled(name: String): Boolean
}

class PackageInfoProvider (
    context: Context
) : IPackageInfoProvider {

    private val packageManager: PackageManager? = context.packageManager

    override fun getApplications(): List<PackageInfo> {
        return packageManager!!
            .getInstalledApplications(PackageManager.GET_META_DATA)
            .filter {
                !it!!.sourceDir!!.contains("/system/")
            }
            .map {
                PackageInfo(
                    it.packageName,
                    it.uid.toString()
                )
            }
    }

    override fun getSystemApplications(): List<PackageInfo> {
        return packageManager!!
            .getInstalledApplications(PackageManager.GET_META_DATA)
            .filter {
                it!!.sourceDir!!.contains("/system/")
            }
            .map {
                PackageInfo(
                    it.packageName,
                    it.uid.toString()
                )
            }
    }

    override fun isPackageInstalled(name: String): Boolean {
        try{
            packageManager!!.getPackageInfo(name, 0)
            return true
        } catch (_: PackageManager.NameNotFoundException) {

        }
        return false
    }
}