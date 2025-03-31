#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface DateTimeHelper : NSObject

+ (NSString *)getCurrent;
+ (nullable NSDate *)parseDateTime:(NSString *)strDate;

@end

NS_ASSUME_NONNULL_END