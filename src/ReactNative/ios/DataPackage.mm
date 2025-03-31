#import "DataPackage.h"

@implementation DataPackage

- (instancetype)init {
    self = [super init];
    if (self) {
        _Account = @"";
        _Subscriber = @"";
        _LoginTime = @"";
        _Action = @"";
        _ActionResult = @"";
        _IsEmulator = NO;
    }
    return self;
}

- (void)encodeWithCoder:(NSCoder *)coder {
    [coder encodeObject:_Account forKey:@"Account"];
    [coder encodeObject:_Subscriber forKey:@"Subscriber"];
    [coder encodeObject:_LoginTime forKey:@"LoginTime"];
    [coder encodeObject:_Action forKey:@"Action"];
    [coder encodeObject:_ActionResult forKey:@"ActionResult"];
    [coder encodeObject:_MachineCode forKey:@"MachineCode"];
    [coder encodeObject:_CustomCode forKey:@"CustomCode"];
    [coder encodeObject:_MediaDRMCode forKey:@"MediaDRMCode"];
    [coder encodeBool:_IsEmulator forKey:@"IsEmulator"];
    [coder encodeObject:_ModelName forKey:@"ModelName"];
    [coder encodeObject:_Manufacturer forKey:@"Manufacturer"];
    [coder encodeObject:_Brand forKey:@"Brand"];
    [coder encodeObject:_SdkVersion forKey:@"SdkVersion"];
    [coder encodeObject:_OS forKey:@"OS"];
    [coder encodeObject:_Timezone forKey:@"Timezone"];
    [coder encodeObject:_SystemLanguage forKey:@"SystemLanguage"];
    [coder encodeObject:_Country forKey:@"Country"];
    [coder encodeObject:_Exceptions forKey:@"Exceptions"];
}

- (nullable instancetype)initWithCoder:(NSCoder *)coder {
    self = [super init];
    if (self) {
        _Account = [coder decodeObjectForKey:@"Account"];
        _Subscriber = [coder decodeObjectForKey:@"Subscriber"];
        _LoginTime = [coder decodeObjectForKey:@"LoginTime"];
        _Action = [coder decodeObjectForKey:@"Action"];
        _ActionResult = [coder decodeObjectForKey:@"ActionResult"];
        _MachineCode = [coder decodeObjectForKey:@"MachineCode"];
        _CustomCode = [coder decodeObjectForKey:@"CustomCode"];
        _MediaDRMCode = [coder decodeObjectForKey:@"MediaDRMCode"];
        _IsEmulator = [coder decodeBoolForKey:@"IsEmulator"];
        _ModelName = [coder decodeObjectForKey:@"ModelName"];
        _Manufacturer = [coder decodeObjectForKey:@"Manufacturer"];
        _Brand = [coder decodeObjectForKey:@"Brand"];
        _SdkVersion = [coder decodeObjectForKey:@"SdkVersion"];
        _OS = [coder decodeObjectForKey:@"OS"];
        _Timezone = [coder decodeObjectForKey:@"Timezone"];
        _SystemLanguage = [coder decodeObjectForKey:@"SystemLanguage"];
        _Country = [coder decodeObjectForKey:@"Country"];
        _Exceptions = [coder decodeObjectForKey:@"Exceptions"];
    }
    return self;
}

- (NSDictionary *)toDictionary {
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    
    dict[@"Account"] = self.Account;
    dict[@"Subscriber"] = self.Subscriber;
    dict[@"LoginTime"] = self.LoginTime;
    dict[@"Action"] = self.Action;
    dict[@"ActionResult"] = self.ActionResult;
    
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