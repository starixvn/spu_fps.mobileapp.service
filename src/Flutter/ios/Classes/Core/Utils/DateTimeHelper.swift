//
//  DateTimeHelper.swift
//  FPSCore
//
//  Created by SPU on 7/2/25.
//
import Foundation

class DateTimeHelper {
    static func getCurrent() -> String {
        let formatter = DateFormatter()
        formatter.dateFormat = Constants.DATETIME_FORMAT
        return formatter.string(from: Date())
    }

    static func parseDateTime(_ strDate: String) -> Date? {
        let formatter = DateFormatter()
        formatter.dateFormat = Constants.DATETIME_FORMAT
        return formatter.date(from: strDate)
    }
}
