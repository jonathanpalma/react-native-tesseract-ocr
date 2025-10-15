import {useEffect} from 'react';
import {NativeModules, DeviceEventEmitter} from 'react-native';
import type {TessOptions, Token, ProgressListener, ProgressEvent} from './types';

export * from './types';

// Legacy exports for backwards compatibility
export const LANG_AFRIKAANS = 'afr';
export const LANG_AMHARIC = 'amh';
export const LANG_ARABIC = 'ara';
export const LANG_ASSAMESE = 'asm';
export const LANG_AZERBAIJANI = 'aze';
export const LANG_BELARUSIAN = 'bel';
export const LANG_BOSNIAN = 'bos';
export const LANG_BULGARIAN = 'bul';
export const LANG_CHINESE_SIMPLIFIED = 'chi_sim';
export const LANG_CHINESE_TRADITIONAL = 'chi_tra';
export const LANG_CROATIAN = 'hrv';
export const LANG_CUSTOM = 'custom';
export const LANG_DANISH = 'dan';
export const LANG_ENGLISH = 'eng';
export const LANG_ESTONIAN = 'est';
export const LANG_FRENCH = 'fra';
export const LANG_GALICIAN = 'glg';
export const LANG_GERMAN = 'deu';
export const LANG_HEBREW = 'heb';
export const LANG_HUNGARIAN = 'hun';
export const LANG_ICELANDIC = 'isl';
export const LANG_INDONESIAN = 'ind';
export const LANG_IRISH = 'gle';
export const LANG_ITALIAN = 'ita';
export const LANG_JAPANESE = 'jpn';
export const LANG_KOREAN = 'kor';
export const LANG_LATIN = 'lat';
export const LANG_LITHUANIAN = 'lit';
export const LANG_NEPALI = 'nep';
export const LANG_NORWEGIAN = 'nor';
export const LANG_PERSIAN = 'fas';
export const LANG_POLISH = 'pol';
export const LANG_PORTUGUESE = 'por';
export const LANG_RUSSIAN = 'rus';
export const LANG_SERBIAN = 'srp';
export const LANG_SLOVAK = 'slk';
export const LANG_SPANISH = 'spa';
export const LANG_SWEDISH = 'swe';
export const LANG_TURKISH = 'tur';
export const LANG_UKRAINIAN = 'ukr';
export const LANG_VIETNAMESE = 'vie';

export const LEVEL_BLOCK = 'block';
export const LEVEL_LINE = 'line';
export const LEVEL_PARAGRAPH = 'paragraph';
export const LEVEL_SYMBOL = 'symbol';
export const LEVEL_WORD = 'word';

interface TesseractOcrModule {
  /**
   * Recognizes text in an image and returns the raw text string
   * @param imageSource - Path to the image file (local file path)
   * @param lang - Language code for OCR recognition
   * @param tessOptions - Optional configuration for OCR
   * @returns Promise that resolves to the recognized text
   */
  recognize(imageSource: string, lang: string, tessOptions?: TessOptions): Promise<string>;

  /**
   * Recognizes text in an image and returns tokens with bounding boxes
   * @param imageSource - Path to the image file (local file path)
   * @param lang - Language code for OCR recognition
   * @param tessOptions - Optional configuration for OCR including token level
   * @returns Promise that resolves to an array of tokens with bounding boxes
   */
  recognizeTokens(
    imageSource: string,
    lang: string,
    tessOptions?: TessOptions,
  ): Promise<Token[]>;

  /**
   * Stops the current OCR recognition process
   * @returns Promise that resolves to a success message
   */
  stop(): Promise<string>;
}

const {TesseractOcr} = NativeModules;

// Type assertion for the native module
const TesseractOcrTyped = TesseractOcr as TesseractOcrModule;

/**
 * React hook for listening to OCR progress events
 * @param eventType - Event type to listen for (e.g., 'onProgressChange')
 * @param listener - Callback function to handle the event
 */
export function useEventListener(eventType: string, listener: ProgressListener): void {
  useEffect(() => {
    const subscription = DeviceEventEmitter.addListener(
      eventType,
      (event: ProgressEvent) => {
        listener(event);
      },
    );
    return () => {
      subscription.remove();
    };
  }, [eventType, listener]);
}

export default TesseractOcrTyped;