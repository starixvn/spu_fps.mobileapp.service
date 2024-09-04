import 'package:flutter_test/flutter_test.dart';

import 'package:fps_mobile_client/fps_mobile_client.dart';

void main() {
  test('Test device info retrieval', () {
    final mySdk = FpsMobileClient();
    expect(mySdk.getDeviceInfo(), isNotEmpty);
  });
}
