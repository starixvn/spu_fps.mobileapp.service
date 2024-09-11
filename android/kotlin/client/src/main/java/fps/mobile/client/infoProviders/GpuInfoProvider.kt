package fps.mobile.client.infoProviders

import android.app.ActivityManager
import android.content.Context

interface  IGpuInfoProvider {
    fun getGlesVersion(): String
}

class GpuInfoProvider(
    private val context: Context
) : IGpuInfoProvider {
    override fun getGlesVersion(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.deviceConfigurationInfo.glEsVersion
    }

}