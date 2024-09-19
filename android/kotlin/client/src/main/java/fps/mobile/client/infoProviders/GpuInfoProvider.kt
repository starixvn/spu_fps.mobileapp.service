package fps.mobile.client.infoProviders

import android.app.ActivityManager
import android.content.Context

internal interface  IGpuInfoProvider {
    fun getGlesVersion(): String
}

internal class GpuInfoProvider(
    private val context: Context
) : IGpuInfoProvider {

    private var glesVersion: String? = null

    override fun getGlesVersion(): String {
        if(glesVersion == null)
            glesVersion = glesVersion()

        return glesVersion!!
    }

    private fun glesVersion(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.deviceConfigurationInfo.glEsVersion
    }
}