@file:Suppress("DEPRECATION")

package fps.mobile.client.deviceIdProviders

import android.content.Context
import androidx.ads.identifier.AdvertisingIdClient

internal class GoogleAdIdProvider(
    private val context: Context
) : IDeviceIdProvider {

    override fun getId(): String? {
        try {
            if (AdvertisingIdClient.isAdvertisingIdProviderAvailable(context)) {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                return adInfo.get().id
            }
        } catch (_: Exception) {
        }

        return null
    }
}