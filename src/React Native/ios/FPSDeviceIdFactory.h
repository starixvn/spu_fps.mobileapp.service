#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Factory class for device identifiers
 * Equivalent to DeviceIdFactory.kt in Android
 */
@interface FPSDeviceIdFactory : NSObject

/**
 * Initialize the factory with a context
 */
+ (instancetype)sharedInstanceWithContext:(id)context;

/**
 * Get the unique identifier for iOS devices (like UUID)
 * Equivalent to AndroidId in Android
 */
+ (NSString * _Nullable)androidId;

/**
 * Get a UUID for the device stored in preferences or file
 */
+ (NSString * _Nullable)uuid;

/**
 * Get a device-specific ID based on hardware
 * Equivalent to MediaDrmId in Android
 */
+ (NSString * _Nullable)mediaDrmId;

@end

NS_ASSUME_NONNULL_END