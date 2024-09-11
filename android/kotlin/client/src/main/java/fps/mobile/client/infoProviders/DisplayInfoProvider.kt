package fps.mobile.client.InfoProviders

import android.content.Context
import android.util.DisplayMetrics

public data class DisplayInfo(
    val widthPixel: Int,
    val heightPixel: Int,
    val densityDpi: Int
)

interface IDisplayInfoProvider {
    public fun getDisplayInfo(): DisplayInfo
}

internal class DisplayInfoProvider(
    private val context: Context
) : IDisplayInfoProvider {

    override fun getDisplayInfo(): DisplayInfo {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return DisplayInfo(
            widthPixel = displayMetrics.widthPixels,
            heightPixel = displayMetrics.heightPixels,
            densityDpi = displayMetrics.densityDpi
        )
    }
}