#import "MachineCodeProvider.h"
#import <UIKit/UIKit.h>

@implementation MachineCodeProvider

- (NSString *)getId {
    return [[[UIDevice currentDevice] identifierForVendor] UUIDString];
}

@end