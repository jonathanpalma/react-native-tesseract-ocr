/**
 * ./index.js
 * @author  Jonathan Palma <tanpalma04@gmail.com> 
 */
'use strict';
import React, { Component } from 'react';
import { Image, Platform, PixelRatio, StyleSheet, Text, TouchableNativeFeedback, TouchableOpacity, View } from 'react-native';
import ImagePicker from 'react-native-image-picker';
import RNTesseractOcr from 'react-native-tesseract-ocr';

var Button = (Platform.OS === 'android') ? TouchableNativeFeedback : TouchableOpacity;

export default class App extends React.Component {
  constructor(props, context){
    super(props, context);
    this.state = { imgSource: null, ocrResult: null };
  }

  selectPhoto(){
    const options = {
      quality: 1.0,
      maxWidth: 500,
      maxHeight: 500,
      storageOptions: {
        skipBackup: true
      }
    };

    ImagePicker.showImagePicker(options, (response) => {
      console.log('Response = ', response);

      if (response.didCancel) {
        console.log('User cancelled photo picker');
      }
      else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      }
      else if (response.customButton) {
        console.log('User tapped custom button: ', response.customButton);
      }
      else {
        var source;

        if (Platform.OS === 'android') {
          source = {uri: response.uri, isStatic: true};
        } else {
          source = {uri: response.uri.replace('file://', ''), isStatic: true};
        }

        this.setState({ imgSource: source });
        
        RNTesseractOcr.startOcr(response.path, "LANG_ENGLISH")
          .then((result) => {
            this.setState({ ocrResult: result });
            console.log("OCR Result: ", result);
          })
          .catch((err) => {
            console.log("OCR Error: ", err);
          })
          .done();
      }
    });
  }

  render() {
    return(
      <View style={styles.container}>
        <Button onPress={this.selectPhoto.bind(this)} >
          <View style={[styles.img, styles.imgContainer, {marginBottom: 20}]}>
          { this.state.imgSource === null ? <Text>Select a Photo</Text> :
            <Image style={styles.img} source={this.state.imgSource} />
          }
          </View>
        </Button>

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
    alignItems: 'center'
  },
  img: {
    borderRadius: 75,
    width: 150,
    height: 150
  }
});

module.exports = App;