library fps_mobile_client;

import 'dart:io';
import 'package:http/http.dart' as http;
import 'dart:convert'; // For JSON encoding/decoding


/// A Calculator.
class FpsMobileClient {
  final String apiUrl;
  FpsMobileClient({required this.apiUrl});
  // Method to send data via POST request
  Future<String> _sendData(Map<String, dynamic> data) async {
    try {
      // Send the POST request to the specified API URL
      final response = await http.post(
        Uri.parse(apiUrl),
        headers: {
          "Content-Type": "application/json",
        },
        body: jsonEncode(data), // Convert the data to JSON format
      );

      // Check the response status code
      if (response.statusCode == 200) {
        // Success
        return "Data sent successfully: ${response.body}";
      } else {
        // Error
        return "Failed to send data. Status code: ${response.statusCode}";
      }
    } catch (e) {
      // Handle errors
      return "Error: $e";
    }
  }

  Future<String> getDeviceInfo() async {
    return await _sendData({
      "Version": Platform.operatingSystemVersion,
      "OS" : Platform.operatingSystem
    });
  }

}
