import { NativeModules } from "react-native";

const { TesseractOcr } = NativeModules;

TesseractOcr.recognize = (path, lang, { onProgress, ...rest }) =>
  TesseractOcr.recognize(path, lang, rest, onProgress);

export default TesseractOcr;
