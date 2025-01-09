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

      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(requestData),
      );
      return "checkTransaction: ${baseUrl} ${response.statusCode}";
    }
    catch (e) {
      debugPrint('FPS Error: $e');
      return "";
    }
  }
}