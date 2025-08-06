#import "AsyncStorageIdentifierStorage.h"
#import <CommonCrypto/CommonDigest.h>

@implementation AsyncStorageIdentifierStorage {
    NSString *storageDirectory;
    NSMutableDictionary *manifest;
    BOOL isSetup;
}

- (instancetype)init {
    self = [super init];
    if (self) {
        storageDirectory = nil;
        manifest = nil;
        isSetup = NO;
    }
    return self;
}

#pragma mark - Private Helper Methods

- (NSString *)createStorageDirectory {
    NSString *bundleId = [[NSBundle mainBundle] bundleIdentifier];
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSApplicationSupportDirectory, NSUserDomainMask, YES);
    NSString *applicationSupportDirectory = [paths firstObject];
    
    // Create path: Application Support/[bundleID]/FPSAsyncStorage
    NSString *fpsStorageDirectory = [applicationSupportDirectory stringByAppendingPathComponent:bundleId];
    fpsStorageDirectory = [fpsStorageDirectory stringByAppendingPathComponent:@"FPSAsyncStorage"];
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;
    
    if (![fileManager fileExistsAtPath:fpsStorageDirectory]) {
        BOOL success = [fileManager createDirectoryAtPath:fpsStorageDirectory
                              withIntermediateDirectories:YES
                                               attributes:nil
                                                    error:&error];
        if (!success) {
            NSLog(@"Failed to create FPS storage directory: %@", error.localizedDescription);
            return nil;
        }
        
        // Set to exclude from backup by default (following AsyncStorage pattern)
        NSURL *directoryURL = [NSURL fileURLWithPath:fpsStorageDirectory];
        [directoryURL setResourceValue:@YES forKey:NSURLIsExcludedFromBackupKey error:nil];
    }
    
    return fpsStorageDirectory;
}

- (NSString *)manifestFilePath {
    return [storageDirectory stringByAppendingPathComponent:@"manifest.json"];
}

- (NSString *)md5Hash:(NSString *)string {
    const char *cStr = [string UTF8String];
    unsigned char digest[CC_MD5_DIGEST_LENGTH];
    CC_MD5(cStr, (CC_LONG)strlen(cStr), digest);
    
    NSMutableString *result = [NSMutableString stringWithCapacity:CC_MD5_DIGEST_LENGTH * 2];
    for (int i = 0; i < CC_MD5_DIGEST_LENGTH; i++) {
        [result appendFormat:@"%02x", digest[i]];
    }
    return result;
}

- (NSString *)filePathForKey:(NSString *)key {
    NSString *safeFileName = [self md5Hash:key];
    return [storageDirectory stringByAppendingPathComponent:safeFileName];
}

- (BOOL)ensureSetup {
    if (isSetup) {
        return YES;
    }
    
    storageDirectory = [self createStorageDirectory];
    if (!storageDirectory) {
        return NO;
    }
    
    // Load or create manifest
    NSString *manifestPath = [self manifestFilePath];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    
    if ([fileManager fileExistsAtPath:manifestPath]) {
        NSData *manifestData = [NSData dataWithContentsOfFile:manifestPath];
        if (manifestData) {
            NSError *error;
            NSDictionary *loadedManifest = [NSJSONSerialization JSONObjectWithData:manifestData
                                                                           options:NSJSONReadingMutableContainers
                                                                             error:&error];
            if (loadedManifest && !error) {
                manifest = [loadedManifest mutableCopy];
            } else {
                NSLog(@"Failed to parse manifest, creating new one: %@", error.localizedDescription);
                manifest = [NSMutableDictionary new];
            }
        } else {
            manifest = [NSMutableDictionary new];
        }
    } else {
        manifest = [NSMutableDictionary new];
    }
    
    isSetup = YES;
    return YES;
}

- (BOOL)writeManifest {
    if (!manifest || !storageDirectory) {
        return NO;
    }
    
    NSError *error;
    NSData *manifestData = [NSJSONSerialization dataWithJSONObject:manifest
                                                           options:NSJSONWritingPrettyPrinted
                                                             error:&error];
    if (!manifestData || error) {
        NSLog(@"Failed to serialize manifest: %@", error.localizedDescription);
        return NO;
    }
    
    NSString *manifestPath = [self manifestFilePath];
    BOOL success = [manifestData writeToFile:manifestPath atomically:YES];
    if (!success) {
        NSLog(@"Failed to write manifest to file");
    }
    
    return success;
}

#pragma mark - Public Storage Methods

- (NSString *)getStringFromStorage:(NSString *)key {
    if (!key || key.length == 0) {
        return nil;
    }
    
    if (![self ensureSetup]) {
        return nil;
    }
    
    // Check if value is in manifest (small values are stored inline)
    NSString *value = manifest[key];
    if (value && ![value isEqual:[NSNull null]]) {
        return value;
    }
    
    // If value is NSNull, it means it's stored in a separate file
    if ([value isEqual:[NSNull null]]) {
        NSString *filePath = [self filePathForKey:key];
        NSError *error;
        NSString *fileContent = [NSString stringWithContentsOfFile:filePath
                                                          encoding:NSUTF8StringEncoding
                                                             error:&error];
        if (fileContent && !error) {
            return fileContent;
        } else {
            // File doesn't exist anymore, remove from manifest
            [manifest removeObjectForKey:key];
            [self writeManifest];
        }
    }
    
    return nil;
}

- (void)storeStringIntoStorage:(NSString *)value forKey:(NSString *)key {
    if (!key || key.length == 0) {
        return;
    }
    
    if (![self ensureSetup]) {
        return;
    }
    
    BOOL manifestChanged = NO;
    
    if (!value) {
        // Remove the value
        if (manifest[key]) {
            if ([manifest[key] isEqual:[NSNull null]]) {
                // Remove file
                NSString *filePath = [self filePathForKey:key];
                [[NSFileManager defaultManager] removeItemAtPath:filePath error:nil];
            }
            [manifest removeObjectForKey:key];
            manifestChanged = YES;
        }
    } else {
        // Store the value
        // Following AsyncStorage pattern: small values inline, large values in files
        static const NSUInteger inlineThreshold = 1024;
        
        if (value.length <= inlineThreshold) {
            // Store inline in manifest
            if ([manifest[key] isEqual:[NSNull null]]) {
                // Remove old file if it exists
                NSString *filePath = [self filePathForKey:key];
                [[NSFileManager defaultManager] removeItemAtPath:filePath error:nil];
            }
            manifest[key] = value;
            manifestChanged = YES;
        } else {
            // Store in separate file
            NSString *filePath = [self filePathForKey:key];
            NSError *error;
            BOOL success = [value writeToFile:filePath
                                   atomically:YES
                                     encoding:NSUTF8StringEncoding
                                        error:&error];
            if (success) {
                if (![manifest[key] isEqual:[NSNull null]]) {
                    manifest[key] = [NSNull null];
                    manifestChanged = YES;
                }
            } else {
                NSLog(@"Failed to write value to file for key %@: %@", key, error.localizedDescription);
            }
        }
    }
    
    if (manifestChanged) {
        [self writeManifest];
    }
}

#pragma mark - IdentifierStorable Protocol

- (void)storeIdentifier:(NSUUID *)identifier forKey:(NSString *)key {
    NSString *identifierString = [identifier UUIDString];
    [self storeStringIntoStorage:identifierString forKey:key];
}

- (NSUUID *)loadIdentifierForKey:(NSString *)key {
    NSString *identifierString = [self getStringFromStorage:key];
    if (!identifierString) {
        return nil;
    }
    
    return [[NSUUID alloc] initWithUUIDString:identifierString];
}

@end
