#import "DateTimeHelper.h"
#import "../Constants/Constants.h"

@implementation DateTimeHelper

+ (NSString *)getCurrent {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = [Constants DATETIME_FORMAT];
    NSLocale *englishLocale = [[NSLocale alloc] initWithLocaleIdentifier:@"en"];
    [formatter setLocale:englishLocale];
    return [formatter stringFromDate:[NSDate date]];
}

+ (NSDate *)parseDateTime:(NSString *)strDate {
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    formatter.dateFormat = [Constants DATETIME_FORMAT];
    NSLocale *englishLocale = [[NSLocale alloc] initWithLocaleIdentifier:@"en"];
    [formatter setLocale:englishLocale];
    return [formatter dateFromString:strDate];
}

@end