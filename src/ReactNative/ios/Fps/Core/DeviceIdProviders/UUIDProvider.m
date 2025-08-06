#import "UUIDProvider.h"
#import "../Utils/Storage/AsyncStorageIdentifierStorage.h"

@implementation UUIDProvider {
    AsyncStorageIdentifierStorage *_storage;
    NSString * _uuidKey;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        _storage = [[AsyncStorageIdentifierStorage alloc] init];
        _uuidKey = @"id.deviceUUID";
    }
    return self;
}

- (NSString *)getId {
    // Try to get the UUID from storage first
    NSString *uuid = [_storage getStringFromStorage:_uuidKey];
    
    if (!uuid) {
        uuid = [[NSUUID UUID] UUIDString];
        // Store the UUID in storage for future use
        [_storage storeStringIntoStorage:uuid forKey:_uuidKey];
    }
    
    return uuid;
}

@end