#import "FPSExceptionHandler.h"

@implementation FPSExceptionHandler

static NSMutableArray<NSString *> *exceptions;

+ (void)initialize {
    if (self == [FPSExceptionHandler class]) {
        exceptions = [NSMutableArray array];
    }
}

+ (void)addException:(NSException *)exception {
    NSString *stackTrace = [[exception callStackSymbols] firstObject] ?: @"";
    NSString *exceptionInfo = [NSString stringWithFormat:@"%@ at %@", exception.reason, stackTrace];
    [exceptions addObject:exceptionInfo];
}

+ (void)addErrorMessage:(NSString *)message {
    [exceptions addObject:message];
}

+ (NSString *)getExceptions {
    NSString *result = [exceptions componentsJoinedByString:@";"];
    [exceptions removeAllObjects];
    return result;
}

+ (id)safeExecute:(id(^)(void))block defaultValue:(id)defaultValue {
    @try {
        return block();
    } @catch (NSException *exception) {
        [self addException:exception];
        return defaultValue;
    }
}

@end