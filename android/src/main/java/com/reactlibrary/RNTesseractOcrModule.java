
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.common.annotations.VisibleForTesting;

import com.googlecode.tesseract.android.TessBaseAPI;

public class RNTesseractOcrModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private TessBaseAPI tessBaseApi;

  @VisibleForTesting
  private static final String REACT_CLASS = "RNTesseractOcr"; 
  
  private static final String TESSDATA = "tessdata";

  private static final String LANG_GERMAN = "deu";
  private static final String LANG_ENGLISH = "eng";
  private static final String LANG_FRENCH = "fra";
  private static final String LANG_ITALIAN = "ita";
  private static final String LANG_PORTUGUESE = "por";
  private static final String LANG_SPANISH = "spa";
  private static final String LANG_SWEDISH = "swe";

  public RNTesseractOcrModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }
  
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put("LANG_GERMAN", LANG_GERMAN);
    constants.put("LANG_ENGLISH", LANG_ENGLISH);
    constants.put("LANG_FRENCH", LANG_FRENCH);
    constants.put("LANG_ITALIAN", LANG_ITALIAN);
    constants.put("LANG_PORTUGUESE", LANG_PORTUGUESE);
    constants.put("LANG_SPANISH", LANG_SPANISH);
    constants.put("LANG_SWEDISH", LANG_SWEDISH);
    return constants;
  }

  @ReactMethod
  public void startOcr(String path) {
    Log.d(REACT_CLASS, "Start ocr images");
    try{

    } catch(Exception e){
      Log.e(REACT_CLASS, "Error trying to start");
      e.printStackTrace();
    }
  }
}