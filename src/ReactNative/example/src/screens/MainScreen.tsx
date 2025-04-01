import React, { useEffect } from 'react';
import { 
  View, 
  Text, 
  StyleSheet, 
  SafeAreaView,
  TouchableOpacity
} from 'react-native';
import type { RouteProp } from '@react-navigation/native';
import type { NativeStackNavigationProp } from '@react-navigation/native-stack';
import useMainViewModel from '../viewModels/MainViewModel';
import { useLanguage, type Language } from '../contexts/LanguageContext';

// Define the navigation param types
type RootStackParamList = {
  Login: undefined;
  Main: {
    username: string;
    subscriberName: string;
    language: string;
  };
};

type MainScreenNavigationProp = NativeStackNavigationProp<RootStackParamList, 'Main'>;
type MainScreenRouteProp = RouteProp<RootStackParamList, 'Main'>;

interface MainScreenProps {
  navigation: MainScreenNavigationProp;
  route: MainScreenRouteProp;
}

const MainScreen: React.FC<MainScreenProps> = ({ navigation, route }) => {
  // Initialize the view model with route params
  const viewModel = useMainViewModel(route.params);
  const { setLanguage } = useLanguage();
  
  // Set the app language based on the selected language in the login screen
  useEffect(() => {
    if (route.params.language) {
      setLanguage(route.params.language as Language);
    }
  }, [route.params.language, setLanguage]);
  
  const handleLogout = () => {
    navigation.navigate('Login');
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        <Text style={styles.welcome}>
          {viewModel.t('main', 'welcome')}, {viewModel.userData.username}!
        </Text>
        
        <View style={styles.card}>
          <Text style={styles.cardTitle}>{viewModel.t('main', 'userInfoTitle')}</Text>
          
          <View style={styles.infoRow}>
            <Text style={styles.infoLabel}>{viewModel.t('main', 'usernameLabel')}</Text>
            <Text style={styles.infoValue}>{viewModel.userData.username}</Text>
          </View>
          
          <View style={styles.infoRow}>
            <Text style={styles.infoLabel}>{viewModel.t('main', 'subscriberLabel')}</Text>
            <Text style={styles.infoValue}>{viewModel.userData.subscriberName}</Text>
          </View>
          
          <View style={styles.infoRow}>
            <Text style={styles.infoLabel}>{viewModel.t('main', 'languageLabel')}</Text>
            <Text style={styles.infoValue}>{viewModel.getLanguageName(viewModel.userData.language)}</Text>
          </View>
        </View>
        
        <TouchableOpacity style={styles.logoutButton} onPress={handleLogout}>
          <Text style={styles.logoutButtonText}>{viewModel.t('main', 'logoutButton')}</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#f5f5f5'
  },
  container: {
    flex: 1,
    padding: 20,
    alignItems: 'center',
    justifyContent: 'center'
  },
  welcome: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 30,
    textAlign: 'center'
  },
  card: {
    backgroundColor: '#ffffff',
    borderRadius: 10,
    padding: 20,
    width: '100%',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
    marginBottom: 20
  },
  cardTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#2c3e50',
    marginBottom: 15,
    textAlign: 'center'
  },
  infoRow: {
    flexDirection: 'row',
    marginBottom: 10,
    paddingBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#f0f0f0'
  },
  infoLabel: {
    fontSize: 16,
    color: '#7f8c8d',
    flex: 1
  },
  infoValue: {
    fontSize: 16,
    color: '#34495e',
    fontWeight: '500',
    flex: 1
  },
  logoutButton: {
    backgroundColor: '#e74c3c',
    paddingVertical: 12,
    paddingHorizontal: 30,
    borderRadius: 8,
    marginTop: 10
  },
  logoutButtonText: {
    color: '#ffffff',
    fontSize: 16,
    fontWeight: '600'
  }
});

export default MainScreen;