//
//  File.swift
//
//
//  Created by SPU on 04/02/2025.
//

import Foundation
import UIKit

protocol IDeviceIDProvider {
    func getId() -> String?
}

public class DeviceIdProvider : IDeviceIDProvider {

    public init() {}

    public func getId() -> String? {
        return UIDevice.current.identifierForVendor!.uuidString
    }
}
