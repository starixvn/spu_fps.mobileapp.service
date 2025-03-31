#import "DeviceInfoProvider.h"
#import "Constants.h"
#include <sys/utsname.h>

@implementation DeviceInfoProvider

- (nullable NSString *)getModelName {
    @try {
        struct utsname systemInfo;
        uname(&systemInfo);
        
        return [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
    } @catch (NSException *exception) {
        NSLog(@"Exception in getModelName: %@", exception);
        return nil;
    }
}

- (nullable NSString *)getSdkVersion {
    @try {
        return [UIDevice currentDevice].systemVersion;
    } @catch (NSException *exception) {
        NSLog(@"Exception in getSdkVersion: %@", exception);
        return nil;
    }
}

- (NSString *)getManufacturer {
    return MANUFACTURER;
}

- (NSString *)getBrand {
    return BRAND;
}

- (BOOL)isEmulator {
    @try {
        #if TARGET_IPHONE_SIMULATOR
            return YES;
        #else
            return NO;
        #endif
    } @catch (NSException *exception) {
        NSLog(@"Exception in isEmulator: %@", exception);
        return NO;
    }
}

@end