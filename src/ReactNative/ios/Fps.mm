#import "Fps.h"
#import "Module/FPSModule.h"

@implementation Fps
RCT_EXPORT_MODULE()

- (NSNumber *)multiply:(double)a b:(double)b {
    NSNumber *result = @(a * b);
    return result;
}

- (void)onResetFPSLastLiveTime {
    [FPSModule onResetFPSLastLiveTime];
}

- (NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                       userName:(NSString *)userName
                                         action:(NSString *)action
                                      isOnStart:(BOOL)isOnStart {
    return [FPSModule onCheckFPSTransWithSubscriberName:subscriberName
                                              userName:userName
                                                action:action
                                             isOnStart:isOnStart];
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeFpsSpecJSI>(params);
}

@end
