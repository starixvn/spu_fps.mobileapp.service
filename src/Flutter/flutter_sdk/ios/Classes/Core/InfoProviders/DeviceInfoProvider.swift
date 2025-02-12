//
//  DeviceInfoProvider.swift
//  FPSCore
//
//  Created by SPU on 7/2/25.
//
import UIKit

public class DeviceInfoProvider {
    public func getModelName() -> String? {
        return safeException({
            return UIDevice().name
        }, defaultValue: nil)
    }

    public func getSdkVersion() -> String? {
        return safeException({
            
            return UIDevice().systemVersion
        }, defaultValue: nil)
    }

    public func getManufacturer() -> String {
        return Constants.MANUFACTURER
    }

    public func getBrand() -> String {
        return Constants.BRAND
    }
        
    public func isEmulator() -> Bool {
        return safeException({
            #if targetEnvironment(simulator)
            return true
            #else
            return false
            #endif
        }, defaultValue: false)
    }
}
