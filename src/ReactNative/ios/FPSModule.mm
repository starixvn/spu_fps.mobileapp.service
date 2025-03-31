#import "FPSModule.h"
#import "DataPackage.h"
#import "Constants.h"
#import "DateTimeHelper.h"
#import "MachineCodeProvider.h"
#import "UUIDProvider.h"
#import "DeviceInfoProvider.h"
#import "SettingInfoProvider.h"
#import "ThrowableHandler.h"
#import "NSString+FPSExtensions.h"

@implementation FPSModule {
    static NSDate *_lastLiveTime;
}

+ (void)initialize {
    if (self == [FPSModule class]) {
        _lastLiveTime = nil;
    }
}

+ (nullable NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                              userName:(NSString *)userName
                                                action:(NSString *)action
                                             isOnStart:(BOOL)isOnStart {
    return SafeExceptionWithDefault(^{
        if (isOnStart && _lastLiveTime != nil) {
            NSTimeInterval timeInterval = [[NSDate date] timeIntervalSinceDate:_lastLiveTime];
            int freezeTimeInSeconds = (int)timeInterval;
            
            if (freezeTimeInSeconds > TIME_FREEZE_VALUE) {
                return [self getDataPackageWithSubscriberName:subscriberName 
                                                     userName:userName 
                                                       action:action];
            }
            
            return (NSString *)nil;
        }
        
        return [self getDataPackageWithSubscriberName:subscriberName 
                                            userName:userName 
                                              action:action];
    }, nil);
}

+ (void)onResetFPSLastLiveTime {
    _lastLiveTime = [NSDate date];
}

+ (nullable NSString *)getDataPackageWithSubscriberName:(NSString *)subscriberName
                                              userName:(NSString *)userName
                                                action:(NSString *)action {
    return SafeExceptionWithDefault(^{
        MachineCodeProvider *machineCodeProvider = [[MachineCodeProvider alloc] init];
        UUIDProvider *uuidProvider = [[UUIDProvider alloc] init];
        DeviceInfoProvider *deviceInfoProvider = [[DeviceInfoProvider alloc] init];
        SettingInfoProvider *settingInfoProvider = [[SettingInfoProvider alloc] init];
        
        DataPackage *dataPackage = [[DataPackage alloc] init];
        dataPackage.Account = userName;
        dataPackage.Subscriber = subscriberName;
        dataPackage.LoginTime = [DateTimeHelper getCurrent];
        dataPackage.Action = action;
        dataPackage.ActionResult = @"Successfully";
        
        NSString *machineCodeId = [machineCodeProvider getId];
        if (machineCodeId) {
            dataPackage.MachineCode = [machineCodeId hashingMd5];
        }
        
        NSString *uuidId = [uuidProvider getId];
        if (uuidId) {
            dataPackage.CustomCode = [uuidId hashingMd5];
        }
        
        dataPackage.MediaDRMCode = nil;
        dataPackage.IsEmulator = [deviceInfoProvider isEmulator];
        dataPackage.ModelName = [deviceInfoProvider getModelName];
        dataPackage.Manufacturer = [deviceInfoProvider getManufacturer];
        dataPackage.Brand = [deviceInfoProvider getBrand];
        dataPackage.SdkVersion = [deviceInfoProvider getSdkVersion];
        dataPackage.OS = OS_VALUE;
        dataPackage.Timezone = [settingInfoProvider getTimezone];
        dataPackage.SystemLanguage = [settingInfoProvider getSystemLanguage];
        dataPackage.Country = [settingInfoProvider getCountry];
        dataPackage.Exceptions = [ThrowableHandler get];
        
        NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:[dataPackage toDictionary]
                                                           options:0
                                                             error:&error];
        
        if (error || !jsonData) {
            return (NSString *)nil;
        }
        
        NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        return [jsonString toBase64];
    }, nil);
}

@end