import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_sdk/flutter_sdk.dart';
import 'package:flutter_sdk/flutter_sdk_platform_interface.dart';
import 'package:flutter_sdk/flutter_sdk_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterSdkPlatform
    with MockPlatformInterfaceMixin
    implements FlutterSdkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterSdkPlatform initialPlatform = FlutterSdkPlatform.instance;

  test('$MethodChannelFlutterSdk is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterSdk>());
  });

  test('getPlatformVersion', () async {
    FlutterSdk flutterSdkPlugin = FlutterSdk();
    MockFlutterSdkPlatform fakePlatform = MockFlutterSdkPlatform();
    FlutterSdkPlatform.instance = fakePlatform;

    expect(await flutterSdkPlugin.getPlatformVersion(), '42');
  });
}
