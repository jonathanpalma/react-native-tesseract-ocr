
package com.reactlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.common.annotations.VisibleForTesting;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.util.HashMap;
import java.util.Map;

public class RNTesseractOcrModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private TessBaseAPI tessBaseApi;

  @VisibleForTesting
  private static final String REACT_CLASS = "RNTesseractOcr"; 
  
  private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/" + REACT_CLASS + "/";
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
  public void startOcr(String path, String lang, Promise promise) {
    Log.d(REACT_CLASS, "Start ocr images");
    
    try{
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inSampleSize = 4; //inSampleSize documentation --> http://goo.gl/KRrlvi
      Bitmap bitmap = BitmapFactory.decodeFile(path, options);

      String result = extractText(bitmap, lang);

      promise.resolve(result);

    } catch(Exception e){
      Log.e(REACT_CLASS, e.getMessage());
      promise.reject("An error occurred", e.getMessage());
    }
  }


  private String extractText(Bitmap bitmap, String lang) {
    try {
      tessBaseApi = new TessBaseAPI();
    } catch (Exception e) {
      Log.e(REACT_CLASS, e.getMessage());
      if (tessBaseApi == null) {
        Log.e(REACT_CLASS, "TessBaseAPI is null. TessFactory not returning tess object.");
      }
    }

    tessBaseApi.init(DATA_PATH, getConstants().get(lang).toString());

//  //EXTRA SETTINGS (Future implementation) 
// 
//  //Whitelist - List of characters you want to detect
//  //Example: You just want to detect digits (0-9)
//  tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
//  
//  //Blacklist - List of characters you DON'T want to detect
//  //Example: You DON'T want to detect some special characters
//  tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-[]}{;:'\"\\|~`,./<>?");
    
    Log.d(REACT_CLASS, "Training file loaded");

    tessBaseApi.setImage(bitmap);

    String extractedText = "Empty result";
    try {
        extractedText = tessBaseApi.getUTF8Text();
    } catch (Exception e) {
        Log.e(REACT_CLASS, "Error in recognizing text: " + e.getMessage());
    }

    tessBaseApi.end();

    return extractedText;
  }
}