# FPS Flutter Plugin
The FPS Flutter SDK provides a seamless integration of FPS (Fraud Prevention Service) capabilities within your Flutter applications. This plugin supports both Android and iOS platforms.

## Requirements
- Flutter SDK: ^3.3.0
- Dart SDK: ^3.5.2

## Installation
Add the FPS Flutter SDK to your project by adding it to your `pubspec.yaml` file:
```yaml
dependencies:
  fps:
    path: [path_to_fps_sdk]
```

Then run:

```sh 
flutter pub get
```

## Platform Support
- Android: minSdkVersion as specified in gradle file
- iOS: As specified in Podfile
  
## Quick Started

### Basic Setup
Import the FPS package in your Dart file:

```dart
import 'package:fps/fps.dart';
```

### Define the FPS Service layer
Implement a service layer that utilizes the FPS SDK methods to handle transactions and forwards the `transaction` data to the `FPS Backend` through your application's service layer.

``` dart
import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:fps/fps.dart';
import 'package:http/http.dart' as http;

class FpsService {
  String baseUrl;
  String endpoint;

  FpsService(this.baseUrl, this.endpoint);

  Future<String> checkTransaction(
      String subscriberName,
      String userName,
      String action,
      {bool isOnStart = false}
      ) async {

    final url = Uri.parse(baseUrl + endpoint);

    try {
      Fps fpsSDK = Fps();
      String? fpsTrans = await fpsSDK.onCheckFPSTrans(subscriberName, userName, action, isOnStart: isOnStart);
      
      final Map<String, dynamic> requestData = {
        'transaction': fpsTrans
      };
      if(isOnStart) {
        Future.delayed(const Duration(seconds: 3));
      }
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(requestData),
      );
      return "checkTransaction: $baseUrl ${response.statusCode}";
    }
    catch (e) {
      debugPrint('FPS Error: $e');
      return "";
    }
  }
}
```
### Integrate in Authentication Flow
```dart
// Example of login flow
Future<void> login(BuildContext context) async {
    // Your authentication logic
    // After successful authentication:
  
    FpsService fpsService = FpsService(environmentUrl, "/FPS/MobileCheckTransaction");
    fpsService.checkTransaction(subscriber, userName, "login");
}
```

### Integrating in Session Management
Handle app lifecycle states to properly manage FPS sessions:
- When the user is inactive in the app, invoke `onResetFPSLastLiveTime` to record the last active time.
- When the user returns to the app (e.g., after switching apps, turning off the screen, or experiencing a Wi-Fi disconnection), call `checkTransaction` to log the `login` or `resume app` activity.

```dart
@override
void didChangeAppLifecycleState(AppLifecycleState state) {
  if (state == AppLifecycleState.resumed) {
    // Check transactions when app is resumed
    FpsService fpsService = FpsService(environmentUrl, "/FPS/MobileCheckTransaction");
    fpsService.checkTransaction(subscriber, userName, "resume app", isOnStart: true);
  } else if (state == AppLifecycleState.paused) {
    // Reset FPS last live time when app is paused
    Fps fpsSDK = Fps();
    fpsSDK.onResetFPSLastLiveTime();
  }
}
```

## Changelog
* 1.0.6
    * Fix bug on handling Burmese numerals (and similar non-Arabic numerals)
* 1.0.5
    * Support iOS platform
    * Enhance Session Management
* 1.0.4
    * Initial Release for Flutter Plugin
