#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface FPSModule : NSObject

+ (nullable NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                              userName:(NSString *)userName
                                                action:(NSString *)action
                                             isOnStart:(BOOL)isOnStart;

+ (void)onResetFPSLastLiveTime;

+ (nullable NSString *)getDataPackageWithSubscriberName:(NSString *)subscriberName
                                              userName:(NSString *)userName
                                                action:(NSString *)action;

@end

NS_ASSUME_NONNULL_END