#import "SettingInfoProvider.h"

@implementation SettingInfoProvider

- (NSString *)getTimezone {
    NSTimeZone *timeZone = [NSTimeZone localTimeZone];
    return timeZone.name;
}

- (NSString *)getSystemLanguage {
    NSString *language = [[NSLocale preferredLanguages] firstObject];
    return language;
}

- (NSString *)getCountry {
    NSLocale *locale = [NSLocale currentLocale];
    NSString *countryCode = [locale objectForKey:NSLocaleCountryCode];
    return countryCode;
}

@end