export interface TessOptions {
  /** List of characters you want to detect */
  allowlist?: string;
  /** List of characters you DON'T want to detect */
  denylist?: string;
  /** Token level for recognizeTokens method */
  level?: 'block' | 'line' | 'paragraph' | 'symbol' | 'word';
}

export interface Token {
  /** The recognized text token */
  token: string;
  /** Confidence level of the recognition (0-100) */
  confidence: number;
  /** Bounding box coordinates */
  bounding: {
    bottom: number;
    left: number;
    right: number;
    top: number;
  };
}

export interface ProgressEvent {
  /** Progress percentage (0-100) */
  percent: number;
}

export type ProgressListener = (event: ProgressEvent) => void;

// Language constants
export const LANGUAGES = {
  AFRIKAANS: 'afr',
  AMHARIC: 'amh',
  ARABIC: 'ara',
  ASSAMESE: 'asm',
  AZERBAIJANI: 'aze',
  BELARUSIAN: 'bel',
  BOSNIAN: 'bos',
  BULGARIAN: 'bul',
  CHINESE_SIMPLIFIED: 'chi_sim',
  CHINESE_TRADITIONAL: 'chi_tra',
  CROATIAN: 'hrv',
  CUSTOM: 'custom',
  DANISH: 'dan',
  ENGLISH: 'eng',
  ESTONIAN: 'est',
  FRENCH: 'fra',
  GALICIAN: 'glg',
  GERMAN: 'deu',
  HEBREW: 'heb',
  HUNGARIAN: 'hun',
  ICELANDIC: 'isl',
  INDONESIAN: 'ind',
  IRISH: 'gle',
  ITALIAN: 'ita',
  JAPANESE: 'jpn',
  KOREAN: 'kor',
  LATIN: 'lat',
  LITHUANIAN: 'lit',
  NEPALI: 'nep',
  NORWEGIAN: 'nor',
  PERSIAN: 'fas',
  POLISH: 'pol',
  PORTUGUESE: 'por',
  RUSSIAN: 'rus',
  SERBIAN: 'srp',
  SLOVAK: 'slk',
  SPANISH: 'spa',
  SWEDISH: 'swe',
  TURKISH: 'tur',
  UKRAINIAN: 'ukr',
  VIETNAMESE: 'vie',
} as const;

export type Language = typeof LANGUAGES[keyof typeof LANGUAGES];

// Level constants
export const LEVELS = {
  BLOCK: 'block',
  LINE: 'line',
  PARAGRAPH: 'paragraph',
  SYMBOL: 'symbol',
  WORD: 'word',
} as const;

export type Level = typeof LEVELS[keyof typeof LEVELS];