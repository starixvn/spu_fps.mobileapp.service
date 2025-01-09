import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'fps_method_channel.dart';

abstract class FpsPlatform extends PlatformInterface {
  /// Constructs a FpsPlatform.
  FpsPlatform() : super(token: _token);

  static final Object _token = Object();

  static FpsPlatform _instance = MethodChannelFps();

  /// The default instance of [FpsPlatform] to use.
  ///
  /// Defaults to [MethodChannelFps].
  static FpsPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FpsPlatform] when
  /// they register themselves.
  static set instance(FpsPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> onCheckFPSTrans(
      String subscriberName,
      String userName,
      String action,
      bool isOnStart) {
    throw UnimplementedError('onCheckFPSTrans() has not been implemented.');
  }

  Future onResetFPSLastLiveTime() {
    throw UnimplementedError('onResetFPSLastLiveTime() has not been implemented.');
  }
}
