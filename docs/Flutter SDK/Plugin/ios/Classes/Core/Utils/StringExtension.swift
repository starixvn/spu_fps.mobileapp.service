//
//  StringExtension.swift
//  FPSCore
//
//  Created by SPU on 11/2/25.
//

import Foundation
import CommonCrypto

extension String {
    func toBase64() -> String {
        return Data(self.utf8).base64EncodedString()
    }
    
    func hashingMd5() -> String {
        let data = Data(self.utf8)
        var digest = [UInt8](repeating: 0, count: Int(CC_MD5_DIGEST_LENGTH))

        data.withUnsafeBytes { bytes in
            _ = CC_MD5(bytes.baseAddress, CC_LONG(data.count), &digest)
        }

        return digest.map { String(format: "%02x", $0) }.joined()
    }
}
