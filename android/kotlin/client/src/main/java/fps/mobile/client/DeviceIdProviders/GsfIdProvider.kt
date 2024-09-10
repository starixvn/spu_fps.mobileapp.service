package fps.mobile.client.DeviceIdProviders

import android.content.ContentResolver
import android.net.Uri

private const val URI_GSF_CONTENT_PROVIDER = "content://com.google.android.gsf.gservices"
private const val ID_KEY = "android_id"

class GsfIdProvider(
    private val contentResolver: ContentResolver?
) {

    public fun getGsfId(): String? {
        val URI = Uri.parse(URI_GSF_CONTENT_PROVIDER)
        val params = arrayOf(ID_KEY)
        return try {
            val cursor = contentResolver!!
                .query(URI, null, null, params, null)

            if (cursor == null) {
                return null
            }

            if (!cursor.moveToFirst() || cursor.columnCount < 2) {
                cursor.close()
                return null
            }
            try {
                val result: String? = java.lang.Long.toHexString(cursor.getString(1).toLong())
                cursor.close()
                result
            } catch (e: NumberFormatException) {
                cursor.close()
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}