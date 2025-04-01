import { useEffect, useRef } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { StatusBar, AppState } from 'react-native';
import type { AppStateStatus } from 'react-native';

// Import FPS service
import { fpsService } from './services/FPSService';

// Import screens
import LoginScreen from './screens/LoginScreen';
import MainScreen from './screens/MainScreen';

// Import providers
import { LanguageProvider } from './contexts/LanguageContext';
import { UserProvider, useUser } from './contexts/UserContext';

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

// AppContent component for handling app state changes with access to context
function AppContent() {
  const appState = useRef(AppState.currentState);
  const { username, subscriberName } = useUser();
  
  // Handle app state changes
  useEffect(() => {
    const subscription = AppState.addEventListener('change', async (nextAppState: AppStateStatus) => {
      if (appState.current.match(/inactive|background/) && nextAppState === 'active') {
        // Add delay to ensure FPS service is ready
        await new Promise(resolve => setTimeout(resolve, 1000));

        // App has come to the foreground
        console.log('App has come to the foreground!');
        
        // Use FPSService to check transaction when app resumes
        if (username && subscriberName) {
          const result = await fpsService.processLoginTransaction(username, subscriberName, "resume app", true);
          
          if (result) {
            // Handle the result if needed
            console.log('App resume check completed');
          }
        }
      } else if (nextAppState === 'background' || nextAppState === 'inactive') {
        // When app moves to background, call FPSService to reset last live time
        fpsService.resetFPSLastLiveTime();
      }
      
      // Update the appState ref
      appState.current = nextAppState;
    });

    // Clean up subscription on unmount
    return () => {
      subscription.remove();
    };
  }, [username, subscriberName]);

  return (
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
  );
}

export default function App() {
  return (
    <LanguageProvider initialLanguage="en">
      <UserProvider>
        <AppContent />
      </UserProvider>
    </LanguageProvider>
  );
}
