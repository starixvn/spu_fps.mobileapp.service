#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface DataPackage : NSObject <NSCoding>

@property (nonatomic, strong) NSString *Account;
@property (nonatomic, strong) NSString *Subscriber;
@property (nonatomic, strong) NSString *LoginTime;
@property (nonatomic, strong) NSString *Action;
@property (nonatomic, strong) NSString *ActionResult;
@property (nonatomic, strong, nullable) NSString *MachineCode;
@property (nonatomic, strong, nullable) NSString *CustomCode;
@property (nonatomic, strong, nullable) NSString *MediaDRMCode;
@property (nonatomic, assign) BOOL IsEmulator;
@property (nonatomic, strong, nullable) NSString *ModelName;
@property (nonatomic, strong, nullable) NSString *Manufacturer;
@property (nonatomic, strong, nullable) NSString *Brand;
@property (nonatomic, strong, nullable) NSString *SdkVersion;
@property (nonatomic, strong, nullable) NSString *OS;
@property (nonatomic, strong, nullable) NSString *Timezone;
@property (nonatomic, strong, nullable) NSString *SystemLanguage;
@property (nonatomic, strong, nullable) NSString *Country;
@property (nonatomic, strong, nullable) NSString *Exceptions;

- (NSDictionary *)toDictionary;

@end

NS_ASSUME_NONNULL_END