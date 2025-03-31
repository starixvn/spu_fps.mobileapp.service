#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface SettingInfoProvider : NSObject

- (nullable NSString *)getTimezone;
- (nullable NSString *)getSystemLanguage;
- (nullable NSString *)getCountry;

@end

NS_ASSUME_NONNULL_END