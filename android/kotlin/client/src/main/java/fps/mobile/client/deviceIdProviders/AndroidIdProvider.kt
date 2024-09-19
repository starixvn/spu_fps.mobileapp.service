package fps.mobile.client.deviceIdProviders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings

internal class AndroidIdProvider(
    private val contentResolver: ContentResolver?
) : IDeviceIdProvider {

    @SuppressLint("HardwareIds")
    override fun getId(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }
}