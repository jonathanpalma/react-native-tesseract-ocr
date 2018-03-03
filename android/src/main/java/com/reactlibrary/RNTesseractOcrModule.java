
package com.reactlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.annotations.VisibleForTesting;

import com.googlecode.tesseract.android.ResultIterator;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RNTesseractOcrModule extends ReactContextBaseJavaModule {

	private final ReactApplicationContext reactContext;
	private TessBaseAPI tessBaseApi;

	@VisibleForTesting
	private static final String REACT_CLASS = "RNTesseractOcr";

	private static String DATA_PATH = Environment.getExternalStorageDirectory().toString() + File.separator;
	private static final String TESSDATA = "tessdata";

	private static final String LANG_AFRIKAANS = "afr";
	private static final String LANG_AMHARIC = "amh";
	private static final String LANG_ARABIC = "ara";
	private static final String LANG_ASSAMESE = "asm";
	private static final String LANG_AZERBAIJANI = "aze";
	private static final String LANG_BELARUSIAN = "bel";
	private static final String LANG_BOSNIAN = "bos";
	private static final String LANG_BULGARIAN = "bul";
	private static final String LANG_CHINESE_SIMPLIFIED = "chi_sim";
	private static final String LANG_CHINESE_TRADITIONAL = "chi_tra";
	private static final String LANG_CROATIAN = "hrv";
	private static final String LANG_CUSTOM = "custom";
	private static final String LANG_DANISH = "dan";
	private static final String LANG_ENGLISH = "eng";
	private static final String LANG_ESTONIAN = "est";
	private static final String LANG_FRENCH = "fra";
	private static final String LANG_GALICIAN = "glg";
	private static final String LANG_GERMAN = "deu";
	private static final String LANG_HEBREW = "heb";
	private static final String LANG_HUNGARIAN = "hun";
	private static final String LANG_ICELANDIC = "isl";
	private static final String LANG_INDONESIAN = "ind";
	private static final String LANG_IRISH = "gle";
	private static final String LANG_ITALIAN = "ita";
	private static final String LANG_JAPANESE = "jpn";
	private static final String LANG_KOREAN = "kor";
	private static final String LANG_LATIN = "lat";
	private static final String LANG_LITHUANIAN = "lit";
	private static final String LANG_NEPALI = "nep";
	private static final String LANG_NORWEGIAN = "nor";
	private static final String LANG_PERSIAN = "fas";
	private static final String LANG_POLISH = "pol";
	private static final String LANG_PORTUGUESE = "por";
	private static final String LANG_RUSSIAN = "rus";
	private static final String LANG_SERBIAN = "srp";
	private static final String LANG_SLOVAK = "slk";
	private static final String LANG_SPANISH = "spa";
	private static final String LANG_SWEDISH = "swe";
	private static final String LANG_TURKISH = "tur";
	private static final String LANG_UKRAINIAN = "ukr";
	private static final String LANG_VIETNAMESE = "vie";

	public RNTesseractOcrModule(ReactApplicationContext reactContext) {
		super(reactContext);
		this.reactContext = reactContext;
		if (!this.DATA_PATH.contains(reactContext.getPackageName()))
			this.DATA_PATH += reactContext.getPackageName() + File.separator;
	}

	@Override
	public String getName() {
		return REACT_CLASS;
	}

	@Override
	public Map<String, Object> getConstants() {
		final Map<String, Object> constants = new HashMap<>();
		constants.put("LANG_AFRIKAANS", LANG_AFRIKAANS);
		constants.put("LANG_AMHARIC", LANG_AMHARIC);
		constants.put("LANG_ARABIC", LANG_ARABIC);
		constants.put("LANG_ASSAMESE", LANG_ASSAMESE);
		constants.put("LANG_AZERBAIJANI", LANG_AZERBAIJANI);
		constants.put("LANG_BELARUSIAN", LANG_BELARUSIAN);
		constants.put("LANG_BOSNIAN", LANG_BOSNIAN);
		constants.put("LANG_BULGARIAN", LANG_BULGARIAN);
		constants.put("LANG_CHINESE_SIMPLIFIED", LANG_CHINESE_SIMPLIFIED);
		constants.put("LANG_CHINESE_TRADITIONAL", LANG_CHINESE_TRADITIONAL);
		constants.put("LANG_CROATIAN", LANG_CROATIAN);
		constants.put("LANG_CUSTOM", LANG_CUSTOM);
		constants.put("LANG_DANISH", LANG_DANISH);
		constants.put("LANG_ENGLISH", LANG_ENGLISH);
		constants.put("LANG_ESTONIAN", LANG_ESTONIAN);
		constants.put("LANG_FRENCH", LANG_FRENCH);
		constants.put("LANG_GALICIAN", LANG_GALICIAN);
		constants.put("LANG_GERMAN", LANG_GERMAN);
		constants.put("LANG_HEBREW", LANG_HEBREW);
		constants.put("LANG_HUNGARIAN", LANG_HUNGARIAN);
		constants.put("LANG_ICELANDIC", LANG_ICELANDIC);
		constants.put("LANG_INDONESIAN", LANG_INDONESIAN);
		constants.put("LANG_IRISH", LANG_IRISH);
		constants.put("LANG_ITALIAN", LANG_ITALIAN);
		constants.put("LANG_JAPANESE", LANG_JAPANESE);
		constants.put("LANG_KOREAN", LANG_KOREAN);
		constants.put("LANG_LATIN", LANG_LATIN);
		constants.put("LANG_LITHUANIAN", LANG_LITHUANIAN);
		constants.put("LANG_NEPALI", LANG_NEPALI);
		constants.put("LANG_NORWEGIAN", LANG_NORWEGIAN);
		constants.put("LANG_PERSIAN", LANG_PERSIAN);
		constants.put("LANG_POLISH", LANG_POLISH);
		constants.put("LANG_PORTUGUESE", LANG_PORTUGUESE);
		constants.put("LANG_RUSSIAN", LANG_RUSSIAN);
		constants.put("LANG_SERBIAN", LANG_SERBIAN);
		constants.put("LANG_SLOVAK", LANG_SLOVAK);
		constants.put("LANG_SPANISH", LANG_SPANISH);
		constants.put("LANG_SWEDISH", LANG_SWEDISH);
		constants.put("LANG_TURKISH", LANG_TURKISH);
		constants.put("LANG_UKRAINIAN", LANG_UKRAINIAN);
		constants.put("LANG_VIETNAMESE", LANG_VIETNAMESE);

		return constants;
	}

	@ReactMethod
	public void stop(Promise promise) {
		try {
			tessBaseApi.stop();
			promise.resolve("Recognition was canceled");
		} catch (Exception e) {
			Log.e(REACT_CLASS, e.toString());
			promise.reject("An error occurred", e.getMessage());
		}
	}

	@Deprecated
	@ReactMethod
	public void startOcr(String path, String lang, Promise promise) {
		this.recognize(path, lang, null, promise);
	}

	@ReactMethod
	public void recognize(String path, String lang, @Nullable ReadableMap tessOptions, Promise promise) {
		prepareTesseract();

		Log.d(REACT_CLASS, "Start ocr images");

		try {
			BitmapFactory.Options options = new BitmapFactory.Options();

			// TODO:
			// Check image size before use inSampleSize (maybe this could help) --> https://goo.gl/4MvBvB
			// considering that when inSampleSize is used (usually to save memory) the ocr quality decreases

			//options.inSampleSize = 4; //inSampleSize documentation --> http://goo.gl/KRrlvi
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);

			String result = extractText(bitmap, lang, tessOptions);

			promise.resolve(result);
		} catch (Exception e) {
			Log.e(REACT_CLASS, e.getMessage());
			promise.reject("An error occurred", e.getMessage());
		}
	}

	@ReactMethod
	public void recognizeWithBounds(String path, String lang, @Nullable ReadableMap tessOptions, Promise promise) {
		prepareTesseract();

		Log.d(REACT_CLASS, "Start ocr images with bounds");

		try {
			BitmapFactory.Options options = new BitmapFactory.Options();

			// TODO:
			// Check image size before use inSampleSize (maybe this could help) --> https://goo.gl/4MvBvB
			// considering that when inSampleSize is used (usually to save memory) the ocr quality decreases

			//options.inSampleSize = 4; //inSampleSize documentation --> http://goo.gl/KRrlvi
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);

			WritableArray result = extractTextWithBounds(bitmap, lang, TessBaseAPI.PageIteratorLevel.RIL_WORD, tessOptions);

			promise.resolve(result);
		} catch (Exception e) {
			Log.e(REACT_CLASS, e.getMessage());
			promise.reject("An error occurred", e.getMessage());
		}
	}

	private String extractText(Bitmap bitmap, String lang, @Nullable final ReadableMap tessOptions) {
		createTesseractApi(lang, tessOptions);

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

	private WritableArray extractTextWithBounds(Bitmap bitmap, String lang, int level, @Nullable final ReadableMap tessOptions) {
		createTesseractApi(lang, tessOptions);

		tessBaseApi.setImage(bitmap);
		tessBaseApi.getUTF8Text();

		WritableArray words = new WritableNativeArray();
		ResultIterator it = tessBaseApi.getResultIterator();
		it.begin();

		do {
			String word = it.getUTF8Text(level);
			Rect region = it.getBoundingRect(level);
			WritableMap map = new WritableNativeMap();

			map.putString("text", word);
			map.putInt("x1", region.left);
			map.putInt("y1", region.top);
			map.putInt("x2", region.right);
			map.putInt("y2", region.bottom);

			words.pushMap(map);
		} while (it.next(level));

		it.delete();

		return words;
	}

	private void createTesseractApi(String lang, @Nullable final ReadableMap tessOptions) {
		try {
			tessBaseApi = new TessBaseAPI();
		} catch (Exception e) {
			Log.e(REACT_CLASS, e.getMessage());
			if (tessBaseApi == null) {
				Log.e(REACT_CLASS, "TessBaseAPI is null. TessFactory is not returning tess object.");
			}
		}

		tessBaseApi.init(DATA_PATH, getConstants().get(lang).toString());

		if (tessOptions != null) {

			//  //Whitelist - List of characters you want to detect
			if (tessOptions.hasKey("whitelist") && tessOptions.getString("whitelist") != null
					&& !tessOptions.getString("whitelist").isEmpty()) {
				Log.d(REACT_CLASS, "Whitelist: " + tessOptions.getString("whitelist"));
				tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, tessOptions.getString("whitelist"));
			}

			//  //Blacklist - List of characters you DON'T want to detect
			if (tessOptions.hasKey("blacklist") && tessOptions.getString("blacklist") != null
					&& !tessOptions.getString("blacklist").isEmpty()) {
				Log.d(REACT_CLASS, "Blacklist: " + tessOptions.getString("blacklist"));
				tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, tessOptions.getString("blacklist"));
			}
		}

		Log.d(REACT_CLASS, "Training file loaded");
	}

	private void prepareDirectory(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				Log.e(REACT_CLASS, "ERROR: Creation of directory " + path
						+ " failed, check permission to write to external storage.");
			}
		} else {
			Log.i(REACT_CLASS, "Created directory " + path);
		}
	}

	private void prepareTesseract() {
		Log.d(REACT_CLASS, "Preparing tesseract enviroment");

		try {
			prepareDirectory(DATA_PATH + TESSDATA);
		} catch (Exception e) {
			e.printStackTrace();
		}

		copyTessDataFiles(TESSDATA);
	}

	private void copyTessDataFiles(String path) {
		try {
			String fileList[] = reactContext.getAssets().list(path);

			for (String fileName : fileList) {

				String pathToDataFile = DATA_PATH + path + "/" + fileName;
				if (!(new File(pathToDataFile)).exists()) {

					InputStream in = reactContext.getAssets().open(path + "/" + fileName);

					OutputStream out = new FileOutputStream(pathToDataFile);

					byte[] buf = new byte[1024];
					int len;

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();

					Log.d(REACT_CLASS, "Copied " + fileName + "to tessdata");
				}
			}
		} catch (IOException e) {
			Log.e(REACT_CLASS, "Unable to copy files to tessdata " + e.toString());
		}
	}
}