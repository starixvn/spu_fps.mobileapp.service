#import "FPSModule.h"
#import "Constants/Constants.h"
#import "DeviceIdProviders/MachineCodeProvider.h"
#import "DeviceIdProviders/UUIDProvider.h"
#import "InfoProviders/DeviceInfoProvider.h"
#import "InfoProviders/SettingInfoProvider.h"
#import "Utils/DateTimeHelper.h"
#import "Utils/ThrowableHandler.h"
#import "Utils/NSString+Extensions.h"

@interface DataPackage : NSObject

@property (nonatomic, strong) NSString *Account;
@property (nonatomic, strong) NSString *Subscriber;
@property (nonatomic, strong) NSString *LoginTime;
@property (nonatomic, strong) NSString *Action;
@property (nonatomic, strong) NSString *ActionResult;
@property (nonatomic, strong) NSString *MachineCode;
@property (nonatomic, strong) NSString *CustomCode;
@property (nonatomic, strong) NSString *MediaDRMCode;
@property (nonatomic, assign) BOOL IsEmulator;
@property (nonatomic, strong) NSString *ModelName;
@property (nonatomic, strong) NSString *Manufacturer;
@property (nonatomic, strong) NSString *Brand;
@property (nonatomic, strong) NSString *SdkVersion;
@property (nonatomic, strong) NSString *OS;
@property (nonatomic, strong) NSString *Timezone;
@property (nonatomic, strong) NSString *SystemLanguage;
@property (nonatomic, strong) NSString *Country;
@property (nonatomic, strong) NSString *Exceptions;

- (NSDictionary *)toDictionary;

@end

@implementation DataPackage

- (NSDictionary *)toDictionary {
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    if (self.Account) dict[@"Account"] = self.Account;
    if (self.Subscriber) dict[@"Subscriber"] = self.Subscriber;
    if (self.LoginTime) dict[@"LoginTime"] = self.LoginTime;
    if (self.Action) dict[@"Action"] = self.Action;
    if (self.ActionResult) dict[@"ActionResult"] = self.ActionResult;
    if (self.MachineCode) dict[@"MachineCode"] = self.MachineCode;
    if (self.CustomCode) dict[@"CustomCode"] = self.CustomCode;
    if (self.MediaDRMCode) dict[@"MediaDRMCode"] = self.MediaDRMCode;
    dict[@"IsEmulator"] = @(self.IsEmulator);
    if (self.ModelName) dict[@"ModelName"] = self.ModelName;
    if (self.Manufacturer) dict[@"Manufacturer"] = self.Manufacturer;
    if (self.Brand) dict[@"Brand"] = self.Brand;
    if (self.SdkVersion) dict[@"SdkVersion"] = self.SdkVersion;
    if (self.OS) dict[@"OS"] = self.OS;
    if (self.Timezone) dict[@"Timezone"] = self.Timezone;
    if (self.SystemLanguage) dict[@"SystemLanguage"] = self.SystemLanguage;
    if (self.Country) dict[@"Country"] = self.Country;
    if (self.Exceptions) dict[@"Exceptions"] = self.Exceptions;
    
    return dict;
}

@end

@implementation FPSModule

static NSDate *lastLiveTime = nil;

+ (NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                      userName:(NSString *)userName
                                        action:(NSString *)action
                                     isOnStart:(BOOL)isOnStart {
    @try {
        if (isOnStart && lastLiveTime) {
            NSTimeInterval timeInterval = [[NSDate date] timeIntervalSinceDate:lastLiveTime];
            NSInteger freezeTimeInSeconds = (NSInteger)timeInterval;
            
            if (freezeTimeInSeconds > [Constants TIME_FREEZE_VALUE]) {
                return [self getDataPackageWithSubscriberName:subscriberName
                                                    userName:userName
                                                      action:action];
            }
            
            return nil;
        }
        
        return [self getDataPackageWithSubscriberName:subscriberName
                                           userName:userName
                                             action:action];
    } @catch (NSException *exception) {
        [ThrowableHandler addMessage:[exception description]];
        return nil;
    }
}

+ (void)onResetFPSLastLiveTime {
    lastLiveTime = [NSDate date];
}

+ (NSString *)getDataPackageWithSubscriberName:(NSString *)subscriberName
                                     userName:(NSString *)userName
                                       action:(NSString *)action {
    @try {
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
        dataPackage.MachineCode = [[machineCodeProvider getId] hashingMd5];
        dataPackage.CustomCode = [[uuidProvider getId] hashingMd5];
        dataPackage.MediaDRMCode = nil;
        dataPackage.IsEmulator = [deviceInfoProvider isEmulator];
        dataPackage.ModelName = [deviceInfoProvider getModelName];
        dataPackage.Manufacturer = [deviceInfoProvider getManufacturer];
        dataPackage.Brand = [deviceInfoProvider getBrand];
        dataPackage.SdkVersion = [deviceInfoProvider getSdkVersion];
        dataPackage.OS = [Constants OS_VALUE];
        dataPackage.Timezone = [settingInfoProvider getTimezone];
        dataPackage.SystemLanguage = [settingInfoProvider getSystemLanguage];
        dataPackage.Country = [settingInfoProvider getCountry];
        dataPackage.Exceptions = [ThrowableHandler get];
        
        NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:[dataPackage toDictionary]
                                                          options:0
                                                            error:&error];
        
        if (error) {
            [ThrowableHandler addMessage:[error localizedDescription]];
            return nil;
        }
        
        NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        return [jsonString toBase64];
    } @catch (NSException *exception) {
        [ThrowableHandler addMessage:[exception description]];
        return nil;
    }
}

@end