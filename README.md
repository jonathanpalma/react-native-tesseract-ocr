<div align="center">
  <h1>react-native-tesseract-ocr 👀</h1>
  <p>
  react-native-tesseract-ocr is a react-native wrapper for <a href="https://github.com/tesseract-ocr">Tesseract OCR</a>
  </p>
</div>

[![Version][version-badge]][package]
[![Install Size][size-badge]][package-size]
[![Downloads][downloads-badge]][npmcharts]
[![PRs Welcome][prs-badge]][prs]
[![Commitizen friendly][cz-badge]][cz]
[![MIT License][license-badge]][license]

[![Watch on GitHub][github-watch-badge]][github-watch]
[![Star on GitHub][github-star-badge]][github-star]
[![Tweet][twitter-badge]][twitter]

This project uses:

- [tess-two][url-tess-and] for Android
- [Tesseract-OCR-iOS][url-tess-ios] for iOS ⚠️ (This has NOT been implemented yet) ⚠️

NOTE: It is recommended to use react-native => 0.60.0

## Getting started

`$ npm install react-native-tesseract-ocr --save`

### Mostly automatic installation

`$ react-native link react-native-tesseract-ocr`

## Usage

```javascript
import TesseractOcr from "react-native-tesseract-ocr";

// TODO: Define new example
TesseractOcr;
```

## Contributing

### How to contribute?

This is a `commitizen friendly` repository, so instead of creating commits using `git commit`, please use our custom CLI by running:

`$ npm run cz`

#### Formatting & Linting

Make sure you have installed the following plugins on your Code Editor

- [ESLint][url-eslint]
- [Prettier][url-prettier]

## License

MIT © [jonathanpalma](https://github.com/jonathanpalma)

This library wouldn't be possible without these amazing projects:

- [Tesseract OCR][url-tesseract] - [Apache 2.0 license][url-tesseract-lsc]
- [tess-two][url-tess-and] - [Apache 2.0 license][url-tess-and-lsc]
<!-- - [Tesseract-OCR-iOS][url-tess-ios] - [MIT license][url-tess-ios-lsc] -->

[downloads-badge]: https://img.shields.io/npm/dm/react-native-tesseract-ocr.svg?style=flat-square
[license-badge]: https://img.shields.io/npm/l/react-native-tesseract-ocr.svg?style=flat-square
[license]: https://github.com/jonathanpalma/react-native-tesseract-ocr/blob/master/LICENSE
[npmcharts]: http://npmcharts.com/compare/react-native-tesseract-ocr
[package-size]: https://packagephobia.now.sh/result?p=react-native-tesseract-ocr
[package]: https://www.npmjs.com/package/react-native-tesseract-ocr
[prs-badge]: https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square
[prs]: http://makeapullrequest.com
[cz-badge]: https://img.shields.io/badge/commitizen-friendly-brightgreen.svg?style=flat-square
[cz]: http://commitizen.github.io/cz-cli/
[size-badge]: https://flat.badgen.net/packagephobia/install/react-native-tesseract-ocr
[version-badge]: https://img.shields.io/npm/v/react-native-tesseract-ocr.svg?style=flat-square
[github-watch-badge]: https://img.shields.io/github/watchers/jonathanpalma/react-native-tesseract-ocr.svg?style=social
[github-watch]: https://github.com/jonathanpalma/react-native-tesseract-ocr/watchers
[github-star-badge]: https://img.shields.io/github/stars/jonathanpalma/react-native-tesseract-ocr.svg?style=social
[github-star]: https://github.com/jonathanpalma/react-native-tesseract-ocr/stargazers
[url-eslint]: https://eslint.org/
[url-prettier]: https://prettier.io/
[url-tesseract]: https://github.com/tesseract-ocr/tesseract
[url-tesseract-lsc]: https://github.com/tesseract-ocr/tesseract/blob/master/LICENSE
[url-tess-and]: https://github.com/rmtheis/tess-two
[url-tess-and-lsc]: https://github.com/rmtheis/tess-two/blob/master/COPYING
[url-tess-ios]: https://github.com/gali8/Tesseract-OCR-iOS
[url-tess-ios-lsc]: https://github.com/gali8/Tesseract-OCR-iOS/blob/master/LICENSE.md
[twitter]: https://twitter.com/intent/tweet?text=Check%20out%20react-native-tesseract-ocr!%20https://github.com/jonathanpalma/react-native-tesseract-ocr
[twitter-badge]: https://img.shields.io/twitter/url/https/github.com/jonathanpalma/react-native-tesseract-ocr.svg?style=social
