#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface DeviceInfoProvider : NSObject

- (nullable NSString *)getModelName;
- (nullable NSString *)getSdkVersion;
- (NSString *)getManufacturer;
- (NSString *)getBrand;
- (BOOL)isEmulator;

@end

NS_ASSUME_NONNULL_END