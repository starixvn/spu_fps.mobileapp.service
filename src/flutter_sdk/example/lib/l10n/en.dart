import 'package:flutter/material.dart';

import 'app_localizations.dart';

class AppLocalizationsEn extends AppLocalizations {
  static AppLocalizationsEn of(BuildContext context) {
    return AppLocalizationsEn();
  }

  @override
  String get selectLanguage => 'Select Language';
  @override
  String get login => 'Login';
  @override
  String get username => 'UserName';
  @override
  String get subscriber => 'Subscriber';
}