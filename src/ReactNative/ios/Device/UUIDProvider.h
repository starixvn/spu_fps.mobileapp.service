#import <Foundation/Foundation.h>
#import "IDeviceIdProvider.h"
#import "IdentifierStorable.h"

NS_ASSUME_NONNULL_BEGIN

@interface UUIDProvider : NSObject <IDeviceIdProvider>

- (instancetype)init;
- (nullable NSString *)getId;

@end

NS_ASSUME_NONNULL_END