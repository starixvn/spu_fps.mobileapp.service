#import "MachineCodeProvider.h"
#import "KeychainIdentifierStorage.h"

@implementation UIDevice (VendorIdentifierProviding)
// No implementation needed, UIDevice already has identifierForVendor
@end

@interface MachineCodeProvider ()

@property (nonatomic, strong) id<IdentifierStorable> identifierStorage;
@property (nonatomic, strong) id<VendorIdentifierProviding> vendorIdentifierProvider;
@property (nonatomic, strong) NSString *vendorIdentifierKey;

@end

@implementation MachineCodeProvider

- (instancetype)init {
    self = [super init];
    if (self) {
        _vendorIdentifierKey = @"id.vendor";
        _identifierStorage = [[KeychainIdentifierStorage alloc] init];
        _vendorIdentifierProvider = [UIDevice currentDevice];
    }
    return self;
}

- (nullable NSString *)getId {
    @try {
        NSUUID *vendorIdentifier = [self getVendorIdentifier];
        return vendorIdentifier.UUIDString;
    } @catch (NSException *exception) {
        NSLog(@"Exception in getId: %@", exception);
        return nil;
    }
}

- (nullable NSUUID *)getVendorIdentifier {
    NSUUID *identifier = [self.identifierStorage loadIdentifierFor:self.vendorIdentifierKey];
    
    if (identifier) {
        return identifier;
    } else if (self.vendorIdentifierProvider.identifierForVendor) {
        NSUUID *vendorId = self.vendorIdentifierProvider.identifierForVendor;
        [self.identifierStorage storeIdentifier:vendorId for:self.vendorIdentifierKey];
        return vendorId;
    }
    
    return nil;
}

@end