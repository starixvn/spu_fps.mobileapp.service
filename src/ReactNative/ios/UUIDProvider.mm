#import "UUIDProvider.h"
#import "KeychainIdentifierStorage.h"
#import <Security/Security.h>

@interface UUIDProvider ()

@property (nonatomic, strong) id<IdentifierStorable> identifierStorage;
@property (nonatomic, strong) NSString *keychainKey;

@end

@implementation UUIDProvider

- (instancetype)init {
    self = [super init];
    if (self) {
        _keychainKey = @"id.deviceUUID";
        _identifierStorage = [[KeychainIdentifierStorage alloc] init];
    }
    return self;
}

- (nullable NSString *)getId {
    @try {
        NSUUID *uuidIdentifier = [self getUUIDIdentifier];
        return uuidIdentifier.UUIDString;
    } @catch (NSException *exception) {
        NSLog(@"Exception in getId: %@", exception);
        return nil;
    }
}

- (NSUUID *)getUUIDIdentifier {
    NSUUID *identifier = [self.identifierStorage loadIdentifierFor:self.keychainKey];
    
    if (identifier) {
        return identifier;
    } else {
        NSUUID *newIdentifier = [NSUUID UUID];
        [self.identifierStorage storeIdentifier:newIdentifier for:self.keychainKey];
        return newIdentifier;
    }
}

@end