#import "DateTimeHelper.h"
#import "Constants.h"

@implementation DateTimeHelper

+ (NSString *)getCurrent {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = DATETIME_FORMAT;
    return [formatter stringFromDate:[NSDate date]];
}

+ (nullable NSDate *)parseDateTime:(NSString *)strDate {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = DATETIME_FORMAT;
    return [formatter dateFromString:strDate];
}

@end