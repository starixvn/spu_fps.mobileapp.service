#import "FPSDeviceInfoFactory.h"
#import "FPSExceptionHandler.h"
#import "Fps.h"
#import <UIKit/UIKit.h>
#import <sys/utsname.h>

@implementation FPSDeviceInfoFactory

static id sharedContext;
static NSString *cachedModelName;
static NSString *cachedManufacturer;
static NSString *cachedBrand;
static NSString *cachedSdkVersion;
static NSNumber *cachedIsEmulator;

+ (instancetype)sharedInstanceWithContext:(id)context {
    static FPSDeviceInfoFactory *instance = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        instance = [[self alloc] init];
        sharedContext = context;
    });
    
    return instance;
}

+ (NSString *)modelName {
    if (cachedModelName) {
        return cachedModelName;
    }
    
    cachedModelName = [FPSExceptionHandler safeExecute:^id{
        struct utsname systemInfo;
        uname(&systemInfo);
        NSString *deviceModel = [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
        
        // Map the device identifier to a more human-readable name
        NSDictionary *deviceNamesByCode = @{
            // iPhone
            @"iPhone1,1": @"iPhone",
            @"iPhone1,2": @"iPhone 3G",
            @"iPhone2,1": @"iPhone 3GS",
            @"iPhone3,1": @"iPhone 4",
            @"iPhone3,2": @"iPhone 4",
            @"iPhone3,3": @"iPhone 4",
            @"iPhone4,1": @"iPhone 4S",
            @"iPhone5,1": @"iPhone 5",
            @"iPhone5,2": @"iPhone 5",
            @"iPhone5,3": @"iPhone 5c",
            @"iPhone5,4": @"iPhone 5c",
            @"iPhone6,1": @"iPhone 5s",
            @"iPhone6,2": @"iPhone 5s",
            @"iPhone7,1": @"iPhone 6 Plus",
            @"iPhone7,2": @"iPhone 6",
            @"iPhone8,1": @"iPhone 6s",
            @"iPhone8,2": @"iPhone 6s Plus",
            @"iPhone8,4": @"iPhone SE (1st gen)",
            @"iPhone9,1": @"iPhone 7",
            @"iPhone9,3": @"iPhone 7",
            @"iPhone9,2": @"iPhone 7 Plus",
            @"iPhone9,4": @"iPhone 7 Plus",
            @"iPhone10,1": @"iPhone 8",
            @"iPhone10,4": @"iPhone 8",
            @"iPhone10,2": @"iPhone 8 Plus",
            @"iPhone10,5": @"iPhone 8 Plus",
            @"iPhone10,3": @"iPhone X",
            @"iPhone10,6": @"iPhone X",
            @"iPhone11,2": @"iPhone XS",
            @"iPhone11,4": @"iPhone XS Max",
            @"iPhone11,6": @"iPhone XS Max",
            @"iPhone11,8": @"iPhone XR",
            @"iPhone12,1": @"iPhone 11",
            @"iPhone12,3": @"iPhone 11 Pro",
            @"iPhone12,5": @"iPhone 11 Pro Max",
            @"iPhone12,8": @"iPhone SE (2nd gen)",
            @"iPhone13,1": @"iPhone 12 mini",
            @"iPhone13,2": @"iPhone 12",
            @"iPhone13,3": @"iPhone 12 Pro",
            @"iPhone13,4": @"iPhone 12 Pro Max",
            @"iPhone14,4": @"iPhone 13 mini",
            @"iPhone14,5": @"iPhone 13",
            @"iPhone14,2": @"iPhone 13 Pro",
            @"iPhone14,3": @"iPhone 13 Pro Max",
            @"iPhone14,6": @"iPhone SE (3rd gen)",
            @"iPhone14,7": @"iPhone 14",
            @"iPhone14,8": @"iPhone 14 Plus",
            @"iPhone15,2": @"iPhone 14 Pro",
            @"iPhone15,3": @"iPhone 14 Pro Max",
            @"iPhone15,4": @"iPhone 15",
            @"iPhone15,5": @"iPhone 15 Plus",
            @"iPhone16,1": @"iPhone 15 Pro",
            @"iPhone16,2": @"iPhone 15 Pro Max",
            
            // iPad
            @"iPad1,1": @"iPad",
            @"iPad2,1": @"iPad 2",
            @"iPad2,2": @"iPad 2",
            @"iPad2,3": @"iPad 2",
            @"iPad2,4": @"iPad 2",
            @"iPad2,5": @"iPad mini",
            @"iPad2,6": @"iPad mini",
            @"iPad2,7": @"iPad mini",
            @"iPad3,1": @"iPad (3rd gen)",
            @"iPad3,2": @"iPad (3rd gen)",
            @"iPad3,3": @"iPad (3rd gen)",
            @"iPad3,4": @"iPad (4th gen)",
            @"iPad3,5": @"iPad (4th gen)",
            @"iPad3,6": @"iPad (4th gen)",
            @"iPad4,1": @"iPad Air",
            @"iPad4,2": @"iPad Air",
            @"iPad4,3": @"iPad Air",
            @"iPad4,4": @"iPad mini 2",
            @"iPad4,5": @"iPad mini 2",
            @"iPad4,6": @"iPad mini 2",
            @"iPad4,7": @"iPad mini 3",
            @"iPad4,8": @"iPad mini 3",
            @"iPad4,9": @"iPad mini 3",
            @"iPad5,1": @"iPad mini 4",
            @"iPad5,2": @"iPad mini 4",
            @"iPad5,3": @"iPad Air 2",
            @"iPad5,4": @"iPad Air 2",
            @"iPad6,3": @"iPad Pro (9.7-inch)",
            @"iPad6,4": @"iPad Pro (9.7-inch)",
            @"iPad6,7": @"iPad Pro (12.9-inch)",
            @"iPad6,8": @"iPad Pro (12.9-inch)",
            @"iPad6,11": @"iPad (5th gen)",
            @"iPad6,12": @"iPad (5th gen)",
            @"iPad7,1": @"iPad Pro (12.9-inch) (2nd gen)",
            @"iPad7,2": @"iPad Pro (12.9-inch) (2nd gen)",
            @"iPad7,3": @"iPad Pro (10.5-inch)",
            @"iPad7,4": @"iPad Pro (10.5-inch)",
            @"iPad7,5": @"iPad (6th gen)",
            @"iPad7,6": @"iPad (6th gen)",
            @"iPad7,11": @"iPad (7th gen)",
            @"iPad7,12": @"iPad (7th gen)",
            @"iPad8,1": @"iPad Pro (11-inch)",
            @"iPad8,2": @"iPad Pro (11-inch)",
            @"iPad8,3": @"iPad Pro (11-inch)",
            @"iPad8,4": @"iPad Pro (11-inch)",
            @"iPad8,5": @"iPad Pro (12.9-inch) (3rd gen)",
            @"iPad8,6": @"iPad Pro (12.9-inch) (3rd gen)",
            @"iPad8,7": @"iPad Pro (12.9-inch) (3rd gen)",
            @"iPad8,8": @"iPad Pro (12.9-inch) (3rd gen)",
            @"iPad8,9": @"iPad Pro (11-inch) (2nd gen)",
            @"iPad8,10": @"iPad Pro (11-inch) (2nd gen)",
            @"iPad8,11": @"iPad Pro (12.9-inch) (4th gen)",
            @"iPad8,12": @"iPad Pro (12.9-inch) (4th gen)",
            @"iPad11,1": @"iPad mini (5th gen)",
            @"iPad11,2": @"iPad mini (5th gen)",
            @"iPad11,3": @"iPad Air (3rd gen)",
            @"iPad11,4": @"iPad Air (3rd gen)",
            @"iPad11,6": @"iPad (8th gen)",
            @"iPad11,7": @"iPad (8th gen)",
            @"iPad12,1": @"iPad (9th gen)",
            @"iPad12,2": @"iPad (9th gen)",
            @"iPad14,1": @"iPad mini (6th gen)",
            @"iPad14,2": @"iPad mini (6th gen)",
            
            // Simulator
            @"i386": @"Simulator",
            @"x86_64": @"Simulator",
            @"arm64": @"Simulator",
        };
        
        NSString *deviceName = deviceNamesByCode[deviceModel];
        if (deviceName) {
            return deviceName;
        }
        
        return deviceModel;
    } defaultValue:kUnknownValue];
    
    return cachedModelName;
}

+ (NSString *)manufacturer {
    if (cachedManufacturer) {
        return cachedManufacturer;
    }
    
    cachedManufacturer = [FPSExceptionHandler safeExecute:^id{
        // On iOS devices, the manufacturer is always Apple
        return @"Apple";
    } defaultValue:kUnknownValue];
    
    return cachedManufacturer;
}

+ (NSString *)brand {
    if (cachedBrand) {
        return cachedBrand;
    }
    
    cachedBrand = [FPSExceptionHandler safeExecute:^id{
        // On iOS devices, brand is Apple
        return @"Apple";
    } defaultValue:kUnknownValue];
    
    return cachedBrand;
}

+ (NSString *)sdkVersion {
    if (cachedSdkVersion) {
        return cachedSdkVersion;
    }
    
    cachedSdkVersion = [FPSExceptionHandler safeExecute:^id{
        // iOS version
        return [UIDevice currentDevice].systemVersion;
    } defaultValue:kUnknownValue];
    
    return cachedSdkVersion;
}

+ (NSString *)timezone {
    return [FPSExceptionHandler safeExecute:^id{
        return [NSTimeZone localTimeZone].name;
    } defaultValue:kUnknownValue];
}

+ (NSString *)systemLanguage {
    return [FPSExceptionHandler safeExecute:^id{
        NSString *language = [[NSLocale preferredLanguages] firstObject];
        if (language) {
            NSLocale *locale = [NSLocale localeWithLocaleIdentifier:language];
            return [locale localizedStringForLanguageCode:language];
        }
        return kUnknownValue;
    } defaultValue:kUnknownValue];
}

+ (NSString *)country {
    return [FPSExceptionHandler safeExecute:^id{
        NSLocale *locale = [NSLocale currentLocale];
        NSString *countryCode = [locale objectForKey:NSLocaleCountryCode];
        return countryCode ?: kUnknownValue;
    } defaultValue:kUnknownValue];
}

+ (BOOL)isEmulator {
    if (cachedIsEmulator) {
        return [cachedIsEmulator boolValue];
    }
    
    BOOL result = [FPSExceptionHandler safeExecute:^id{
        // Check for simulator
        #if TARGET_OS_SIMULATOR
            return @(YES);
        #else
            // Additional checks for jailbroken or modified devices
            NSString *deviceModel = [self modelName];
            if ([deviceModel isEqualToString:@"Simulator"]) {
                return @(YES);
            }
            
            // Check for common emulator indicators
            struct utsname systemInfo;
            uname(&systemInfo);
            NSString *machine = [NSString stringWithCString:systemInfo.machine encoding:NSUTF8StringEncoding];
            
            if ([machine isEqualToString:@"x86_64"] || 
                [machine isEqualToString:@"i386"] || 
                [machine isEqualToString:@"arm64"]) {
                return @(YES);
            }
            
            return @(NO);
        #endif
    } defaultValue:@(NO)];
    
    cachedIsEmulator = @(result);
    return result;
}

@end