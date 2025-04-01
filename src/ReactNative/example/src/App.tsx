import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatusBar } from 'react-native';

// Import screens
import LoginScreen from './screens/LoginScreen';
import MainScreen from './screens/MainScreen';

// Import language provider
import { LanguageProvider } from './contexts/LanguageContext';

// Define types for our navigation stack
export type RootStackParamList = {
  Login: undefined;
  Main: {
    username: string;
    subscriberName: string;
    language: string;
  };
};

const Stack = createNativeStackNavigator<RootStackParamList>();

export default function App() {
  return (
    <LanguageProvider initialLanguage="en">
      <NavigationContainer>
        <StatusBar barStyle="dark-content" backgroundColor="#f5f5f5" />
        <Stack.Navigator 
          initialRouteName="Login" 
          screenOptions={{
            headerShown: false,
            contentStyle: { backgroundColor: '#f5f5f5' }
          }}
        >
          <Stack.Screen name="Login" component={LoginScreen} />
          <Stack.Screen name="Main" component={MainScreen} />
        </Stack.Navigator>
      </NavigationContainer>
    </LanguageProvider>
  );
}
