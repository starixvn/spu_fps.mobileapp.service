#import "UUIDProvider.h"
#import "../Utils/Storage/KeychainIdentifierStorage.h"

@implementation UUIDProvider {
    KeychainIdentifierStorage *_keychainStorage;
    NSString * _uuidKey;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        _keychainStorage = [[KeychainIdentifierStorage alloc] init];
        _uuidKey = @"id.deviceUUID";
    }
    return self;
}

- (NSString *)getId {
    // Try to get the UUID from keychain first
    NSString *uuid = [_keychainStorage getStringFromKeychain:_uuidKey];
    
    if (!uuid) {
        uuid = [[NSUUID UUID] UUIDString];
        // Store the UUID in keychain for future use
        [_keychainStorage storeStringIntoKeychain:uuid forKey:_uuidKey];
    }
    
    return uuid;
}

@end