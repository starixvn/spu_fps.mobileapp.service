#import "Constants.h"

@implementation Constants

+ (NSInteger)TIME_FREEZE_VALUE {
    return 300; // 5 minutes in seconds
}

+ (NSString *)OS_VALUE {
    return @"iOS";
}

+ (NSString *)DATETIME_FORMAT {
    return @"yyyy-MM-dd'T'HH:mm:ss.SSSZ";
}

@end