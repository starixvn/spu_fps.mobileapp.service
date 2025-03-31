#import "Fps.h"
#import <Foundation/Foundation.h>
#import <React/RCTUtils.h>

// Constants definition
NSString * const kUnknownValue = @"unknown";
NSString * const kDateTimeFormat = @"MM-dd-yyyy HH:mm:ss";
NSInteger const kTimeFreezeValue = 60;
NSString * const kOSValue = @"iOS";

// Forward declarations of helper classes
@interface FPSDeviceIdFactory : NSObject
+ (instancetype)sharedInstanceWithContext:(id)context;
+ (NSString *)androidId;
+ (NSString *)uuid;
+ (NSString *)mediaDrmId;
@end

@interface FPSDeviceInfoFactory : NSObject
+ (instancetype)sharedInstanceWithContext:(id)context;
+ (NSString *)modelName;
+ (NSString *)manufacturer;
+ (NSString *)brand;
+ (NSString *)sdkVersion;
+ (NSString *)timezone;
+ (NSString *)systemLanguage;
+ (NSString *)country;
+ (BOOL)isEmulator;
@end

@interface FPSStringUtils : NSObject
+ (NSString *)md5:(NSString *)input;
+ (NSString *)base64Encode:(NSString *)input;
@end

@interface FPSDateTimeHelper : NSObject
+ (NSString *)getCurrentDateTime;
+ (NSDate *)parseDateTimeString:(NSString *)dateString;
@end

@interface FPSExceptionHandler : NSObject
+ (void)addException:(NSException *)exception;
+ (void)addErrorMessage:(NSString *)message;
+ (NSString *)getExceptions;
@end

// Private interface for Fps class
@interface Fps ()
@property (nonatomic, strong) NSDate *lastLiveTime;
@end

@implementation Fps

// Shared instance implementation
+ (instancetype)sharedInstance {
    static Fps *sharedInstance = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[Fps alloc] init];
    });
    return sharedInstance;
}

RCT_EXPORT_MODULE()

- (NSNumber *)multiply:(double)a b:(double)b {
    NSNumber *result = @(a * b);
    return result;
}

- (void)onResetFPSLastLiveTime {
    @try {
        self.lastLiveTime = [NSDate date];
    } @catch (NSException *exception) {
        [FPSExceptionHandler addException:exception];
    }
}

- (NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                      userName:(NSString *)userName
                                        action:(NSString *)action
                                     isOnStart:(BOOL)isOnStart {
    if ([userName length] == 0) {
        @throw [NSException exceptionWithName:@"InvalidArgumentException"
                                       reason:@"userName must be non-empty!"
                                     userInfo:nil];
    }
    
    if ([subscriberName length] == 0) {
        @throw [NSException exceptionWithName:@"InvalidArgumentException"
                                       reason:@"subscriberName must be non-empty!"
                                     userInfo:nil];
    }
    
    @try {
        if (isOnStart && self.lastLiveTime != nil) {
            NSTimeInterval freezeTimeInSeconds = [[NSDate date] timeIntervalSinceDate:self.lastLiveTime];
            
            if (freezeTimeInSeconds > kTimeFreezeValue) {
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
        [FPSExceptionHandler addException:exception];
        return nil;
    }
}

- (NSString *)getDataPackageWithSubscriberName:(NSString *)subscriberName
                                      userName:(NSString *)userName
                                        action:(NSString *)action {
    @try {
        // Initialize factories with context - assuming we use RCTBridge here
        [FPSDeviceIdFactory sharedInstanceWithContext:self];
        [FPSDeviceInfoFactory sharedInstanceWithContext:self];
        
        NSMutableDictionary *dataPackage = [NSMutableDictionary dictionary];
        
        [dataPackage setObject:userName forKey:@"Account"];
        [dataPackage setObject:subscriberName forKey:@"Subscriber"];
        [dataPackage setObject:[FPSDateTimeHelper getCurrentDateTime] forKey:@"LoginTime"];
        
        [dataPackage setObject:action forKey:@"Action"];
        [dataPackage setObject:@"Successfully" forKey:@"ActionResult"];
        
        NSString *androidId = [FPSDeviceIdFactory androidId];
        if (androidId) {
            [dataPackage setObject:[FPSStringUtils md5:androidId] forKey:@"AndroidCode"];
        }
        
        NSString *uuid = [FPSDeviceIdFactory uuid];
        if (uuid) {
            [dataPackage setObject:[FPSStringUtils md5:uuid] forKey:@"CustomCode"];
        }
        
        NSString *mediaDrmId = [FPSDeviceIdFactory mediaDrmId];
        if (mediaDrmId) {
            [dataPackage setObject:[FPSStringUtils md5:mediaDrmId] forKey:@"MediaDRMCode"];
        }
        
        [dataPackage setObject:@([FPSDeviceInfoFactory isEmulator]) forKey:@"IsEmulator"];
        [dataPackage setObject:[FPSDeviceInfoFactory modelName] forKey:@"ModelName"];
        [dataPackage setObject:[FPSDeviceInfoFactory manufacturer] forKey:@"Manufacturer"];
        [dataPackage setObject:[FPSDeviceInfoFactory brand] forKey:@"Brand"];
        [dataPackage setObject:[FPSDeviceInfoFactory sdkVersion] forKey:@"SdkVersion"];
        [dataPackage setObject:kOSValue forKey:@"OS"];
        
        [dataPackage setObject:[FPSDeviceInfoFactory timezone] forKey:@"Timezone"];
        [dataPackage setObject:[FPSDeviceInfoFactory systemLanguage] forKey:@"SystemLanguage"];
        [dataPackage setObject:[FPSDeviceInfoFactory country] forKey:@"Country"];
        
        [dataPackage setObject:[FPSExceptionHandler getExceptions] forKey:@"Exceptions"];
        
        NSError *error = nil;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dataPackage options:0 error:&error];
        if (error) {
            [FPSExceptionHandler addErrorMessage:[NSString stringWithFormat:@"JSON serialization error: %@", error.localizedDescription]];
            return nil;
        }
        
        NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        return [FPSStringUtils base64Encode:jsonString];
        
    } @catch (NSException *exception) {
        [FPSExceptionHandler addException:exception];
        return nil;
    }
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeFpsSpecJSI>(params);
}

@end
