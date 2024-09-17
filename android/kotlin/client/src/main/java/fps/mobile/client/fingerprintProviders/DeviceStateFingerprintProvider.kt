package fps.mobile.client.fingerprintProviders

import android.content.Context
import fps.mobile.client.infoProviders.DeviceInfoProvider
import fps.mobile.client.infoProviders.IDeviceInfoProvider
import fps.mobile.client.infoProviders.ISettingInfoProvider
import fps.mobile.client.infoProviders.SettingInfoProvider
import fps.mobile.client.utils.StringExtension.hashingMd5

class DeviceStateFingerprintProvider(
    context: Context
) : IFingerprintProvider {

    private val settingInfoProvider: ISettingInfoProvider = SettingInfoProvider(context)
    private val deviceInfoProvider: IDeviceInfoProvider = DeviceInfoProvider(context)

    override fun getFingerprint(): String {
        val stringBuilder = StringBuilder()

        stringBuilder.append(settingInfoProvider.getTimezone())
        stringBuilder.append(settingInfoProvider.getDateFormat())
        stringBuilder.append(settingInfoProvider.getWifiSleepPolicy())
        stringBuilder.append(settingInfoProvider.getSystemLanguage())
        stringBuilder.append(settingInfoProvider.getFontScale())

        val ringtoneList = settingInfoProvider.getRingtoneList()
        ringtoneList.forEach{
            stringBuilder.append(it.title).append(it.uri)
        }
        val currentRingtone = settingInfoProvider.getRingtoneSource()
        stringBuilder.append(currentRingtone.title).append(currentRingtone.uri)

        stringBuilder.append(settingInfoProvider.getBrightnessMode())

        stringBuilder.append(settingInfoProvider.getScreenOffTimeout())

        stringBuilder.append(settingInfoProvider.is24HourFormat())
        stringBuilder.append(settingInfoProvider.isAutoTime())
        stringBuilder.append(settingInfoProvider.isAutoTimezone())
        stringBuilder.append(settingInfoProvider.isNotifyAvailableWifi())
        stringBuilder.append(settingInfoProvider.isSoundEffectsEnable())
        stringBuilder.append(settingInfoProvider.isShowingPassword())
        stringBuilder.append(settingInfoProvider.isAutoRotation())

        stringBuilder.append(deviceInfoProvider.isRootedDevice())
        stringBuilder.append(deviceInfoProvider.isEmulator())

        return stringBuilder.toString().hashingMd5()
    }

}