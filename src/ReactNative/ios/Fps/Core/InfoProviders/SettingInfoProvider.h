#import <Foundation/Foundation.h>

@interface SettingInfoProvider : NSObject

- (NSString *)getTimezone;
- (NSString *)getSystemLanguage;
- (NSString *)getCountry;

@end