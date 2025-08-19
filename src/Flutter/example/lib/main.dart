import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:fps/fps.dart';
import 'package:fps_example/services/fps_service.dart';
import 'package:fps_example/viewModel/login_view_model.dart';
import 'package:provider/provider.dart';

import 'l10n/app_localizations.dart';
import 'login_screen.dart';

void main() {
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => LoginViewModel()),
      ],
      child: const MyApp(),
    ),
  );
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> with WidgetsBindingObserver {
  AppLifecycleState? _lastState;
  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    final viewModel = Provider.of<LoginViewModel>(context, listen: false);

    super.didChangeAppLifecycleState(state);

    if (Platform.isAndroid) {
      _handleAndroidLifecycle(state, viewModel);
    } else if (Platform.isIOS) {
      _handleIOSLifecycle(state, viewModel);
    }
  }

  void _handleAndroidLifecycle(
      AppLifecycleState state, LoginViewModel viewModel) {
    if (state == AppLifecycleState.resumed) {
      _handleResumedState(viewModel, "resume app");
    } else if (state == AppLifecycleState.paused) {
      _resetFpsLastLiveTime();
    }
  }

  void _handleIOSLifecycle(AppLifecycleState state, LoginViewModel viewModel) {
    if (state == AppLifecycleState.resumed) {
      _handleResumedState(viewModel, "resume app");
    } else if (state == AppLifecycleState.inactive &&
        _lastState == AppLifecycleState.resumed) {
      _resetFpsLastLiveTime();
    }

    _lastState = state;
  }

  void _handleResumedState(LoginViewModel viewModel, String action) {
    if (viewModel.isAuthenticated) {
      String userName = viewModel.userName;
      String subscriber = viewModel.subscriber;

      FpsService fpsService =
          FpsService(viewModel.env, "/FPS/MobileCheckTransaction");
      fpsService.checkTransaction(subscriber, userName, action,
          isOnStart: true);
    }
  }

  void _resetFpsLastLiveTime() {
    Fps fpsSDK = Fps();
    fpsSDK.onResetFPSLastLiveTime();
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<LoginViewModel>(context);

    return MaterialApp(
      title: 'Login Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      locale: viewModel.locale,
      supportedLocales: AppLocalizations.supportedLocales,
      localizationsDelegates: const [
        // Localization delegate
        GlobalMaterialLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
      home: const LoginScreen(),
    );
  }
}
