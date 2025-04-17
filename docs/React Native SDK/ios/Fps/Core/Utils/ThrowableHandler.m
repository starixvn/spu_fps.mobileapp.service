#import "ThrowableHandler.h"
#import <UIKit/UIKit.h>

@implementation ThrowableHandler

static NSMutableArray *exceptions = nil;

+ (void)initialize {
    if (self == [ThrowableHandler class]) {
        exceptions = [NSMutableArray array];
    }
}

+ (void)add:(NSError *)error {
    NSString *message = [NSString stringWithFormat:@"%@", [error localizedDescription]];
    [self addMessage:message];
}

+ (void)addMessage:(NSString *)message {
    if (!exceptions) {
        exceptions = [NSMutableArray array];
    }
    [exceptions addObject:message];
}

+ (NSString *)get {
    NSString *result = [exceptions componentsJoinedByString:@"; "];
    [exceptions removeAllObjects];
    return result;
}

@end

NSObject *safeExecuteWithDefault(id (^block)(void), NSObject *defaultValue) {
    @try {
        return block();
    }
    @catch (NSException *exception) {
        [ThrowableHandler addMessage:[exception description]];
    }
    return defaultValue;
}