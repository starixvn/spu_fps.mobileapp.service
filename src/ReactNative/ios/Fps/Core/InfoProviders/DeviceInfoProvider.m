#import "DeviceInfoProvider.h"
#import <UIKit/UIKit.h>
#import <sys/utsname.h>

@implementation DeviceInfoProvider

- (BOOL)isEmulator {
#if TARGET_IPHONE_SIMULATOR
    return YES;
#else
    return NO;
#endif
}

- (NSString *)getModelName {
    struct utsname systemInfo;
    uname(&systemInfo);
    return [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
}

- (NSString *)getManufacturer {
    return @"Apple Inc";
}

- (NSString *)getBrand {
    return @"Apple";
}

- (NSString *)getSdkVersion {
    return [[UIDevice currentDevice] systemVersion];
}

@end