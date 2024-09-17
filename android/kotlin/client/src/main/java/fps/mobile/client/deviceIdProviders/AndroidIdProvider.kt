package fps.mobile.client.deviceIdProviders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings

internal class AndroidIdProvider(
    private val contentResolver: ContentResolver?
) {
    @SuppressLint("HardwareIds")
    fun getAndroidId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }
}