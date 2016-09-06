# react-native-tesseract-ocr
**_This repository is not ready yet, but we are working hard to get a first stable version, if you want to contribute you are welcome_** 

react-native-tesseract-ocr is a react-native wrapper for [Tesseract OCR](https://github.com/tesseract-ocr) using base on
  - [tess-two](https://github.com/rmtheis/tess-two) for Android
  - [Tesseract-OCR-iOS](https://github.com/gali8/Tesseract-OCR-iOS) for iOS

## Getting started *(expectations, but not ready yet)*

`$ npm install react-native-tesseract-ocr --save` 

### Mostly automatic installation

`$ react-native link react-native-tesseract-ocr`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-tesseract-ocr` and add `RNTesseractOcr.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNTesseractOcr.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNTesseractOcrPackage;` to the imports at the top of the file
  - Add `new RNTesseractOcrPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-tesseract-ocr'
  	project(':react-native-tesseract-ocr').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-tesseract-ocr/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-tesseract-ocr')
  	```
4. [Trained data files](https://github.com/tesseract-ocr/tessdata) for a language must be 
extracted in `android/app/src/main/assets/tessdata`.

## Usage *(expectations, but not ready yet)*
```javascript
import RNTesseractOcr from 'react-native-tesseract-ocr';

// TODO: What do with the module?
RNTesseractOcr;
```

# Contribution
Contributions are welcome :raised_hands:

# License
This repository is distributed under [MIT license](https://github.com/jonathanpalma/react-native-tesseract-ocr/blob/master/LICENSE) 
 - [Tesseract OCR](https://github.com/tesseract-ocr) - maintained by Google, is distributed under [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0)
 - [tess-two](https://github.com/rmtheis/tess-two) is distributed under [Apache 2.0 license](https://github.com/rmtheis/tess-two/blob/master/COPYING)
 - [Tesseract-OCR-iOS](https://github.com/gali8/Tesseract-OCR-iOS) is distributed under [MIT license](https://github.com/gali8/Tesseract-OCR-iOS/blob/master/LICENSE.md)