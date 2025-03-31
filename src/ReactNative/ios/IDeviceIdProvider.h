#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol IDeviceIdProvider <NSObject>

- (nullable NSString *)getId;

@end

NS_ASSUME_NONNULL_END