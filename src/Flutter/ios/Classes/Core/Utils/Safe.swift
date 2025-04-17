//
//  Safe.swift
//  FPSCore
//
//  Created by SPU on 12/2/25.
//

import Foundation

public func safeException<T>(_ block: () throws -> T, defaultValue: T) -> T {
    do {
        return try block()
    } catch {
        ThrowableHandler.add(error)
    }
    return defaultValue
}

public class ThrowableHandler {
    private static var exceptions: [String] = []

    static func add(_ error: Error) {
        let message = "\(error.localizedDescription) at \(Thread.callStackSymbols.first ?? "Unknown location")"
        exceptions.append(message)
    }

    static func add(_ message: String) {
        exceptions.append(message)
    }

    static func get() -> String {
        let result = exceptions.joined(separator: "; ")
        exceptions.removeAll()
        return result
    }
}