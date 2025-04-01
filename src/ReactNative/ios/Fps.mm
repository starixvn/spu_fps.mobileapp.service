#import "Fps.h"
#import "Fps/Core/FPSModule.h"

@implementation Fps

RCT_EXPORT_MODULE(Fps)

RCT_EXPORT_METHOD(multiply:(double)a
                  b:(double)b
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
  NSNumber *result = @(a * b);
  resolve(result);
}

RCT_EXPORT_METHOD(onResetFPSLastLiveTime)
{
  [FPSModule onResetFPSLastLiveTime];
}

RCT_EXPORT_METHOD(onCheckFPSTrans:(NSString *)subscriberName
                  userName:(NSString *)userName
                  action:(NSString *)action
                  isOnStart:(BOOL)isOnStart
                  resolve:(RCTPromiseResolveBlock)resolve
                  reject:(RCTPromiseRejectBlock)reject)
{
  NSString *result = [FPSModule onCheckFPSTransWithSubscriberName:subscriberName
                                                        userName:userName
                                                          action:action
                                                       isOnStart:isOnStart];
  resolve(result);
}

- (NSNumber *)multiply:(double)a b:(double)b {
  return @(a * b);
}

- (void)onResetFPSLastLiveTime {
  [FPSModule onResetFPSLastLiveTime];
}

- (NSString *)onCheckFPSTrans:(NSString *)subscriberName
                     userName:(NSString *)userName
                       action:(NSString *)action
                    isOnStart:(BOOL)isOnStart {
  return [FPSModule onCheckFPSTransWithSubscriberName:subscriberName
                                           userName:userName
                                             action:action
                                          isOnStart:isOnStart];
}

@end