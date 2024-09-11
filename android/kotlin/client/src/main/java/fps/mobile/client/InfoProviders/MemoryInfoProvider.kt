package fps.mobile.client.InfoProviders

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import java.math.BigInteger


interface IMemoryInfoProvider {
    public fun getTotalRaminMB(): Long

    public fun getInternalStorage(): Long
    public fun getExternalStorage(): Long
}

internal class MemoryInfoProvider(
    private val context: Context
) : IMemoryInfoProvider {

    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    override fun getTotalRaminMB(): Long {
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
            ?.let { StatFs(it.absolutePath!!) }!!
        return root.totalBytes
    }
}