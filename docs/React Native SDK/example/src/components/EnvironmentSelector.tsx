import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import DropDownPicker from 'react-native-dropdown-picker';
import { Environment, EnvironmentService } from '../services/EnvironmentService';

interface EnvironmentSelectorProps {
  selectedEnvironment: Environment;
  setSelectedEnvironment: (environment: Environment) => void;
  label?: string;
}

/**
 * A dropdown component for selecting the API environment
 */
const EnvironmentSelector: React.FC<EnvironmentSelectorProps> = ({
  selectedEnvironment,
  setSelectedEnvironment,
  label = 'API Environment',
}) => {
  const [open, setOpen] = React.useState(false);
  const [items] = React.useState(EnvironmentService.getEnvironmentOptions());

  return (
    <View style={styles.container}>
      {label && <Text style={styles.label}>{label}</Text>}
      <DropDownPicker
        open={open}
        value={selectedEnvironment}
        items={items}
        setOpen={setOpen}
        setValue={(callback) => {
          const value = typeof callback === 'function' ? callback(selectedEnvironment) : callback;
          setSelectedEnvironment(value as Environment);
        }}
        style={styles.dropdown}
        dropDownContainerStyle={styles.dropdownList}
        placeholder="Select Environment"
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: 20,
    zIndex: 1000,
  },
  label: {
    fontSize: 16,
    marginBottom: 8,
    color: '#34495e'
  },
  dropdown: {
    borderColor: '#dcdde1',
    backgroundColor: '#f9f9f9'
  },
  dropdownList: {
    borderColor: '#dcdde1',
    backgroundColor: '#ffffff'
  }
});

export default EnvironmentSelector;