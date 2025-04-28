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

#ifdef RCT_NEW_ARCH_ENABLED
 - (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
 {
    return std::make_shared<facebook::react::<RNFpsSpecJSI>>(params);
 }
#endif
@end