//
//  File.swift
//
//
//  Created by SPU on 03/02/2025.
//

import Foundation
import Security

public class UUIDProvider: IDeviceIdProvider {
    private let keychainKey = "id.deviceUUID"

    private let identifierStorage: IdentifierStorable
    
    public init() {
        self.identifierStorage = KeychainIdentifierStorage()
    }

    public func getId() -> String? {
        return safeException({
            var uuidIdentifier: UUID? {
                if let uuidIdentifier = identifierStorage.loadIdentifier(for: keychainKey) {
                    return uuidIdentifier
                } else {
                    let uuidIdentifier = UUID()
                    identifierStorage.storeIdentifier(uuidIdentifier, for: keychainKey)
                    return uuidIdentifier
                }
            }
            
            return uuidIdentifier?.uuidString
        }, defaultValue: nil)
    }
}
