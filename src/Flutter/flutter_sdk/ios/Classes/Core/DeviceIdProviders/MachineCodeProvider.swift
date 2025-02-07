//
//  MachineCodeProvider.swift
//
//
//  Created by SPU on 04/02/2025.
//

import Foundation
import UIKit

public class MachineCodeProvider : IDeviceIdProvider {

    public init() {}

    public func getId() -> String? {
        return UIDevice.current.identifierForVendor!.uuidString
    }
}
