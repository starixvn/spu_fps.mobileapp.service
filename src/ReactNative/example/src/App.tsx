import { useEffect, useRef, useState } from 'react';
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

import NetInfo from '@react-native-community/netinfo';

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
  const [isConnected, setIsConnected] = useState<boolean | null>(null);
  
  useEffect(() => {
    const handleAppStateChange = async (nextAppState: AppStateStatus) => {
      if (nextAppState === 'active') {
        console.log('App has come to the foreground!');
        await processLoginTransaction(true);
      } else if (nextAppState === 'background' || nextAppState === 'inactive') {
        fpsService.resetFPSLastLiveTime();
      }
      appState.current = nextAppState;
    };

    const handleNetworkChange = async (state: NetInfo.NetInfoState) => {
      if (state.isConnected && state.type === 'wifi' && isConnected === false) {
        console.log('WiFi reconnected!');
        await processLoginTransaction(false);
      }
      setIsConnected(state.isConnected);
    };

    const processLoginTransaction = async (isOnStart: boolean) => {
      if (username && subscriberName) {
        await new Promise(resolve => setTimeout(resolve, 10000));
        const result = await fpsService.processLoginTransaction(username, subscriberName, "resume app", isOnStart);
        if (result) {
          console.log('Transaction processed successfully');
        }
      }
    };

    const appStateSubscription = AppState.addEventListener('change', handleAppStateChange);
    const netInfoSubscription = NetInfo.addEventListener(handleNetworkChange);

    return () => {
      appStateSubscription.remove();
      netInfoSubscription();
    };
  }, [username, subscriberName, isConnected]);

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
