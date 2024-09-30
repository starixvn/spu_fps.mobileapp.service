package fps.mobile.client.infoProviders

import android.content.Context
import android.util.DisplayMetrics

internal data class DisplayInfo(
    val widthPixel: Int,
    val heightPixel: Int,
    val densityDpi: Int
)

internal interface IDisplayInfoProvider {
    fun getDisplayInfo(): DisplayInfo
}

internal class DisplayInfoProvider(
    private val context: Context
) : IDisplayInfoProvider {

    private var displayInfo: DisplayInfo? = null

    override fun getDisplayInfo(): DisplayInfo {
        if(displayInfo == null)
            displayInfo = displayInfo()

        return displayInfo!!
    }

    @Suppress("DEPRECATION")
    private fun displayInfo(): DisplayInfo {
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = context.display
            display?.getRealMetrics(displayMetrics)
        } else {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        return DisplayInfo(
            widthPixel = displayMetrics.widthPixels,
            heightPixel = displayMetrics.heightPixels,
            densityDpi = displayMetrics.densityDpi
        )
    }
}