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
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-3-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->

[![Watch on GitHub][github-watch-badge]][github-watch]
[![Star on GitHub][github-star-badge]][github-star]
[![Tweet][twitter-badge]][twitter]

This project uses:

- [tess-two][url-tess-and] for Android
- [Tesseract-OCR-iOS][url-tess-ios] for iOS ⚠️ (This has NOT been implemented yet) ⚠️

NOTE: It is recommended to use react-native >= 0.60.0

## Getting started

`$ npm i react-native-tesseract-ocr --save`

### Mostly automatic installation

`$ react-native link react-native-tesseract-ocr`


## Example


<div align="center">
  <img src="https://raw.githubusercontent.com/jonathanpalma/react-native-tesseract-ocr/master/example/showcase.android.picker.gif" alt="Showcase Android using Picker" width="215" height="430" style="margin: 0px 10px" />
  <img src="https://raw.githubusercontent.com/jonathanpalma/react-native-tesseract-ocr/master/example/showcase.android.camera.gif" alt="Showcase Android using Camera" width="215" height="430" style="margin: 0px 10px" />
</div>

Check the example by yourself [here][url-example]

## Usage

### tessOptions

| Property  | Type     | Description                                                                |
| --------- | -------- | -------------------------------------------------------------------------- |
| allowlist | `string` | List of characters you want to recognize                                   |
| denylist  | `string` | List of characters you DON'T want to recognize                             |
| level     | `Level`  | Level of the tokens of the page hierarchy (only used in `recognizeTokens`) |

_`Level` can be one of the following values 'symbol' | 'block' | 'line' | 'paragraph' | 'word'_

### recognize

```typescript
import TesseractOcr, { LANG_ENGLISH } from 'react-native-tesseract-ocr';

const tessOptions = {};
TesseractOcr.recognize(imageSource, LANG_ENGLISH, tessOptions);
```

### recognizeTokens

```typescript
import TesseractOcr, { LANG_ENGLISH, LEVEL_WORD } from 'react-native-tesseract-ocr';

const tessOptions = { level: LEVEL_WORD };
TesseractOcr.recognizeTokens(imageSource, LANG_ENGLISH, tessOptions);
```


### useEventListener

```typescript
import React, { useState } from 'react';
import { useEventListener } from 'react-native-tesseract-ocr';

function App() {
  const [progress, setProgress] = useState(0);
  useEventListener('onProgressChange', (p) => {
    setProgress(p.percent / 100);
  });

  // return ...
}
```



## Contributing

### How to contribute?

This is a `commitizen friendly` repository, so instead of creating commits using `git commit`, please use our custom CLI by running:

`$ npm run cz`

## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tr>
    <td align="center"><a href="http://jonathanpalma.me"><img src="https://avatars3.githubusercontent.com/u/12414771?v=4" width="100px;" alt=""/><br /><sub><b>Jonathan Palma</b></sub></a><br /><a href="https://github.com/jonathanpalma/react-native-tesseract-ocr/commits?author=jonathanpalma" title="Code">💻</a> <a href="https://github.com/jonathanpalma/react-native-tesseract-ocr/commits?author=jonathanpalma" title="Documentation">📖</a> <a href="#example-jonathanpalma" title="Examples">💡</a></td>
    <td align="center"><a href="https://github.com/jrunestone"><img src="https://avatars3.githubusercontent.com/u/2293001?v=4" width="100px;" alt=""/><br /><sub><b>Johan Runsten</b></sub></a><br /><a href="https://github.com/jonathanpalma/react-native-tesseract-ocr/commits?author=jrunestone" title="Code">💻</a></td>
  </tr>
</table>

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!

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
[url-example]: https://github.com/jonathanpalma/react-native-tesseract-ocr/tree/master/example
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
