#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Utility class for string operations like MD5 hashing and Base64 encoding
 * Corresponds to StringExtension.kt in the Android implementation
 */
@interface FPSStringUtils : NSObject

/**
 * Calculates MD5 hash of the input string
 */
+ (NSString *)md5:(NSString *)input;

/**
 * Base64 encodes the input string
 */
+ (NSString *)base64Encode:(NSString *)input;

@end

NS_ASSUME_NONNULL_END