
import 'fps_platform_interface.dart';

class Fps {
  Future<String?> getPlatformVersion() {
    return FpsPlatform.instance.getPlatformVersion();
  }
  Future<String?> onCheckFPSTrans(
      String subscriberName,
      String userName,
      String action,
      {bool isOnStart = false}) {

    return FpsPlatform.instance.onCheckFPSTrans(subscriberName, userName, action, isOnStart);
  }

  Future onResetFPSLastLiveTime() {
    return FpsPlatform.instance.onResetFPSLastLiveTime();
  }
}
