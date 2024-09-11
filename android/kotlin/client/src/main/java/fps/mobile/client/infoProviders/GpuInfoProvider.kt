package fps.mobile.client.InfoProviders

import android.app.ActivityManager
import android.content.Context

interface  IGpuInfoProvider {
    public fun getGlesVersion(): String
}

internal class GpuInfoProvider(
    private val context: Context
) : IGpuInfoProvider {
    override fun getGlesVersion(): String {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.deviceConfigurationInfo.glEsVersion
    }

}