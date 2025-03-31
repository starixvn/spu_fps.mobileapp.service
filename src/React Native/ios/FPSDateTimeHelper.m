#import "FPSDateTimeHelper.h"
#import "Fps.h"
#import "FPSExceptionHandler.h"

@implementation FPSDateTimeHelper

+ (NSDateFormatter *)getSharedDateFormatter {
    static NSDateFormatter *dateFormatter = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:kDateTimeFormat];
    });
    
    return dateFormatter;
}

+ (NSString *)getCurrentDateTime {
    return [FPSExceptionHandler safeExecute:^id{
        return [[self getSharedDateFormatter] stringFromDate:[NSDate date]];
    } defaultValue:@""];
}

+ (NSDate *)parseDateTimeString:(NSString *)dateString {
    return [FPSExceptionHandler safeExecute:^id{
        return [[self getSharedDateFormatter] dateFromString:dateString];
    } defaultValue:nil];
}

@end