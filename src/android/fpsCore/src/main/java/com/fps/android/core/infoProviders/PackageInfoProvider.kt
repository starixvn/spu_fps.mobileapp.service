package com.fps.android.core.infoProviders

import android.content.Context
import android.content.pm.PackageManager

internal data class PackageInfo(
    val packageName: String,
    val uid: String,
)

internal interface IPackageInfoProvider {
    fun getApplications(): List<PackageInfo>
    fun getSystemApplications(): List<PackageInfo>

    fun isPackageInstalled(name: String): Boolean
}

internal class PackageInfoProvider (
    context: Context
) : IPackageInfoProvider {

    private val packageManager: PackageManager? = context.packageManager
    private var applications: List<PackageInfo>? = null
    private var systemApplications: List<PackageInfo>? = null

    override fun getApplications(): List<PackageInfo> {
        if(applications == null)
            applications = applications()

        return applications!!
    }

    override fun getSystemApplications(): List<PackageInfo> {
        if(systemApplications == null)
            systemApplications = systemApplications()

        return systemApplications!!
    }

    private fun applications(): List<PackageInfo> {
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

    private fun systemApplications(): List<PackageInfo> {
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