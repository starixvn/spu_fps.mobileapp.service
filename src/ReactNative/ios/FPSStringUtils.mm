#import "FPSStringUtils.h"
#import <CommonCrypto/CommonDigest.h>

@implementation FPSStringUtils

+ (NSString *)md5:(NSString *)input {
    if (!input) {
        return nil;
    }
    
    const char *cStr = [input UTF8String];
    unsigned char digest[16];
    CC_MD5(cStr, (CC_LONG)strlen(cStr), digest);
    
    NSMutableString *output = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    for (int i = 0; i < CC_MD5_DIGEST_LENGTH; i++) {
        [output appendFormat:@"%02x", digest[i]];
    }
    
    return output;
}

+ (NSString *)base64Encode:(NSString *)input {
    if (!input) {
        return nil;
    }
    
    NSData *data = [input dataUsingEncoding:NSUTF8StringEncoding];
    return [data base64EncodedStringWithOptions:0];
}

@end