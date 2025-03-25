import { Text, View, StyleSheet } from 'react-native';
import { multiply, onResetFPSLastLiveTime } from 'fps';

const result = multiply(3, 7);
onResetFPSLastLiveTime();

export default function App() {
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
