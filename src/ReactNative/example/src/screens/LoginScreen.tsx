import React from 'react';
import { 
  View, 
  Text, 
  TextInput, 
  StyleSheet, 
  TouchableOpacity,
  ScrollView,
  SafeAreaView,
  KeyboardAvoidingView,
  Platform,
  Alert,
  ActivityIndicator
} from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import useLoginViewModel from '../viewModels/LoginViewModel';
import EnvironmentSelector from '../components/EnvironmentSelector';
import { useUser } from '../contexts/UserContext';

interface LoginScreenProps {
  navigation: any;
}

const LoginScreen: React.FC<LoginScreenProps> = ({ navigation }) => {
  // Use the view model to handle state and logic
  const viewModel = useLoginViewModel();
  // Access the user context to store credentials
  const { setUserCredentials } = useUser();
  
  const handleLogin = async () => {
    if (viewModel.validateForm()) {
      try {
        // Process FPS transaction before navigating
        const fpsSuccess = await viewModel.processFpsTransaction();
        
        if (!fpsSuccess) {
          Alert.alert('Error', 'FPS verification failed. Please try again.');
          return;
        }
        
        // Get form data from view model
        const formData = viewModel.getFormData();
        
        // Save user credentials to context
        setUserCredentials(formData.username, formData.subscriberName);
        
        // Navigate to Main screen passing user data
        navigation.navigate('Main', formData);
      } catch (error) {
        console.error('Login error:', error);
        Alert.alert('Error', 'An unexpected error occurred during login.');
      }
    } else {
      Alert.alert('Error', viewModel.t('login', 'validationError'));
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <KeyboardAvoidingView 
        style={styles.container}
        behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      >
        <ScrollView 
          contentContainerStyle={styles.scrollContainer}
          keyboardShouldPersistTaps="handled"
        >
          <Text style={styles.title}>{viewModel.t('login', 'title')}</Text>
          <Text style={styles.subtitle}>{viewModel.t('login', 'subtitle')}</Text>
          
          <View style={styles.formContainer}>
            <Text style={styles.label}>{viewModel.t('login', 'usernameLabel')}</Text>
            <TextInput
              style={styles.input}
              placeholder={viewModel.t('login', 'usernameLabel')}
              value={viewModel.username}
              onChangeText={viewModel.setUsername}
              autoCapitalize="none"
            />
            
            <Text style={styles.label}>{viewModel.t('login', 'subscriberLabel')}</Text>
            <TextInput
              style={styles.input}
              placeholder={viewModel.t('login', 'subscriberLabel')}
              value={viewModel.subscriberName}
              onChangeText={viewModel.setSubscriberName}
            />
            
            <Text style={styles.label}>{viewModel.t('login', 'languageLabel')}</Text>
            <View style={styles.dropdownContainer}>
              <DropDownPicker
                open={viewModel.open}
                value={viewModel.language}
                items={viewModel.languages}
                setOpen={viewModel.setOpen}
                setValue={(callback) => {
                  const value = typeof callback === 'function' ? callback(viewModel.language) : callback;
                  viewModel.setLanguage(value);
                }}
                style={styles.dropdown}
                dropDownContainerStyle={styles.dropdownList}
                placeholder={viewModel.t('login', 'languageLabel')}
              />
            </View>
            
            <EnvironmentSelector
              selectedEnvironment={viewModel.environment}
              setSelectedEnvironment={viewModel.setEnvironment}
              label="API Environment"
            />
            
            <TouchableOpacity
              style={styles.loginButton}
              onPress={handleLogin}
              disabled={viewModel.isProcessing}
            >
              {viewModel.isProcessing ? (
                <ActivityIndicator size="small" color="#ffffff" />
              ) : (
                <Text style={styles.loginButtonText}>{viewModel.t('login', 'loginButton')}</Text>
              )}
            </TouchableOpacity>
          </View>
        </ScrollView>
      </KeyboardAvoidingView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#f5f5f5'
  },
  container: {
    flex: 1
  },
  scrollContainer: {
    flexGrow: 1,
    padding: 20,
    justifyContent: 'center'
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2c3e50',
    textAlign: 'center',
    marginBottom: 10
  },
  subtitle: {
    fontSize: 16,
    color: '#7f8c8d',
    textAlign: 'center',
    marginBottom: 30
  },
  formContainer: {
    backgroundColor: '#ffffff',
    borderRadius: 10,
    padding: 20,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3
  },
  label: {
    fontSize: 16,
    marginBottom: 8,
    color: '#34495e'
  },
  input: {
    height: 50,
    borderWidth: 1,
    borderColor: '#dcdde1',
    borderRadius: 8,
    marginBottom: 20,
    paddingHorizontal: 15,
    backgroundColor: '#f9f9f9'
  },
  dropdownContainer: {
    marginBottom: 20,
    zIndex: 1000 // Required for dropdown to show above other elements
  },
  dropdown: {
    borderColor: '#dcdde1',
    backgroundColor: '#f9f9f9'
  },
  dropdownList: {
    borderColor: '#dcdde1',
    backgroundColor: '#ffffff'
  },
  loginButton: {
    backgroundColor: '#3498db',
    paddingVertical: 15,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 10
  },
  loginButtonText: {
    color: '#ffffff',
    fontSize: 18,
    fontWeight: '600'
  }
});

export default LoginScreen;