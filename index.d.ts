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
    | "hrv"
    | "custom"
    | "dan"
    | "eng"
    | "est"
    | "fra"
    | "glg"
    | "deu"
    | "heb"
    | "hun"
    | "isl"
    | "ind"
    | "gle"
    | "ita"
    | "jpn"
    | "kor"
    | "lat"
    | "lit"
    | "nep"
    | "nor"
    | "fas"
    | "pol"
    | "por"
    | "rus"
    | "srp"
    | "slk"
    | "spa"
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
    stop(): void;
  }

  const TesseractOcr: ITesseractOcrModule;
  export default TesseractOcr;
}
