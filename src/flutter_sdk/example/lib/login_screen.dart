import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:fps_example/viewModel/login_viewModel.dart';

import 'l10n/app_localizations.dart';
import 'main_screen.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  @override
  void initState() {
    super.initState();
  }

  Future<void> _login(BuildContext context) async {
    final viewModel = Provider.of<LoginViewModel>(context, listen: false);
    String response = await viewModel.login(context);

    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(response)),
    );

    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => const MainScreen()),
    );
  }

  @override
  Widget build(BuildContext context) {
    final viewModel = Provider.of<LoginViewModel>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Login Screen'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              DropdownButton<Locale>(
                value: viewModel.locale,
                items: AppLocalizations.supportedLocales.map((locale) {
                  return DropdownMenuItem<Locale>(
                    value: locale,
                    child: Text(locale.languageCode == 'en' ? 'English' : 'Vietnam'),
                  );
                }).toList(),
                onChanged: (newLocale) {
                  viewModel.setLocale(newLocale!);
                },
              ),
              const SizedBox(height: 16),
              DropdownButton<String>(
                value: viewModel.env,
                items: Environment.values.map((env) {
                  return DropdownMenuItem<String>(
                    value: env.url,
                    child: Text("${env.displayName}: ${env.url}"),
                  );
                }).toList(),
                onChanged: (value) {
                  viewModel.setEnv(value!);
                },
              ),
              const SizedBox(height: 16),
              TextFormField(
                initialValue: viewModel.subscriber,
                onChanged: viewModel.setSubscriber,
                decoration: InputDecoration(
                  labelText: AppLocalizations.of(context).subscriber,
                  border: const OutlineInputBorder(),
                )
              ),
              const SizedBox(height: 16),
              TextFormField(
                initialValue: viewModel.userName,
                onChanged: viewModel.setUserName,
                decoration: InputDecoration(
                  labelText: AppLocalizations.of(context).username,
                  border: const OutlineInputBorder(),
                )
              ),
              const SizedBox(height: 24.0),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () => _login(context),
                  child: Text(AppLocalizations.of(context).login),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}