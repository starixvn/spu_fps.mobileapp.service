#import "ThrowableHandler.h"

@implementation ThrowableHandler

static NSMutableArray<NSString *> *exceptions;

+ (void)initialize {
    if (self == [ThrowableHandler class]) {
        exceptions = [NSMutableArray array];
    }
}

+ (void)addWithError:(NSError *)error {
    NSString *message = [NSString stringWithFormat:@"%@ at %@", 
                        error.localizedDescription, 
                        [NSThread callStackSymbols].firstObject ?: @"Unknown location"];
    [exceptions addObject:message];
}

+ (void)addWithMessage:(NSString *)message {
    [exceptions addObject:message];
}

+ (NSString *)get {
    NSString *result = [exceptions componentsJoinedByString:@"; "];
    [exceptions removeAllObjects];
    return result;
}

@end