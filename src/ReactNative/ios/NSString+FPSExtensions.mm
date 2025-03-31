#import "NSString+FPSExtensions.h"
#import <CommonCrypto/CommonDigest.h>

@implementation NSString (FPSExtensions)

- (NSString *)toBase64 {
    NSData *data = [self dataUsingEncoding:NSUTF8StringEncoding];
    return [data base64EncodedStringWithOptions:0];
}

- (NSString *)hashingMd5 {
    // Convert the string to data
    NSData *data = [self dataUsingEncoding:NSUTF8StringEncoding];
    
    // Create byte array for the digest
    unsigned char digest[CC_MD5_DIGEST_LENGTH];
    
    // Compute the MD5 hash
    CC_MD5(data.bytes, (CC_LONG)data.length, digest);
    
    // Convert the digest to a hex string
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    
    for (NSInteger i = 0; i < CC_MD5_DIGEST_LENGTH; i++) {
        [output appendFormat:@"%02x", digest[i]];
    }
    
    return output;
}

@end