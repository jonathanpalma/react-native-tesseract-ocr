package com.reactlibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.googlecode.tesseract.android.ResultIterator;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TesseractOcrModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private static final String KEY_ALLOW_LIST = "allowlist";
    private static final String KEY_DENY_LIST = "denylist";
    private static final String KEY_TOKEN_LEVEL = "level";
    private static final String TESS_FILES_DIRECTORY = "tessdata";
    private static final String TESS_FILES_EXTENSION = ".traineddata";
    private static String DATA_PATH = Environment.getExternalStorageDirectory().toString();
    private static String TESS_FILES_PATH;
    private TessBaseAPI tesseract;

    public TesseractOcrModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        if (!this.DATA_PATH.contains(reactContext.getPackageName())) {
            this.DATA_PATH += File.separator + reactContext.getPackageName();
        }
        this.TESS_FILES_PATH = this.DATA_PATH + File.separator + this.TESS_FILES_DIRECTORY;
    }

    @Override
    public String getName() {
        return "TesseractOcr";
    }

    @ReactMethod
    public void stop(Promise promise) {
        Log.d(getName(), "stop");

        try {
            tesseract.stop();
            tesseract.end();
            promise.resolve("Recognition cancelled successfully");
        } catch (Exception e) {
            Log.e(getName(), "Could not stop recognition. " + e.toString(), e);
            promise.reject("Could not stop recognition", e.toString());
        }
    }

    @ReactMethod
    public void recognize(String imageSource, final String lang, @Nullable final ReadableMap tessOptions, final Promise promise) {
        Log.d(getName(), "recognize");

        try {
            if (shouldCopyTrainedFile(lang)) {
                prepareTrainedFilesDirectory();
                copyTrainedFile(lang);
            }

            final Bitmap bitmap = getBitmap(imageSource);

            if (bitmap != null) {
                new Thread() {
                    @Override
                    public void run() {
                        tesseract = createTesseractAPI(lang, tessOptions);
                        tesseract.setImage(bitmap);
                        tesseract.getHOCRText(0);

                        String recognizedText = tesseract.getUTF8Text();

                        tesseract.end();
                        promise.resolve(recognizedText);
                    }

                }.start();
            } else {
                throw new IOException("Could not decode a file path into a bitmap.");
            }
        } catch (IOException e) {
            Log.e(getName(), "Could not access trained files. " + e.toString(), e);
            promise.reject("Could not access trained files", e.toString());
        } catch (Exception e) {
            Log.e(getName(), "Could not recognize text. " + e.toString(), e);
            promise.reject("Could not recognize text", e.toString());
        }
    }

    @ReactMethod
    public void recognizeTokens(String imageSource, final String lang, @Nullable final ReadableMap tessOptions, final Promise promise) {
        Log.d(getName(), "recognizeTokens");

        try {
            if (shouldCopyTrainedFile(lang)) {
                prepareTrainedFilesDirectory();
                copyTrainedFile(lang);
            }

            final int iteratorLevel = getIteratorLevel(tessOptions != null ? tessOptions.getString(KEY_TOKEN_LEVEL) : "word");
            final Bitmap bitmap = getBitmap(imageSource);

            if (bitmap != null) {
                new Thread() {
                    @Override
                    public void run() {
                        tesseract = createTesseractAPI(lang, tessOptions);
                        tesseract.setImage(bitmap);
                        tesseract.getHOCRText(0);

                        WritableArray tokens = Arguments.createArray();
                        WritableMap tempMap;
                        WritableMap bounding;

                        ResultIterator iterator = tesseract.getResultIterator();
                        iterator.begin();

                        do {
                            bounding = Arguments.createMap();
                            tempMap = Arguments.createMap();
                            Rect rect = iterator.getBoundingRect(iteratorLevel);

                            bounding.putInt("bottom", rect.bottom);
                            bounding.putInt("left", rect.left);
                            bounding.putInt("right", rect.right);
                            bounding.putInt("top", rect.top);

                            tempMap.putString("token", iterator.getUTF8Text(iteratorLevel));
                            tempMap.putDouble("confidence", iterator.confidence(iteratorLevel));
                            tempMap.putMap("bounding", bounding);
                            tokens.pushMap(tempMap);
                        } while (iterator.next(iteratorLevel));

                        iterator.delete();
                        tesseract.end();
                        promise.resolve(tokens);
                    }

                }.start();
            } else {
                throw new IOException("Could not decode a file path into a bitmap.");
            }
        } catch (IOException e) {
            Log.e(getName(), "Could not access trained files. " + e.toString(), e);
            promise.reject("Could not access trained files", e.toString());
        } catch (Exception e) {
            Log.e(getName(), "Could not recognize text. " + e.toString(), e);
            promise.reject("Could not recognize text", e.toString());
        }

    }

    private TessBaseAPI createTesseractAPI(String lang, @Nullable final ReadableMap tessOptions) {
        TessBaseAPI tessBaseAPI = new TessBaseAPI(createProgressNotifier());
        tessBaseAPI.init(DATA_PATH + File.separator, lang);

        if (tessOptions != null) {

            //  Allow List - List of characters you want to detect
            if (tessOptions.hasKey(KEY_ALLOW_LIST) && tessOptions.getString(KEY_ALLOW_LIST) != null
                    && !tessOptions.getString(KEY_ALLOW_LIST).isEmpty()) {
                Log.d(getName(), KEY_ALLOW_LIST + " " + tessOptions.getString(KEY_ALLOW_LIST));
                tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, tessOptions.getString(KEY_ALLOW_LIST));
            }

            //  Deny List - List of characters you DON'T want to detect
            if (tessOptions.hasKey(KEY_DENY_LIST) && tessOptions.getString(KEY_DENY_LIST) != null
                    && !tessOptions.getString(KEY_DENY_LIST).isEmpty()) {
                Log.d(getName(), KEY_DENY_LIST + " " + tessOptions.getString(KEY_DENY_LIST));
                tessBaseAPI.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, tessOptions.getString(KEY_DENY_LIST));
            }
        }

        return tessBaseAPI;
    }

    private TessBaseAPI.ProgressNotifier createProgressNotifier() {
        return new TessBaseAPI.ProgressNotifier() {
            @Override
            public void onProgressValues(TessBaseAPI.ProgressValues progressValues) {
                Log.d(getName(), "progress " + String.valueOf(progressValues.getPercent()));
                onProgress(progressValues.getPercent());
            }
        };
    }

    private void onProgress(int percent) {
        Log.d(getName(), "onProgressChange " + Integer.toString(percent));
        WritableMap payload = Arguments.createMap();
        payload.putInt("percent", percent);
        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onProgressChange", payload);
    }

    private int getIteratorLevel(String level) {
        switch (level) {
            case "block":
                return TessBaseAPI.PageIteratorLevel.RIL_BLOCK;
            case "paragraph":
                return TessBaseAPI.PageIteratorLevel.RIL_PARA;
            case "symbol":
                return TessBaseAPI.PageIteratorLevel.RIL_SYMBOL;
            case "line":
                return TessBaseAPI.PageIteratorLevel.RIL_TEXTLINE;
            default: // word
                return TessBaseAPI.PageIteratorLevel.RIL_WORD;
        }
    }

    private Bitmap getBitmap(String imageSource) throws Exception {
        String path = imageSource.startsWith("file://") ? imageSource.replace("file://", "") : imageSource;

        if (path.startsWith("http://") || path.startsWith("https://")) {
            // TODO: support remote files
            throw new Exception("Cannot select remote files");
        }

        return BitmapFactory.decodeFile(path, new BitmapFactory.Options());
    }

    private boolean shouldCopyTrainedFile(String lang) {
        Log.d(getName(), "should copy " + lang + " trained files?");
        String filePath = TESS_FILES_PATH + File.separator + lang + TESS_FILES_EXTENSION;
        File file = new File(filePath);
        return !file.exists();
    }

    private void prepareTrainedFilesDirectory() throws IOException {
        Log.d(getName(), "prepare trained files directory");
        File dir = new File(TESS_FILES_PATH);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(getName(), "Could not create directory, please make sure the app has write permission");
                throw new IOException("Could not create directory");
            }
        }
    }

    private void copyTrainedFile(String lang) throws IOException {
        Log.d(getName(), "copy tesseract data file (" + lang + ")");
        String assetPath = TESS_FILES_DIRECTORY + File.separator + lang + TESS_FILES_EXTENSION;
        String newAssetPath = DATA_PATH + File.separator + assetPath;
        copyAsset(assetPath, newAssetPath);
    }

    private void copyAsset(String from, String to) throws IOException {
        Log.d(getName(), "copy asset " + from + " to " + to);

        InputStream in = reactContext.getAssets().open(from);
        OutputStream out = new FileOutputStream(to);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
}
