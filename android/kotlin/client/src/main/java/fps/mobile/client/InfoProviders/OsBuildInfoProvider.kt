package fps.mobile.client.InfoProviders

import android.os.Build

interface IOsBuildInfoProvider {
    fun getManufacturerName(): String
    fun getModelName(): String
}

internal class OsBuildInfoProvider : IOsBuildInfoProvider{

    override fun getManufacturerName(): String {
        return Build.MANUFACTURER!!
    }

    override fun getModelName(): String {
        return Build.MODEL!!
    }

}