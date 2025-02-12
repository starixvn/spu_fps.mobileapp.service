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
                // Map of ISO 639-1 (2-letter) to ISO 639-2 (3-letter) language codes
                let iso639Map: [String: String] = [
                    "af": "afr", "sq": "sqi", "ar": "ara", "hy": "hye", "az": "aze",
                    "eu": "eus", "be": "bel", "bn": "ben", "bs": "bos", "bg": "bul",
                    "ca": "cat", "zh": "zho", "hr": "hrv", "cs": "ces", "da": "dan",
                    "nl": "nld", "en": "eng", "et": "est", "fi": "fin", "fr": "fra",
                    "ka": "kat", "de": "deu", "el": "ell", "he": "heb", "hi": "hin",
                    "hu": "hun", "is": "isl", "id": "ind", "it": "ita", "ja": "jpn",
                    "kk": "kaz", "ko": "kor", "lv": "lav", "lt": "lit", "mk": "mkd",
                    "ms": "msa", "mt": "mlt", "mn": "mon", "no": "nor", "pl": "pol",
                    "pt": "por", "ro": "ron", "ru": "rus", "sr": "srp", "sk": "slk",
                    "sl": "slv", "es": "spa", "sv": "swe", "th": "tha", "tr": "tur",
                    "uk": "ukr", "ur": "urd", "vi": "vie"
                ]
                
                guard let languageCode = Locale.current.languageCode else { return nil }
                
                return iso639Map[languageCode]
            }
        }, defaultValue: nil)
    }

    public func getCountry() -> String? {
        return safeException({ 
            return Locale.current.regionCode
        }, defaultValue: nil)
    }
}
