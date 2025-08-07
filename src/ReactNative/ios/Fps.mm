#import "Fps.h"
#import "Fps/Core/FPSModule.h"

@implementation Fps

RCT_EXPORT_MODULE()

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

@end