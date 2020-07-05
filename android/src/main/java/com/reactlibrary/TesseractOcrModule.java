package com.reactlibrary;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
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

    @SuppressLint("StaticFieldLeak")
    @ReactMethod
    public void recognize(String imagePath, final String lang, @Nullable ReadableMap tessOptions, final Callback callback, final Promise promise) {
        Log.d(getName(), "recognize");

        try {
            if (shouldCopyTrainedFile(lang)) {
                prepareTrainedFilesDirectory();
                copyTrainedFile(lang);
            }

            final BitmapFactory.Options options = new BitmapFactory.Options();
            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            new Thread() {
                @Override
                public void run() {
                    tesseract = new TessBaseAPI(new TessBaseAPI.ProgressNotifier() {
                        @Override
                        public void onProgressValues(TessBaseAPI.ProgressValues progressValues) {
                            Log.d(getName(), "progress " + String.valueOf(progressValues.getPercent()));
                            callback.invoke(progressValues.getPercent());
                        }
                    });
                    tesseract.init(DATA_PATH + File.separator, lang);
                    tesseract.setImage(bitmap);
                    tesseract.getHOCRText(0);

                    String recognizedText = tesseract.getUTF8Text();

                    tesseract.end();
                    promise.resolve(recognizedText);
                }

            }.start();

        } catch (IOException e) {
            Log.e(getName(), "Could not access trained files. " + e.toString(), e);
            promise.reject("Could not access trained files", e.toString());
        } catch (Exception e) {
            Log.e(getName(), "Could not recognize text. " + e.toString(), e);
            promise.reject("Could not recognize text", e.toString());
        }
    }

    private boolean shouldCopyTrainedFile(String lang) {
        Log.d(getName(), "should copy trained files?");

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
        InputStream in = reactContext.getAssets().open(from);
        OutputStream out = new FileOutputStream(to);
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();

        Log.d(getName(), "Copied " + from + " to " + to);
    }
}
