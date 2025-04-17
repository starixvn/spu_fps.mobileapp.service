#import <Foundation/Foundation.h>

@interface FPSModule : NSObject

+ (NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                      userName:(NSString *)userName
                                        action:(NSString *)action
                                     isOnStart:(BOOL)isOnStart;

+ (void)onResetFPSLastLiveTime;

+ (NSString *)getDataPackageWithSubscriberName:(NSString *)subscriberName
                                      userName:(NSString *)userName
                                        action:(NSString *)action;

@end