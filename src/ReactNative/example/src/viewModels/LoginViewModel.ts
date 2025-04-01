import { useState, useCallback, useEffect } from 'react';
import { useLanguage, type Language } from '../contexts/LanguageContext';
import { fpsService } from '../services/FPSService';
import { Environment, DEFAULT_ENVIRONMENT } from '../services/EnvironmentService';

export interface LoginFormData {
  username: string;
  subscriberName: string;
  language: string;
  environment: Environment;
}

export interface LanguageOption {
  label: string;
  value: string;
}

export const useLoginViewModel = () => {
  const { language, setLanguage, t } = useLanguage();
  
  const [username, setUsername] = useState('test');
  const [subscriberName, setSubscriberName] = useState('Alpha');
  const [open, setOpen] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [environment, setEnvironment] = useState<Environment>(DEFAULT_ENVIRONMENT);
  
  const [languages] = useState<LanguageOption[]>([
    { label: 'English', value: 'en' },
    { label: 'Spanish', value: 'es' },
    { label: 'French', value: 'fr' },
    { label: 'German', value: 'de' },
    { label: 'Chinese', value: 'zh' }
  ]);

  // Update both local state and app-wide language context
  const handleLanguageChange = useCallback((value: string) => {
    // Update app-wide language context
    setLanguage(value as Language);
  }, [setLanguage]);

  const validateForm = (): boolean => {
    return username.trim() !== '' && subscriberName.trim() !== '';
  };

  // Update FPS service when environment changes
  useEffect(() => {
    fpsService.setEnvironment(environment);
  }, [environment]);

  const handleEnvironmentChange = useCallback((env: Environment) => {
    setEnvironment(env);
  }, []);

  const getFormData = (): LoginFormData => {
    return {
      username,
      subscriberName,
      language,
      environment
    };
  };
  
  const processFpsTransaction = async (): Promise<boolean> => {
    try {
      setIsProcessing(true);
      
      // Use the FPSService to process the login transaction
      return await fpsService.processLoginTransaction(username, subscriberName);
    } catch (error) {
      console.error('FPS transaction error:', error);
      return false;
    } finally {
      setIsProcessing(false);
    }
  };

  return {
    // Form state
    username,
    subscriberName,
    language,
    environment,
    languages,
    open,
    isProcessing,
    
    // Translation helper
    t,
    
    // Actions
    setUsername,
    setSubscriberName,
    setLanguage: handleLanguageChange,
    setEnvironment: handleEnvironmentChange,
    setOpen,
    validateForm,
    getFormData,
    processFpsTransaction
  };
};

export default useLoginViewModel;