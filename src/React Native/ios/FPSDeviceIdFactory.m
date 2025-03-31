#import "FPSDeviceIdFactory.h"
#import "FPSExceptionHandler.h"
#import <UIKit/UIDevice.h>
#import <Security/Security.h>

@implementation FPSDeviceIdFactory

static NSString * const kDeviceIDKey = @"UNIQUE_ID";
static NSString * const kInstallationFileName = @"CONSTANT_INSTALLATION_APP_GENERATED_ID";

static id sharedContext;
static NSString *cachedAndroidId;
static NSString *cachedUUID;
static NSString *cachedMediaDrmId;

+ (instancetype)sharedInstanceWithContext:(id)context {
    static FPSDeviceIdFactory *instance = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        instance = [[self alloc] init];
        sharedContext = context;
    });
    
    return instance;
}

+ (NSString *)androidId {
    if (cachedAndroidId) {
        return cachedAndroidId;
    }
    
    cachedAndroidId = [FPSExceptionHandler safeExecute:^id{
        // On iOS, use identifierForVendor as an equivalent to Android ID
        return [[[UIDevice currentDevice] identifierForVendor] UUIDString];
    } defaultValue:nil];
    
    return cachedAndroidId;
}

+ (NSString *)uuid {
    if (cachedUUID) {
        return cachedUUID;
    }
    
    cachedUUID = [FPSExceptionHandler safeExecute:^id{
        // Try to get UUID from user defaults
        NSString *uuid = [[NSUserDefaults standardUserDefaults] stringForKey:kDeviceIDKey];
        if (uuid.length > 0) {
            return uuid;
        }
        
        // Try to get UUID from installation file
        uuid = [self getUUIDFromInstallationFile];
        if (uuid.length > 0) {
            [self saveUUIDToUserDefaults:uuid];
            return uuid;
        }
        
        // Generate a new UUID if not found
        uuid = [[NSUUID UUID] UUIDString];
        [self saveUUIDToUserDefaults:uuid];
        [self saveUUIDToInstallationFile:uuid];
        
        return uuid;
    } defaultValue:nil];
    
    return cachedUUID;
}

+ (NSString *)mediaDrmId {
    if (cachedMediaDrmId) {
        return cachedMediaDrmId;
    }
    
    cachedMediaDrmId = [FPSExceptionHandler safeExecute:^id{
        // In iOS, we can use device model + name as a unique identifier
        NSString *deviceModel = [UIDevice currentDevice].model;
        NSString *modelName = [self deviceModelName];
        
        // Hash device info to create a persistent ID similar to MediaDRM in Android
        NSString *combinedId = [NSString stringWithFormat:@"%@+%@", [self getDeviceHardwareId], modelName];
        
        return combinedId;
    } defaultValue:nil];
    
    return cachedMediaDrmId;
}

#pragma mark - Private Helpers

+ (NSString *)deviceModelName {
    size_t size;
    sysctlbyname("hw.machine", NULL, &size, NULL, 0);
    char *machine = malloc(size);
    sysctlbyname("hw.machine", machine, &size, NULL, 0);
    NSString *platform = [NSString stringWithCString:machine encoding:NSUTF8StringEncoding];
    free(machine);
    
    return platform;
}

+ (NSString *)getDeviceHardwareId {
    // Generate a hash from device-specific hardware information
    NSMutableDictionary *keychainQuery = [NSMutableDictionary dictionaryWithObjectsAndKeys:
                                         (__bridge id)kSecClassGenericPassword, (__bridge id)kSecClass,
                                         @"DeviceID", (__bridge id)kSecAttrAccount,
                                         @"com.fps.identifier", (__bridge id)kSecAttrService,
                                         (__bridge id)kSecMatchLimitOne, (__bridge id)kSecMatchLimit,
                                         (__bridge id)kCFBooleanTrue, (__bridge id)kSecReturnAttributes,
                                         nil];
    
    CFTypeRef result = NULL;
    OSStatus status = SecItemCopyMatching((__bridge CFDictionaryRef)keychainQuery, &result);
    
    if (status != errSecSuccess) {
        // No device ID in keychain, generate a new one and store
        NSString *newId = [[NSUUID UUID] UUIDString];
        
        NSMutableDictionary *attributes = [NSMutableDictionary dictionaryWithObjectsAndKeys:
                                          (__bridge id)kSecClassGenericPassword, (__bridge id)kSecClass,
                                          @"DeviceID", (__bridge id)kSecAttrAccount,
                                          @"com.fps.identifier", (__bridge id)kSecAttrService,
                                          [newId dataUsingEncoding:NSUTF8StringEncoding], (__bridge id)kSecValueData,
                                          nil];
        
        SecItemAdd((__bridge CFDictionaryRef)attributes, NULL);
        return newId;
    } else {
        // Get device ID from keychain
        keychainQuery = [NSMutableDictionary dictionaryWithObjectsAndKeys:
                        (__bridge id)kSecClassGenericPassword, (__bridge id)kSecClass,
                        @"DeviceID", (__bridge id)kSecAttrAccount,
                        @"com.fps.identifier", (__bridge id)kSecAttrService,
                        (__bridge id)kSecMatchLimitOne, (__bridge id)kSecMatchLimit,
                        (__bridge id)kCFBooleanTrue, (__bridge id)kSecReturnData,
                        nil];
        
        CFTypeRef resultData = NULL;
        status = SecItemCopyMatching((__bridge CFDictionaryRef)keychainQuery, &resultData);
        
        if (status == errSecSuccess) {
            NSString *deviceId = [[NSString alloc] initWithData:(__bridge NSData *)resultData encoding:NSUTF8StringEncoding];
            CFRelease(resultData);
            return deviceId;
        }
    }
    
    if (result) {
        CFRelease(result);
    }
    
    return [[NSUUID UUID] UUIDString]; // Fallback to random UUID
}

+ (void)saveUUIDToUserDefaults:(NSString *)uuid {
    [[NSUserDefaults standardUserDefaults] setObject:uuid forKey:kDeviceIDKey];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

+ (NSString *)getUUIDFromInstallationFile {
    NSString *filePath = [[self getDocumentsDirectory] stringByAppendingPathComponent:kInstallationFileName];
    
    if ([[NSFileManager defaultManager] fileExistsAtPath:filePath]) {
        NSError *error;
        NSString *uuid = [NSString stringWithContentsOfFile:filePath encoding:NSUTF8StringEncoding error:&error];
        
        if (!error && uuid.length > 0) {
            return uuid;
        }
    }
    
    return nil;
}

+ (void)saveUUIDToInstallationFile:(NSString *)uuid {
    NSString *filePath = [[self getDocumentsDirectory] stringByAppendingPathComponent:kInstallationFileName];
    [uuid writeToFile:filePath atomically:YES encoding:NSUTF8StringEncoding error:nil];
}

+ (NSString *)getDocumentsDirectory {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    return [paths firstObject];
}

@end