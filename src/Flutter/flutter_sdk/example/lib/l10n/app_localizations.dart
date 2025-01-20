import 'package:flutter/material.dart';
import 'package:fps_example/l10n/vi.dart';
import 'en.dart';

class AppLocalizations {
  static const supportedLocales = [
    Locale('en', 'US'),
    Locale('vi', 'VN'),
  ];

  static AppLocalizations of(BuildContext context) {
    final locale = Localizations.localeOf(context);
    if (locale.languageCode == 'vi') {
      return AppLocalizationsVi.of(context);
    }
    return AppLocalizationsEn.of(context);
  }

  String get selectLanguage => '';
  String get login => '';
  String get username => '';
  String get subscriber => '';
}