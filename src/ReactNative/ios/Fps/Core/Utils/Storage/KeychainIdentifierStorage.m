#import "KeychainIdentifierStorage.h"
#import <Security/Security.h>
#import "../ThrowableHandler.h"

@implementation KeychainIdentifierStorage {
    NSString *fingerPrintJSService;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        fingerPrintJSService = @"com.fps.ios.keychain";
    }
    return self;
}

- (NSString *)getStringFromKeychain:(NSString *)key {
    NSDictionary *loadQuery = @{
        (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
        (__bridge id)kSecAttrService: fingerPrintJSService,
        (__bridge id)kSecReturnData: @YES,
        (__bridge id)kSecAttrAccount: key,
    };
    
    CFTypeRef result = NULL;
    OSStatus status = SecItemCopyMatching((__bridge CFDictionaryRef)loadQuery, &result);
    
    if (status != errSecSuccess) {
        return nil;
    }
    
    NSData *resultData = (__bridge_transfer NSData *)result;
    return [[NSString alloc] initWithData:resultData encoding:NSUTF8StringEncoding];
}

- (void)storeStringIntoKeychain:(NSString *)value forKey:(NSString *)key {
    NSData *stringData = [value dataUsingEncoding:NSUTF8StringEncoding];
    if (!stringData) {
        // Cannot convert string to data, nothing to save
        return;
    }
    
    NSDictionary *storeQuery = @{
        (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
        (__bridge id)kSecValueData: stringData,
        (__bridge id)kSecAttrAccessible: (__bridge id)kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly,
        (__bridge id)kSecAttrService: fingerPrintJSService,
        (__bridge id)kSecAttrAccount: key,
    };
    
    CFTypeRef result = NULL;
    OSStatus addStatus = SecItemAdd((__bridge CFDictionaryRef)storeQuery, &result);
    
    if (addStatus == errSecDuplicateItem) {
        NSDictionary *updateQuery = @{
            (__bridge id)kSecValueData: stringData
        };
        
        OSStatus updateStatus = SecItemUpdate((__bridge CFDictionaryRef)storeQuery, (__bridge CFDictionaryRef)updateQuery);
        
        if (updateStatus != errSecSuccess) {
            NSString *errorMessage = [NSString stringWithFormat:@"Cannot save identifier into keychain because error %d occurred", (int)updateStatus];
            [ThrowableHandler addMessage:errorMessage];
        }
    } else if (addStatus != errSecSuccess) {
        NSString *errorMessage = [NSString stringWithFormat:@"Cannot save identifier into keychain because error %d occurred", (int)addStatus];
        [ThrowableHandler addMessage:errorMessage];
    }
}

#pragma mark - IdentifierStorable Protocol

- (void)storeIdentifier:(NSUUID *)identifier forKey:(NSString *)key {
    NSString *identifierString = [identifier UUIDString];
    [self storeStringIntoKeychain:identifierString forKey:key];
}

- (NSUUID *)loadIdentifierForKey:(NSString *)key {
    NSString *identifierString = [self getStringFromKeychain:key];
    if (!identifierString) {
        return nil;
    }
    
    return [[NSUUID alloc] initWithUUIDString:identifierString];
}

@end