package fps.mobile.client.infoProviders

import android.content.ContentResolver
import android.content.Context
import android.media.RingtoneManager
import android.provider.Settings
import android.text.format.DateFormat
import java.util.LinkedList
import java.util.Locale
import java.util.TimeZone

data class RingtoneInfo(
    val title: String,
    val uri: String
)

interface ISettingInfoProvider {
    fun getTimezone(): String
    fun getDateFormat(): String
    fun getWifiSleepPolicy(): String
    fun getSystemLanguage(): String
    fun getFontScale(): String
    fun getRingtoneList(): List<RingtoneInfo>
    fun getRingtoneSource(): RingtoneInfo

    fun getScreenOffTimeout(): Long

    fun is24HourFormat(): Boolean
    fun isAutoTime(): Boolean
    fun isAutoTimezone(): Boolean
    fun isNotifyAvailableWifi(): Boolean
}

class SettingInfoProvider(
    private val context: Context
) : ISettingInfoProvider {

    private val contentResolver: ContentResolver? = context.contentResolver!!

    override fun getTimezone(): String {
        return TimeZone.getDefault().displayName
    }

    override fun getDateFormat(): String {
        return DateFormat.getDateFormatOrder(context).joinToString()
    }

    override fun getWifiSleepPolicy(): String {
        val policy = Settings.Global.getInt(contentResolver, Settings.Global.WIFI_SLEEP_POLICY)
        return when (policy) {
            Settings.Global.WIFI_SLEEP_POLICY_DEFAULT -> "default"
            Settings.Global.WIFI_SLEEP_POLICY_NEVER -> "never"
            Settings.Global.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED -> "never while plugged"
            else -> ""
        }
    }

    override fun getSystemLanguage(): String {
        return Locale.getDefault().displayLanguage
    }

    override fun getFontScale(): String {
        return Settings.System.getString(contentResolver, Settings.System.FONT_SCALE)
    }

    override fun getRingtoneList(): List<RingtoneInfo> {
        val ringtoneList = LinkedList<RingtoneInfo>()

        val manager = RingtoneManager(context)
        manager.setType(RingtoneManager.TYPE_RINGTONE)
        val cursor = manager.cursor
        while (cursor.moveToNext()) {
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            val ringtoneURI = manager.getRingtoneUri(cursor.position)

            ringtoneList.add(RingtoneInfo(
                title = title,
                uri = ringtoneURI.toString()
            ))
        }

        return ringtoneList
    }

    override fun getRingtoneSource(): RingtoneInfo {
        val defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
            context,
            RingtoneManager.TYPE_RINGTONE
        )

        return RingtoneInfo(
            title = RingtoneManager.getRingtone(context, defaultRingtoneUri).getTitle(context),
            uri = defaultRingtoneUri.toString()
        )
    }

    override fun getScreenOffTimeout(): Long {
        return Settings.System.getLong(contentResolver, Settings.System.SCREEN_OFF_TIMEOUT)
    }

    override fun is24HourFormat(): Boolean {
        return DateFormat.is24HourFormat(context)
    }

    override fun isAutoTime(): Boolean {
        return Settings.Global.getString(contentResolver, Settings.Global.AUTO_TIME) == "1"
    }

    override fun isAutoTimezone(): Boolean {
        return Settings.Global.getString(contentResolver, Settings.Global.AUTO_TIME_ZONE) == "1"
    }

    override fun isNotifyAvailableWifi(): Boolean {
        return Settings.Global.getString(contentResolver, Settings.Global.WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON).toBoolean()
    }

}