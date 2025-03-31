#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
 * Class for handling and storing exceptions
 * Equivalent to ThrowableHandler in Android
 */
@interface FPSExceptionHandler : NSObject

/**
 * Adds an exception to the exception list
 */
+ (void)addException:(NSException *)exception;

/**
 * Adds an error message to the exception list
 */
+ (void)addErrorMessage:(NSString *)message;

/**
 * Gets all exceptions as a semicolon-separated string and clears the list
 */
+ (NSString *)getExceptions;

/**
 * Utility method to safely execute a block that might throw exceptions
 */
+ (id)safeExecute:(id(^)(void))block defaultValue:(id)defaultValue;

@end

NS_ASSUME_NONNULL_END