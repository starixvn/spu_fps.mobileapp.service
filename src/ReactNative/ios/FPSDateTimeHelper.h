#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Helper class for date and time operations
 * Equivalent to DateTimeHelper in Android
 */
@interface FPSDateTimeHelper : NSObject

/**
 * Gets the current date and time in the specified format
 */
+ (NSString *)getCurrentDateTime;

/**
 * Parses a date string in the specified format
 */
+ (NSDate * _Nullable)parseDateTimeString:(NSString *)dateString;

@end

NS_ASSUME_NONNULL_END