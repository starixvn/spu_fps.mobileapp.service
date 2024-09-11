package fps.mobile.client.infoProviders

import android.os.Build

interface IOsBuildInfoProvider {
    fun getManufacturerName(): String
    fun getModelName(): String
}

class OsBuildInfoProvider : IOsBuildInfoProvider{

    override fun getManufacturerName(): String {
        return Build.MANUFACTURER!!
    }

    override fun getModelName(): String {
        return Build.MODEL!!
    }

}