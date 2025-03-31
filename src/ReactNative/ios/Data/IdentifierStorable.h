#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol IdentifierStorable <NSObject>

/**
 * Stores a UUID identifier for a given key
 * @param identifier The UUID to store
 * @param key The key to associate with the UUID
 */
- (void)storeIdentifier:(NSUUID *)identifier for:(NSString *)key;

/**
 * Loads a UUID identifier for a given key
 * @param key The key associated with the UUID
 * @return The UUID if found, nil otherwise
 */
- (nullable NSUUID *)loadIdentifierFor:(NSString *)key;

@end

NS_ASSUME_NONNULL_END