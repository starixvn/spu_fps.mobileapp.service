#import "Fps.h"

@implementation Fps
RCT_EXPORT_MODULE()

- (NSNumber *)multiply:(double)a b:(double)b {
    NSNumber *result = @(a * b);

    return result;
}

- (void)onResetFPSLastLiveTime {
    // Function implementation goes here
}

- (NSString *)onCheckFPSTransWithSubscriberName:(NSString *)subscriberName
                                       userName:(NSString *)userName
                                         action:(NSString *)action
                                      isOnStart:(BOOL)isOnStart {
    // Implement your logic here
    return nil;
}

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeFpsSpecJSI>(params);
}

@end
