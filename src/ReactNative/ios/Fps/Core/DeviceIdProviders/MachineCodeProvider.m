#import "MachineCodeProvider.h"
#import <UIKit/UIKit.h>
#import "../Utils/Storage/KeychainIdentifierStorage.h"

@implementation MachineCodeProvider {
    KeychainIdentifierStorage *_keychainStorage;
    NSString * _machineCodeKey;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        _keychainStorage = [[KeychainIdentifierStorage alloc] init];
        _machineCodeKey = @"id.vendor";
    }
    return self;
}

- (NSString *)getId {
    // Try to get the ID from keychain first
    NSString *storedId = [_keychainStorage getStringFromKeychain:_machineCodeKey];
    
    // If ID is not stored yet, get it from the device and store it
    if (!storedId) {
        storedId = [[[UIDevice currentDevice] identifierForVendor] UUIDString];
        
        // Store the ID in keychain for future use
        [_keychainStorage storeStringIntoKeychain:storedId forKey:_machineCodeKey];
    }
    
    return storedId;
}

@end