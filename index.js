import { useEffect } from "react";
import { NativeModules, DeviceEventEmitter } from "react-native";

export const LANG_AFRIKAANS = "afr";
export const LANG_AMHARIC = "amh";
export const LANG_ARABIC = "ara";
export const LANG_ASSAMESE = "asm";
export const LANG_AZERBAIJANI = "aze";
export const LANG_BELARUSIAN = "bel";
export const LANG_BOSNIAN = "bos";
export const LANG_BULGARIAN = "bul";
export const LANG_CHINESE_SIMPLIFIED = "chi_sim";
export const LANG_CHINESE_TRADITIONAL = "chi_tra";
export const LANG_CROATIAN = "hrv";
export const LANG_CUSTOM = "custom";
export const LANG_DANISH = "dan";
export const LANG_ENGLISH = "eng";
export const LANG_ESTONIAN = "est";
export const LANG_FRENCH = "fra";
export const LANG_GALICIAN = "glg";
export const LANG_GERMAN = "deu";
export const LANG_HEBREW = "heb";
export const LANG_HUNGARIAN = "hun";
export const LANG_ICELANDIC = "isl";
export const LANG_INDONESIAN = "ind";
export const LANG_IRISH = "gle";
export const LANG_ITALIAN = "ita";
export const LANG_JAPANESE = "jpn";
export const LANG_KOREAN = "kor";
export const LANG_LATIN = "lat";
export const LANG_LITHUANIAN = "lit";
export const LANG_NEPALI = "nep";
export const LANG_NORWEGIAN = "nor";
export const LANG_PERSIAN = "fas";
export const LANG_POLISH = "pol";
export const LANG_PORTUGUESE = "por";
export const LANG_RUSSIAN = "rus";
export const LANG_SERBIAN = "srp";
export const LANG_SLOVAK = "slk";
export const LANG_SPANISH = "spa";
export const LANG_SWEDISH = "swe";
export const LANG_TURKISH = "tur";
export const LANG_UKRAINIAN = "ukr";
export const LANG_VIETNAMESE = "vie";
export const LEVEL_BLOCK = "block";
export const LEVEL_LINE = "line";
export const LEVEL_PARAGRAPH = "paragraph";
export const LEVEL_SYMBOL = "symbol";
export const LEVEL_WORD = "word";

export function useEventListener(eventType, listener) {
  useEffect(() => {
    const subscription = DeviceEventEmitter.addListener(eventType, listener);
    return () => {
      subscription.remove();
    };
  });
}

const { TesseractOcr } = NativeModules;

export default TesseractOcr;
