#import <Foundation/Foundation.h>

@protocol IdentifierStorable <NSObject>

- (void)storeIdentifier:(NSUUID *)identifier forKey:(NSString *)key;
- (NSUUID *)loadIdentifierForKey:(NSString *)key;

@end