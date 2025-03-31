#import "generated/RNFpsSpec/RNFpsSpec.h"

// Forward declarations
@class FPSDeviceIdFactory;
@class FPSDeviceInfoFactory;

@interface Fps : NSObject <NativeFpsSpec>

// Constants
extern NSString * const kUnknownValue;
extern NSString * const kDateTimeFormat;
extern NSInteger const kTimeFreezeValue;
extern NSString * const kOSValue;

// Shared instance
+ (instancetype)sharedInstance;

@end
