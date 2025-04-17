import 'package:flutter/material.dart';

import '../services/fps_service.dart';

enum Environment {
  local,
  uat,
  prod,
  dev,
}

extension EnvironmentExtension on Environment {
  String get displayName {
    switch (this) {
      case Environment.local:
        return 'LOCAL';
      case Environment.uat:
        return 'UAT';
      case Environment.prod:
        return 'PROD';
        case Environment.dev:
        return 'DEV';
    }
  }

  String get url {
    switch (this) {
      case Environment.local:
        return 'http://fpt-local.nexdev.net';
      case Environment.uat:
        return 'https://fpt-uat.starixuat.com:6012';
      case Environment.prod:
        return 'https://api.detecas.com';
      case Environment.dev:
        return 'http://10.0.2.2:5010';
    }
  }
}

class LoginViewModel extends ChangeNotifier {
  String _userName = 'test';
  String _subscriber = 'Alpha';
  bool _isAuthenticated = false;
  String _env = "http://fpt-local.nexdev.net";
  Locale _locale = const Locale("vi", "VN");

  // Getters
  String get userName => _userName;
  String get subscriber => _subscriber;
  bool get isAuthenticated => _isAuthenticated;
  String get env => _env;
  Locale get locale => _locale;

  // Setters
  void setUserName(String value) {
    _userName = value;
    notifyListeners();
  }

  void setSubscriber(String value) {
    _subscriber = value;
    notifyListeners();
  }

  void setEnv(String value) {
    _env = value;
    notifyListeners();
  }

  void setLocale(Locale value) {
    _locale = value;
    notifyListeners();
  }

  Future<String> login(BuildContext context) async {
    _isAuthenticated = true;
    notifyListeners();

    FpsService fpsService = FpsService(env, "/FPS/MobileCheckTransaction");
    return fpsService.checkTransaction(subscriber, userName, "login");
  }

  Future logout() async {
    _isAuthenticated = false;
    notifyListeners();
  }
}
