import { useState, useEffect } from 'react';
import { onResetFPSLastLiveTime } from 'fps';
import { useLanguage } from '../contexts/LanguageContext';

export interface UserData {
  username: string;
  subscriberName: string;
  language: string;
}

export const useMainViewModel = (initialData: UserData) => {
  const { t } = useLanguage();
  const [userData] = useState<UserData>(initialData);
  
  // Language mapping
  const languageNames: Record<string, string> = {
    'en': 'English',
    'es': 'Spanish',
    'fr': 'French',
    'de': 'German',
    'zh': 'Chinese'
  };

  useEffect(() => {
    // Reset FPS last live time
    onResetFPSLastLiveTime();
  }, []);

  const getLanguageName = (code: string): string => {
    return languageNames[code] || code;
  };

  return {
    userData,
    getLanguageName,
    t
  };
};

export default useMainViewModel;