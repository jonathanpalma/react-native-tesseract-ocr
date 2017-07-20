/**
 * Sample React Native Tesseract OCR App
 * https://github.com/jonathanpalma/react-native-tesseract-ocr
 * @author  Jonathan Palma <tanpalma04@gmail.com>
 * @flow
 */
'use strict';
import React, { Component } from 'react';
import {
  ActivityIndicator,
  Image,
  Platform,
  PixelRatio,
  StyleSheet,
  Text,
  TouchableNativeFeedback,
  TouchableOpacity,
  View
} from 'react-native';
import ImagePicker from 'react-native-image-picker';
import RNTesseractOcr from 'react-native-tesseract-ocr';

const Button = (Platform.OS === 'android') ? TouchableNativeFeedback : TouchableOpacity;
const options = {
  quality: 1.0,
  storageOptions: {
    skipBackup: true
  }
};
const tessOptions = {
  whitelist: null,
  blacklist: '1234567890\'!"#$%&/()={}[]+*-_:;<>'
};

class App extends Component {
  state = { isLoading: false, imgSource: null, ocrResult: null };

  selectPhoto() {
    this.setState({ isLoading: true });
    ImagePicker.showImagePicker(options, (response) => {
      if (response.didCancel || response.error) {
        this.setState({ isLoading: false });
      } else {
        let source = (Platform.OS === 'android') ? { uri: response.uri, isStatic: true } : { uri: response.uri.replace('file://', ''), isStatic: true };
        this.setState({ imgSource: source }, this.doOcr(response.path));
      }
    });
  }

  doOcr(path) {
    RNTesseractOcr.recognize(path, 'LANG_ENGLISH', tessOptions)
      .then((result) => {
        this.setState({ isLoading: false, ocrResult: result });
        console.log('OCR Result: ', result);
      })
      .catch((err) => {
        console.log('OCR Error: ', err);
      })
      .done();
  }

  render() {
    return (
      <View style={styles.container}>
        <Button onPress={this.selectPhoto.bind(this)} >
          <View style={[styles.img, styles.imgContainer, this.state.imgSource === null && styles.round]}>
            {this.state.imgSource === null ?
              <Text>Tap me!</Text>
              :
              <Image style={styles.img} source={this.state.imgSource} />
            }
          </View>
        </Button>

        {(this.state.isLoading) ?
          <ActivityIndicator
            animating={this.state.isLoading}
            size="large"
          />
          :
          null
        }
        <Text>{this.state.ocrResult}</Text>
      </View>
    );
  }
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  imgContainer: {
    borderColor: '#9B9B9B',
    borderWidth: 1 / PixelRatio.get(),
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20
  },
  img: {
    width: 150,
    height: 150
  },
  round: {
    borderRadius: 75,
  }
});

export default App;
