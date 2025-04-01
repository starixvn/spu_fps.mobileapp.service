#import <Foundation/Foundation.h>

@interface ThrowableHandler : NSObject

+ (void)add:(NSError *)error;
+ (void)addMessage:(NSString *)message;
+ (NSString *)get;

@end

// Safe execution wrapper function declaration
NSObject *safeExecuteWithDefault(id (^block)(void), NSObject *defaultValue);