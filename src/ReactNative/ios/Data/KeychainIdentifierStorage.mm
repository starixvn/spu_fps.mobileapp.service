#import "KeychainIdentifierStorage.h"
#import <Security/Security.h>

@interface KeychainIdentifierStorage ()
@property (nonatomic, strong, readonly) NSString *fingerPrintJSService;
@end

@implementation KeychainIdentifierStorage

- (instancetype)init {
    self = [super init];
    if (self) {
        _fingerPrintJSService = @"com.fps.ios.keychain";
    }
    return self;
}

- (nullable NSString *)getStringFromKeychain:(NSString *)key {
    NSDictionary *loadQuery = @{
        (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
        (__bridge id)kSecAttrService: self.fingerPrintJSService,
        (__bridge id)kSecReturnData: @YES,
        (__bridge id)kSecAttrAccount: key
    };
    
    CFTypeRef result = NULL;
    OSStatus status = SecItemCopyMatching((__bridge CFDictionaryRef)loadQuery, &result);
    
    if (status != errSecSuccess) {
        return nil;
    }
    
    NSData *resultData = (__bridge_transfer NSData *)result;
    if (!resultData) {
        return nil;
    }
    
    return [[NSString alloc] initWithData:resultData encoding:NSUTF8StringEncoding];
}

- (void)storeStringIntoKeychain:(NSString *)value for:(NSString *)key {
    NSData *stringData = [value dataUsingEncoding:NSUTF8StringEncoding];
    if (!stringData) {
        // Cannot convert string to data, nothing to save
        return;
    }
    
    NSDictionary *storeQuery = @{
        (__bridge id)kSecClass: (__bridge id)kSecClassGenericPassword,
        (__bridge id)kSecValueData: stringData,
        (__bridge id)kSecAttrAccessible: (__bridge id)kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly,
        (__bridge id)kSecAttrService: self.fingerPrintJSService,
        (__bridge id)kSecAttrAccount: key
    };
    
    OSStatus addStatus = SecItemAdd((__bridge CFDictionaryRef)storeQuery, NULL);
    
    if (addStatus == errSecDuplicateItem) {
        NSDictionary *updateQuery = @{
            (__bridge id)kSecValueData: stringData
        };
        
        OSStatus updateStatus = SecItemUpdate((__bridge CFDictionaryRef)storeQuery, (__bridge CFDictionaryRef)updateQuery);
        
        if (updateStatus != errSecSuccess) {
            NSLog(@"Cannot save identifier into keychain because error %d occurred", (int)updateStatus);
            // Assuming ThrowableHandler.add is implemented elsewhere
            // [ThrowableHandler addWithMessage:[NSString stringWithFormat:@"Cannot save identifier into keychain because error %d occurred", (int)updateStatus]];
        }
    } else if (addStatus != errSecSuccess) {
        NSLog(@"Cannot save identifier into keychain because error %d occurred", (int)addStatus);
        // [ThrowableHandler addWithMessage:[NSString stringWithFormat:@"Cannot save identifier into keychain because error %d occurred", (int)addStatus]];
    }
}

#pragma mark - IdentifierStorable Protocol

- (void)storeIdentifier:(NSUUID *)identifier for:(NSString *)key {
    NSString *identifierString = [identifier UUIDString];
    [self storeStringIntoKeychain:identifierString for:key];
}

- (nullable NSUUID *)loadIdentifierFor:(NSString *)key {
    NSString *identifierString = [self getStringFromKeychain:key];
    if (!identifierString) {
        return nil;
    }
    
    return [[NSUUID alloc] initWithUUIDString:identifierString];
}

@end