#import <Foundation/Foundation.h>

@interface DeviceInfoProvider : NSObject

- (BOOL)isEmulator;
- (NSString *)getModelName;
- (NSString *)getManufacturer;
- (NSString *)getBrand;
- (NSString *)getSdkVersion;

@end