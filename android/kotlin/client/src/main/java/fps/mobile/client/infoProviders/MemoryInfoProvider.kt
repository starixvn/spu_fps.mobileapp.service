package fps.mobile.client.infoProviders

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs


internal interface IMemoryInfoProvider {
    fun getTotalRamInMB(): Long

    fun getInternalStorage(): Long
    fun getExternalStorage(): Long
}

internal class MemoryInfoProvider(
    private val context: Context
) : IMemoryInfoProvider {

    private var totalRamInMB: Long? = null
    private var internalStorage: Long? = null
    private var externalStorage: Long? = null

    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    override fun getTotalRamInMB(): Long {
        if(totalRamInMB == null)
            totalRamInMB = totalRamInMB()

        return totalRamInMB!!
    }

    override fun getInternalStorage(): Long {
        if(internalStorage == null)
            internalStorage = internalStorage()

        return internalStorage!!
    }

    override fun getExternalStorage(): Long {
        if(externalStorage == null)
            externalStorage = externalStorage()

        return externalStorage!!
    }

    private fun totalRamInMB(): Long {
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        return memoryInfo.totalMem / (1024 * 1024)
    }

    private fun internalStorage(): Long {
        val root = StatFs(Environment.getRootDirectory().absolutePath)
        return root.totalBytes
    }

    private fun externalStorage(): Long {
        val root = context.getExternalFilesDir(null)
            ?.takeIf { it.canRead() }
            ?.let { StatFs(it.absolutePath) }!!
        return root.totalBytes
    }
}