#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface NSString (FPSExtensions)

/**
 * Converts the string to a Base64-encoded string
 * @return The Base64-encoded string
 */
- (NSString *)toBase64;

/**
 * Computes the MD5 hash of the string
 * @return The MD5 hash as a hex string
 */
- (NSString *)hashingMd5;

@end

NS_ASSUME_NONNULL_END