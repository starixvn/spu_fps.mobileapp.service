//
//  File.swift
//
//
//  Created by SPU on 03/02/2025.
//

import Foundation
import Security

public class UUIDProvider {
    private let keychainKey = "com.fps.ios.deviceUUID"

    public init() {}

    public func getId() -> String? {
        let uuid = getDeviceUUID()
        return uuid.uuidString
    }

    public func getDeviceUUID() -> UUID {
        if let storedUUID = retrieveUUIDFromKeychain() {
            return storedUUID
        }

        let newUUID = UUID()
        storeUUIDInKeychain(newUUID)
        return newUUID
    }

    public func retrieveUUIDFromKeychain() -> UUID? {
        var query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: keychainKey,
            kSecReturnData as String: kCFBooleanTrue!,
            kSecMatchLimit as String: kSecMatchLimitOne]

        var dataTypeRef: AnyObject?
        let status = SecItemCopyMatching(query as CFDictionary, &dataTypeRef)

        if status == errSecSuccess, let data = dataTypeRef as? Data,
           let uuidString = String(data: data, encoding: .utf8),
           let uuid = UUID(uuidString: uuidString) {
            return uuid
        }

        return nil
    }

    public func storeUUIDInKeychain(_ uuid: UUID) {
        let uuidString = uuid.uuidString
        let data = uuidString.data(using: .utf8)!

        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: keychainKey,
            kSecValueData as String: data
        ]

        SecItemDelete(query as CFDictionary)
        SecItemAdd(query as CFDictionary, nil)
    }
}
