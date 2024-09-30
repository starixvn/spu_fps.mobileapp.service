@file:Suppress("DEPRECATION")

package fps.mobile.client.deviceIdProviders

import android.content.Context
import android.util.Log
import androidx.ads.identifier.AdvertisingIdClient
import androidx.ads.identifier.AdvertisingIdInfo
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures.addCallback
import java.util.concurrent.Executors

internal class GoogleAdIdProvider(
    private val context: Context
) : IDeviceIdProvider {

    override fun getId(): String? {

        try {
            if (AdvertisingIdClient.isAdvertisingIdProviderAvailable(context)) {
                val advertisingIdInfoListenableFuture =
                    AdvertisingIdClient.getAdvertisingIdInfo(context)
                var adId: String? = null
                addCallback(advertisingIdInfoListenableFuture,
                    object : FutureCallback<AdvertisingIdInfo> {
                        override fun onSuccess(adInfo: AdvertisingIdInfo?) {
                            adId = adInfo?.id
                        }

                        // Any exceptions thrown by getAdvertisingIdInfo()
                        // cause this method to be called.
                        override fun onFailure(t: Throwable) {
                            Log.e("MY_APP_TAG",
                                "Failed to connect to Advertising ID provider.")
                        }
                    }, Executors.newSingleThreadExecutor())
                return adId
            }
        } catch (e: Exception) {
            return e.toString()
        }

        return null
    }
}