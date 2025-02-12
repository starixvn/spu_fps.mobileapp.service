//
//  StringExtension.swift
//  FPSCore
//
//  Created by SPU on 11/2/25.
//

import Foundation

extension String {
    func toBase64() -> String {
        return Data(self.utf8).base64EncodedString()
    }
    
}
