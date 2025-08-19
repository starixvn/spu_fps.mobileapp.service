import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:fps/fps_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  const MethodChannel channel = MethodChannel('fps');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('onCheckFPSTrans', () async {
    MethodChannelFps platform = MethodChannelFps();
    final result = await platform.onCheckFPSTrans('test', 'test', 'test', false);
    expect(result, '42');
  });
}
