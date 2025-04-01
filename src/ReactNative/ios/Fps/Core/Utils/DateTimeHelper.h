#import <Foundation/Foundation.h>

@interface DateTimeHelper : NSObject

+ (NSString *)getCurrent;
+ (NSDate *)parseDateTime:(NSString *)strDate;

@end