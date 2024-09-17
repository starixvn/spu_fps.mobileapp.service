package fps.mobile.client.fingerprintProviders

import fps.mobile.client.DeviceIdentifyFactory
import fps.mobile.client.utils.StringExtension.hashingMd5

internal class DeviceStateFingerprintProvider: IFingerprintProvider {

    private val settingInfoProvider = DeviceIdentifyFactory.getSettingInfoProvider()
    private val deviceInfoProvider = DeviceIdentifyFactory.getDeviceInfoProvider()

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