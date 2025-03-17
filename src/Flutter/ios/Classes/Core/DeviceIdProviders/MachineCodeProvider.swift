//
//  MachineCodeProvider.swift
//
//
//  Created by SPU on 04/02/2025.
//

import Foundation
import UIKit


protocol VendorIdentifierProviding {
    var identifierForVendor: UUID? { get }
}

extension UIDevice: VendorIdentifierProviding {}

public class MachineCodeProvider : IDeviceIdProvider {
    private let vendorIdentifierKey = "id.vendor"

    private let identifierStorage: IdentifierStorable
    private let vendorIdentifierProvider: VendorIdentifierProviding

    public init(
    ) {
        self.identifierStorage = KeychainIdentifierStorage()
        self.vendorIdentifierProvider = UIDevice.current
    }

    public func getId() -> String? {
        return safeException({
            var vendorIdentifier: UUID? {
                if let vendorIdentifier = identifierStorage.loadIdentifier(for: vendorIdentifierKey) {
                    return vendorIdentifier
                } else if let vendorIdentifier = vendorIdentifierProvider.identifierForVendor {
                    identifierStorage.storeIdentifier(vendorIdentifier, for: vendorIdentifierKey)
                    return vendorIdentifier
                }

                return nil
            }
            
            return vendorIdentifier?.uuidString
        }, defaultValue: nil)
    }
}
