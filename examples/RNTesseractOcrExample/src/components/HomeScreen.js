import React, { Component } from 'react';
import { Button, Text, View } from 'react-native';
import styles from '../../styles';

const HomeScreen = ({ navigation }) => (
  <View style={styles.container}>
    <Text style={styles.title}>React Native Tesseract OCR</Text>
    <Text style={styles.instructions}>
      The following examples have been created using two of the most
      {' '}
      popular RN modules to select images from the device library or
      {' '}
      directly from the camera (both maintained by the react-native-community).
    </Text>
    <View style={styles.button}>
      <Button
        title="Camera Example"
        onPress={() => navigation.navigate('Camera')}
      />
    </View>
    <View style={styles.button}>
      <Button
        title="Image Picker Example"
        onPress={() => navigation.navigate('ImagePicker')}
        style={styles.button}
      />
    </View>
  </View>
);

HomeScreen.navigationOptions = {
  header: null,
};

export default HomeScreen;
