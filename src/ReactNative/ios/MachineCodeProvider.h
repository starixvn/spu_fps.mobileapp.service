#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "IDeviceIdProvider.h"
#import "IdentifierStorable.h"

NS_ASSUME_NONNULL_BEGIN

// Define the VendorIdentifierProviding protocol
@protocol VendorIdentifierProviding <NSObject>
@property (readonly, nullable) NSUUID *identifierForVendor;
@end

// Declare UIDevice conforms to VendorIdentifierProviding (implementation is automatic since UIDevice already has this property)
@interface UIDevice (VendorIdentifierProviding) <VendorIdentifierProviding>
@end

@interface MachineCodeProvider : NSObject <IDeviceIdProvider>