declare module "react-native-tesseract-ocr" {
  export type Lang =
    | "amh"
    | "ara"
    | "asm"
    | "aze"
    | "bel"
    | "bos"
    | "bul"
    | "chi_sim"
    | "chi_tra"
    | "custom"
    | "dan"
    | "deu"
    | "eng"
    | "est"
    | "fas"
    | "fra"
    | "gle"
    | "glg"
    | "heb"
    | "hrv"
    | "hun"
    | "ind"
    | "isl"
    | "ita"
    | "jpn"
    | "kor"
    | "lat"
    | "lit"
    | "nep"
    | "nor"
    | "pol"
    | "por"
    | "rus"
    | "slk"
    | "spa"
    | "srp"
    | "swe"
    | "tur"
    | "ukr"
    | "vie";
  export type Options = {
    allowlist?: string;
    denylist?: string;
    onProgress?: (percent: number) => void;
  };

  export interface ITesseractOcrModule {
    clear(): void;
    recognize(path: string, lang: Lang, options?: Options): Promise<string>;
    stop(): Promise<string>;
  }

  const TesseractOcr: ITesseractOcrModule;
  export default TesseractOcr;
}
