package fps.mobile.client.DeviceIdProviders

import android.content.ContentResolver
import android.provider.Settings

class AndroidIdProvider(
    private val contentResolver: ContentResolver?
) {
    public fun getAndroidId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
    }
}