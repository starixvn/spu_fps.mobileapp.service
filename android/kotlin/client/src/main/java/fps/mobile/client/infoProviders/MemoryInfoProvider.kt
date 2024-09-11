package fps.mobile.client.infoProviders

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs


interface IMemoryInfoProvider {
    fun getTotalRamInMB(): Long

    fun getInternalStorage(): Long
    fun getExternalStorage(): Long
}

class MemoryInfoProvider(
    private val context: Context
) : IMemoryInfoProvider {

    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    override fun getTotalRamInMB(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem / (1024 * 1024)
    }

    override fun getInternalStorage(): Long {
        val root = StatFs(Environment.getRootDirectory().absolutePath)
        return root.totalBytes
    }

    override fun getExternalStorage(): Long {
        val root = context.getExternalFilesDir(null)
            ?.takeIf { it.canRead() }
            ?.let { StatFs(it.absolutePath) }!!
        return root.totalBytes
    }
}