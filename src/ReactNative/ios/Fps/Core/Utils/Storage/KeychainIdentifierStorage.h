#import <Foundation/Foundation.h>
#import "IdentifierStorable.h"

@interface KeychainIdentifierStorage : NSObject <IdentifierStorable>

- (NSString *)getStringFromKeychain:(NSString *)key;
- (void)storeStringIntoKeychain:(NSString *)value forKey:(NSString *)key;

@end