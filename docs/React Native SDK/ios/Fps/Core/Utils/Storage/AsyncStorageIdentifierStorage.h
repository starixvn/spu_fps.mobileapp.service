#import <Foundation/Foundation.h>
#import "IdentifierStorable.h"

@interface AsyncStorageIdentifierStorage : NSObject <IdentifierStorable>

- (NSString *)getStringFromStorage:(NSString *)key;
- (void)storeStringIntoStorage:(NSString *)value forKey:(NSString *)key;

@end
