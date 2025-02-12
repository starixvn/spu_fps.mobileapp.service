//
//  SettingInfoProvider.swift
//  FPSCore
//
//  Created by SPU on 10/2/25.
//

import Foundation

class SettingInfoProvider {
    public func getTimezone() -> String? {
        return safeException({
            return TimeZone.current.identifier
        }, defaultValue: nil)
    }

    public func getSystemLanguage() -> String? {
        return safeException({
            if #available(iOS 16, *) {
                return Locale.current.language.languageCode?.identifier(.alpha3)
            } else {
                return Locale.current.languageCode
            }
        }, defaultValue: nil)
    }

    public func getCountry() -> String? {
        return safeException({ 
            return Locale.current.regionCode
        }, defaultValue: nil)
    }
}
