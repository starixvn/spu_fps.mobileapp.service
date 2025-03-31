#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Factory class for device information
 * Equivalent to DeviceInfoFactory.kt in Android
 */
@interface FPSDeviceInfoFactory : NSObject

/**
 * Initialize the factory with a context
 */
+ (instancetype)sharedInstanceWithContext:(id)context;

/**
 * Get the device model name
 */
+ (NSString *)modelName;

/**
 * Get the device manufacturer
 */
+ (NSString *)manufacturer;

/**
 * Get the device brand
 */
+ (NSString *)brand;

/**
 * Get the iOS SDK version
 */
+ (NSString *)sdkVersion;

/**
 * Get the device timezone
 */
+ (NSString *)timezone;

/**
 * Get the system language
 */
+ (NSString *)systemLanguage;

/**
 * Get the country code
 */
+ (NSString *)country;

/**
 * Check if the device is an emulator
 */
+ (BOOL)isEmulator;

@end

NS_ASSUME_NONNULL_END