//
//  SettingInfoProvider.swift
//  FPSCore
//
//  Created by SPU on 10/2/25.
//

import Foundation

class SettingInfoProvider {
    public func getTimezone() -> String {
        return TimeZone.current.identifier
    }

    public func getSystemLanguage() -> String? {
        if #available(iOS 16, *) {
            return Locale.current.language.languageCode?.identifier(.alpha3)
        } else {
            return Locale.current.languageCode
        }
    }

    public func getCountry() -> String? {
        return Locale.current.regionCode
    }
}
