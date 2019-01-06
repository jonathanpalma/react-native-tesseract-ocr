/**
 * Sample React Native Tesseract OCR App
 * https://github.com/jonathanpalma/react-native-tesseract-ocr
 * @author  Jonathan Palma <tanpalma04@gmail.com>
 * @flow
 */
import { createStackNavigator, createAppContainer } from 'react-navigation';
import CameraScreen from './CameraScreen';
import HomeScreen from './HomeScreen';
import ImagePickerScreen from './ImagePickerScreen';

const AppNavigator = createStackNavigator(
  {
    Camera: CameraScreen,
    Home: HomeScreen,
    ImagePicker: ImagePickerScreen,
  },
  {
    initialRouteName: 'Home'
  }
);

export default createAppContainer(AppNavigator);