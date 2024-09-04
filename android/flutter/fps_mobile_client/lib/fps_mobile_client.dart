library fps_mobile_client;

import 'dart:io';

/// A Calculator.
class FpsMobileClient {
  String getDeviceInfo() {
    if (Platform.isAndroid) {
      return 'Running on Android version ${Platform.operatingSystemVersion}';
    } else if (Platform.isIOS) {
      return 'Running on iOS version ${Platform.operatingSystemVersion}';
    } else {
      return 'Unsupported platform';
    }
  }
}
