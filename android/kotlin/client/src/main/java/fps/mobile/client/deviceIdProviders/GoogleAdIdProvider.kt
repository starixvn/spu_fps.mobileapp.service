@file:Suppress("DEPRECATION")

package fps.mobile.client.deviceIdProviders

import android.content.Context
import androidx.ads.identifier.AdvertisingIdClient

class GoogleAdIdProvider(
    private val context: Context
) {
    fun getGoogleAdId(): String? {
        try {
            if (AdvertisingIdClient.isAdvertisingIdProviderAvailable(context)) {
                val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
                return adInfo.get().id
            }
            return null
        } catch (exception: Exception) {
            return null
        }
    }
}