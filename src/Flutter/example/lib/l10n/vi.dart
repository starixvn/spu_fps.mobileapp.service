import 'package:flutter/material.dart';

import 'app_localizations.dart';

class AppLocalizationsVi extends AppLocalizations{
  static AppLocalizationsVi of(BuildContext context) {
    return AppLocalizationsVi();
  }

  @override
  String get selectLanguage => 'Chọn ngôn ngữ';
  @override
  String get login => 'Đăng nhập';
  @override
  String get username => 'Tên người dùng';
  @override
  String get subscriber => 'Người đăng ký';
}