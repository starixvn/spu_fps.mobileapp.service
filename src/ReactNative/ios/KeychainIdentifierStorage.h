#import <Foundation/Foundation.h>
#import "IdentifierStorable.h"

NS_ASSUME_NONNULL_BEGIN

@interface KeychainIdentifierStorage : NSObject <IdentifierStorable>

- (nullable NSString *)getStringFromKeychain:(NSString *)key;
- (void)storeStringIntoKeychain:(NSString *)value for:(NSString *)key;

// IdentifierStorable protocol methods
- (void)storeIdentifier:(NSUUID *)identifier for:(NSString *)key;
- (nullable NSUUID *)loadIdentifierFor:(NSString *)key;

@end

NS_ASSUME_NONNULL_END