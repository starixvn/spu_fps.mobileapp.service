import 'package:flutter_test/flutter_test.dart';
import 'package:fps/fps.dart';
import 'package:fps/fps_platform_interface.dart';
import 'package:fps/fps_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFpsPlatform
    with MockPlatformInterfaceMixin
    implements FpsPlatform {

  @override
  Future<String?> onCheckFPSTrans(String subscriberName, String userName, String action, bool isOnStart) => Future.value('test');

  @override
  Future onResetFPSLastLiveTime() => Future.value();
}

void main() {
  final FpsPlatform initialPlatform = FpsPlatform.instance;

  test('$MethodChannelFps is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFps>());
  });

  test('onCheckFPSTrans', () async {
    Fps fpsPlugin = Fps();
    MockFpsPlatform fakePlatform = MockFpsPlatform();
    FpsPlatform.instance = fakePlatform;

    expect(await fpsPlugin.onCheckFPSTrans('test', 'test', 'test'), 'test');
  });
}
