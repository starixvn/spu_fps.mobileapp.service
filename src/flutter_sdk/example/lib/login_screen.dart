import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:fps_example/viewModel/login_viewModel.dart';

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
    viewModel.login(context);

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
              DropdownButton<String>(
                value: viewModel.env,
                hint: const Text('Select Environment'),
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
                decoration: const InputDecoration(
                  labelText: 'Subscriber',
                  border: OutlineInputBorder(),
                )
              ),
              const SizedBox(height: 16),
              TextFormField(
                initialValue: viewModel.userName,
                onChanged: viewModel.setUserName,
                decoration: const InputDecoration(
                  labelText: 'UserName',
                  border: OutlineInputBorder(),
                )
              ),
              const SizedBox(height: 24.0),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: () => _login(context),
                  child: const Text('Login'),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}