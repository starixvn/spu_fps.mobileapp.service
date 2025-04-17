import 'package:flutter_test/flutter_test.dart';
import 'package:fps/fps.dart';
import 'package:fps/fps_platform_interface.dart';
import 'package:fps/fps_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFpsPlatform
    with MockPlatformInterfaceMixin
    implements FpsPlatform {
  @override
  Future<String?> onCheckFPSTrans(String subscriberName, String userName, String action, bool isOnStart) {
    // TODO: implement onCheckFPSTrans
    throw UnimplementedError();
  }

  @override
  Future onResetFPSLastLiveTime() {
    // TODO: implement onResetFPSLastLiveTime
    throw UnimplementedError();
  }
}

void main() {
  final FpsPlatform initialPlatform = FpsPlatform.instance;

  test('$MethodChannelFps is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFps>());
  });
}
