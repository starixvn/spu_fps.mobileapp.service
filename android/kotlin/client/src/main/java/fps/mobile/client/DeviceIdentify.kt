package fps.mobile.client
import android.content.Context
import android.provider.Settings

object DeviceIdentify {
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}