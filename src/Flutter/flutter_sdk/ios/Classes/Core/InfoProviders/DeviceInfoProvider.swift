//
//  DeviceInfoProvider.swift
//  FPSCore
//
//  Created by SPU on 7/2/25.
//
import UIKit

public class DeviceInfoProvider {
    public func getModelName() -> String {
        return UIDevice().name
    }

    public func getSdkVersion() -> String {
        return UIDevice().systemVersion
    }

    public func getManufacturer() -> String {
        return Constants.MANUFACTURER
    }
}
