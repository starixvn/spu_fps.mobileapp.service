#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface ThrowableHandler : NSObject

+ (void)addWithError:(NSError *)error;
+ (void)addWithMessage:(NSString *)message;
+ (NSString *)get;

@end

// Safe exception handling macro - for use in implementation files
#define SafeExceptionWithDefault(block, defaultValue) ({ \
    id result = defaultValue; \
    @try { \
        result = block(); \
    } @catch (NSException *exception) { \
        [ThrowableHandler addWithMessage:[NSString stringWithFormat:@"%@ at %@", exception.reason, [NSThread callStackSymbols][0]]]; \
    } \
    result; \
})

NS_ASSUME_NONNULL_END