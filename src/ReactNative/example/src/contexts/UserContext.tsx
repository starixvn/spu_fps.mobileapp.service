import React, {
  createContext,
  useState,
  useContext,
  type ReactNode,
} from 'react';

interface UserContextType {
  username: string;
  subscriberName: string;
  setUserCredentials: (username: string, subscriberName: string) => void;
}

const defaultUserContext: UserContextType = {
  username: '',
  subscriberName: '',
  setUserCredentials: () => {},
};

const UserContext = createContext<UserContextType>(defaultUserContext);

export const useUser = () => useContext(UserContext);

interface UserProviderProps {
  children: ReactNode;
}

export const UserProvider: React.FC<UserProviderProps> = ({ children }) => {
  const [username, setUsername] = useState('');
  const [subscriberName, setSubscriberName] = useState('');

  const setUserCredentials = (username: string, subscriberName: string) => {
    setUsername(username);
    setSubscriberName(subscriberName);
  };

  return (
    <UserContext.Provider
      value={{
        username,
        subscriberName,
        setUserCredentials,
      }}
    >
      {children}
    </UserContext.Provider>
  );
};

export default UserContext;
