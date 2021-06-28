declare module "react-native-tesseract-ocr" {
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
  export const LANG_NEDERLAND = "nld"
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

  export type Lang =
    | typeof LANG_AFRIKAANS
    | typeof LANG_AMHARIC
    | typeof LANG_ARABIC
    | typeof LANG_ASSAMESE
    | typeof LANG_AZERBAIJANI
    | typeof LANG_BELARUSIAN
    | typeof LANG_BOSNIAN
    | typeof LANG_BULGARIAN
    | typeof LANG_CHINESE_SIMPLIFIED
    | typeof LANG_CHINESE_TRADITIONAL
    | typeof LANG_CROATIAN
    | typeof LANG_CUSTOM
    | typeof LANG_DANISH
    | typeof LANG_ENGLISH
    | typeof LANG_ESTONIAN
    | typeof LANG_FRENCH
    | typeof LANG_GALICIAN
    | typeof LANG_GERMAN
    | typeof LANG_HEBREW
    | typeof LANG_HUNGARIAN
    | typeof LANG_ICELANDIC
    | typeof LANG_INDONESIAN
    | typeof LANG_IRISH
    | typeof LANG_ITALIAN
    | typeof LANG_JAPANESE
    | typeof LANG_KOREAN
    | typeof LANG_LATIN
    | typeof LANG_LITHUANIAN
    | typeof LANG_NEPALI
    | typeof LANG_NEDERLAND
    | typeof LANG_NORWEGIAN
    | typeof LANG_PERSIAN
    | typeof LANG_POLISH
    | typeof LANG_PORTUGUESE
    | typeof LANG_RUSSIAN
    | typeof LANG_SERBIAN
    | typeof LANG_SLOVAK
    | typeof LANG_SPANISH
    | typeof LANG_SWEDISH
    | typeof LANG_TURKISH
    | typeof LANG_UKRAINIAN
    | typeof LANG_VIETNAMESE;

  export type Level =
    | typeof LEVEL_BLOCK
    | typeof LEVEL_LINE
    | typeof LEVEL_PARAGRAPH
    | typeof LEVEL_SYMBOL
    | typeof LEVEL_WORD;

  export type Options = {
    allowlist?: string;
    denylist?: string;
    level?: Level; // only required for recognizeTokens
  };

  export type Bounding = {
    bottom: number;
    left: number;
    right: number;
    top: number;
  };

  export type Token = {
    token: string;
    confidence: number;
    bounding: Bounding;
  };

  export interface ITesseractOcrModule {
    clear(): void;
    recognize(
      imageSource: string,
      lang: Lang,
      options?: Options
    ): Promise<string>;
    recognizeTokens(
      imageSource: string,
      lang: Lang,
      options?: Options
    ): Promise<string>;
    stop(): Promise<Token[]>;
  }

  export type ProgressData = {
    percent: number;
  };
  export type EventListener = (data: ProgressData) => void;
  export type EventType = "onProgressChange";
  export function useEventListener(
    eventType: EventType,
    listener: EventListener
  ): void;

  const TesseractOcr: ITesseractOcrModule;
  export default TesseractOcr;
}
