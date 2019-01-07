import React from 'react';
import { Text, View } from 'react-native';
import styles from '../../styles';

const CameraScreen = () => (
  <View style={styles.container}>
    <Text style={styles.title}>Camera Example</Text>
    <Text style={styles.instructions}>
      This example has not been implemented yet!
    </Text>
  </View>
);

CameraScreen.navigationOptions = {
  title: 'Camera Example',
};

export default CameraScreen;
