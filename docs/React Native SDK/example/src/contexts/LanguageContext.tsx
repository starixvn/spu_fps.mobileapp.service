import React, { createContext, useContext, useState, type ReactNode } from 'react';

// Define available languages
export type Language = 'en' | 'es' | 'fr' | 'de' | 'zh';

// Define language resources
export interface LanguageResources {
  login: {
    title: string;
    subtitle: string;
    usernameLabel: string;
    subscriberLabel: string;
    languageLabel: string;
    loginButton: string;
    validationError: string;
  };
  main: {
    welcome: string;
    userInfoTitle: string;
    usernameLabel: string;
    subscriberLabel: string;
    languageLabel: string;
    fpsTestLabel: string;
    logoutButton: string;
  };
}

// Language resources for each supported language
const resources: Record<Language, LanguageResources> = {
  en: {
    login: {
      title: 'FPS Example App',
      subtitle: 'Please login to continue',
      usernameLabel: 'Username',
      subscriberLabel: 'Subscriber Name',
      languageLabel: 'Select Language',
      loginButton: 'Login',
      validationError: 'Please fill in all fields',
    },
    main: {
      welcome: 'Welcome',
      userInfoTitle: 'User Information',
      usernameLabel: 'Username:',
      subscriberLabel: 'Subscriber:',
      languageLabel: 'Language:',
      fpsTestLabel: 'FPS Test:',
      logoutButton: 'Logout',
    },
  },
  es: {
    login: {
      title: 'App de Ejemplo FPS',
      subtitle: 'Por favor inicie sesión para continuar',
      usernameLabel: 'Usuario',
      subscriberLabel: 'Nombre de Suscriptor',
      languageLabel: 'Seleccionar Idioma',
      loginButton: 'Iniciar Sesión',
      validationError: 'Por favor complete todos los campos',
    },
    main: {
      welcome: 'Bienvenido',
      userInfoTitle: 'Información del Usuario',
      usernameLabel: 'Usuario:',
      subscriberLabel: 'Suscriptor:',
      languageLabel: 'Idioma:',
      fpsTestLabel: 'Prueba FPS:',
      logoutButton: 'Cerrar Sesión',
    },
  },
  fr: {
    login: {
      title: 'Application Exemple FPS',
      subtitle: 'Veuillez vous connecter pour continuer',
      usernameLabel: 'Nom d\'utilisateur',
      subscriberLabel: 'Nom d\'abonné',
      languageLabel: 'Choisir la langue',
      loginButton: 'Connexion',
      validationError: 'Veuillez remplir tous les champs',
    },
    main: {
      welcome: 'Bienvenue',
      userInfoTitle: 'Informations Utilisateur',
      usernameLabel: 'Nom d\'utilisateur:',
      subscriberLabel: 'Abonné:',
      languageLabel: 'Langue:',
      fpsTestLabel: 'Test FPS:',
      logoutButton: 'Déconnexion',
    },
  },
  de: {
    login: {
      title: 'FPS Beispiel-App',
      subtitle: 'Bitte melden Sie sich an, um fortzufahren',
      usernameLabel: 'Benutzername',
      subscriberLabel: 'Abonnentname',
      languageLabel: 'Sprache auswählen',
      loginButton: 'Anmelden',
      validationError: 'Bitte füllen Sie alle Felder aus',
    },
    main: {
      welcome: 'Willkommen',
      userInfoTitle: 'Benutzerinformationen',
      usernameLabel: 'Benutzername:',
      subscriberLabel: 'Abonnent:',
      languageLabel: 'Sprache:',
      fpsTestLabel: 'FPS-Test:',
      logoutButton: 'Abmelden',
    },
  },
  zh: {
    login: {
      title: 'FPS 示例应用',
      subtitle: '请登录以继续',
      usernameLabel: '用户名',
      subscriberLabel: '订阅者名称',
      languageLabel: '选择语言',
      loginButton: '登录',
      validationError: '请填写所有字段',
    },
    main: {
      welcome: '欢迎',
      userInfoTitle: '用户信息',
      usernameLabel: '用户名:',
      subscriberLabel: '订阅者:',
      languageLabel: '语言:',
      fpsTestLabel: 'FPS 测试:',
      logoutButton: '登出',
    },
  },
};

// Define the language context type
interface LanguageContextType {
  language: Language;
  setLanguage: (lang: Language) => void;
  t: (section: keyof LanguageResources, key: string) => string;
}

// Create the context with default values
const LanguageContext = createContext<LanguageContextType>({
  language: 'en',
  setLanguage: () => {},
  t: () => '',
});

// Hook for using the language context
export const useLanguage = () => useContext(LanguageContext);

// Provider component
interface LanguageProviderProps {
  children: ReactNode;
  initialLanguage?: Language;
}

export const LanguageProvider: React.FC<LanguageProviderProps> = ({ 
  children, 
  initialLanguage = 'en' 
}) => {
  const [language, setLanguage] = useState<Language>(initialLanguage);

  // Translation function
  const t = (section: keyof LanguageResources, key: string): string => {
    try {
      return resources[language][section][key as keyof typeof resources[typeof language][typeof section]];
    } catch (error) {
      console.error(`Translation not found for ${section}.${key} in language ${language}`);
      return key;
    }
  };

  return (
    <LanguageContext.Provider value={{ language, setLanguage, t }}>
      {children}
    </LanguageContext.Provider>
  );
};

export default LanguageContext;