#import "UUIDProvider.h"

@implementation UUIDProvider

- (NSString *)getId {
    NSString *uuidKey = @"fps_uuid_key";
    NSString *uuid = [[NSUserDefaults standardUserDefaults] stringForKey:uuidKey];
    
    if (!uuid) {
        uuid = [[NSUUID UUID] UUIDString];
        [[NSUserDefaults standardUserDefaults] setObject:uuid forKey:uuidKey];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
    
    return uuid;
}

@end