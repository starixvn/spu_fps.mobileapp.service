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
    var MachineCode: String
    var CustomCode: String
    var MediaDRMCode: String
    var IsEmulator: Bool
    var ModelName: String
    var Manufacturer: String
    var SdkVersion: String
    var OS: String
    var Timezone: String
    var SystemLanguage: String
    var Country: String
    var Exceptions: String
}

public class FPSModule {
    private static var lastLiveTime: Date? = nil

    public init() {}
    public static func onCheckFPSTrans() -> String? {
        return nil
    }

    public static func onResetFPSLastLiveTime() {
        lastLiveTime = Date()
    }

    public static func getDataPackage(
        subscriberName: String,
        userName: String,
        action: String
    ) -> String? {
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
            MachineCode: machineCodeProvider.getId()!,
            CustomCode: uuidProvider.getId()!,
            MediaDRMCode: "N/A",
            IsEmulator: false,
            ModelName: deviceInfoProvider.getModelName(),
            Manufacturer: deviceInfoProvider.getManufacturer(),
            SdkVersion: deviceInfoProvider.getSdkVersion(),
            OS: Constants.OS_VALUE,
            Timezone: settingInfoProvider.getTimezone(),
            SystemLanguage: settingInfoProvider.getSystemLanguage() ?? "N/A",
            Country: settingInfoProvider.getCountry() ?? "N/A",
            Exceptions: ""
        )
        do {
            let json = try JSONEncoder().encode(dataPackage)
            return String(data: json, encoding: .utf8)
        }
        catch {

        }
        return nil
    }
}
