//
//  FPSModule.swift
//  FPSCore
//
//  Created by SPU on 7/2/25.
//
import Foundation

struct DataPackage: Codable {
    var Account: String
    var Subscriber: String
    var LoginTime: String
    var Action: String
    var ActionResult: String
    var MachineCode: String?
    var CustomCode: String?
    var MediaDRMCode: String?
    var IsEmulator: Bool
    var ModelName: String?
    var Manufacturer: String?
    var Brand: String?
    var SdkVersion: String?
    var OS: String?
    var Timezone: String?
    var SystemLanguage: String?
    var Country: String?
    var Exceptions: String?
}

public class FPSModule {
    private static var lastLiveTime: Date? = nil

    public init() {}
    public static func onCheckFPSTrans(
        subscriberName: String,
        userName: String,
        action: String,
        isOnStart: Bool) -> String? {
        return safeException({
            if isOnStart, let lastLiveTime = lastLiveTime {
                let freezeTimeInSeconds = Int(Date().timeIntervalSince(lastLiveTime))

                if freezeTimeInSeconds > Constants.TIME_FREEZE_VALUE {
                    return getDataPackage(subscriberName: subscriberName, userName: userName, action: action)
                }
                
                return nil
            }
            
            return getDataPackage(subscriberName: subscriberName, userName: userName, action: action)
        }, defaultValue: nil)
    }

    public static func onResetFPSLastLiveTime() {
        lastLiveTime = Date()
    }

    public static func getDataPackage(
        subscriberName: String,
        userName: String,
        action: String
    ) -> String? {
        return safeException({
            let machineCodeProvider = MachineCodeProvider()
            let uuidProvider = UUIDProvider()
            let deviceInfoProvider = DeviceInfoProvider()
            let settingInfoProvider = SettingInfoProvider()

            let dataPackage: DataPackage = DataPackage(
                Account: userName,
                Subscriber: subscriberName,
                LoginTime: DateTimeHelper.getCurrent(),
                Action: action,
                ActionResult: "Successfully",
                MachineCode: machineCodeProvider.getId()?.hashingMd5(),
                CustomCode: uuidProvider.getId()?.hashingMd5(),
                MediaDRMCode: nil,
                IsEmulator: deviceInfoProvider.isEmulator(),
                ModelName: deviceInfoProvider.getModelName(),
                Manufacturer: deviceInfoProvider.getManufacturer(),
                Brand: deviceInfoProvider.getBrand(),
                SdkVersion: deviceInfoProvider.getSdkVersion(),
                OS: Constants.OS_VALUE,
                Timezone: settingInfoProvider.getTimezone(),
                SystemLanguage: settingInfoProvider.getSystemLanguage(),
                Country: settingInfoProvider.getCountry(),
                Exceptions: ThrowableHandler.get()
            )
            let json = try JSONEncoder().encode(dataPackage)
            return String(data: json, encoding: .utf8)?.toBase64() 
        }, defaultValue: nil)
    }
}
