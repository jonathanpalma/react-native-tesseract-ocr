package com.reactlibrary;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class TesseractOcrModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Handler handler = new Handler();
    private TessBaseAPI tesseract;

    private static final String KEY_ALLOW_LIST = "allowlist";
    private static final String KEY_DENY_LIST = "denylist";
    private static final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + File.separator;
	  private static final String DATA_FILES_DIRECTORY = "tessdata";

    public TesseractOcrModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        if (!this.DATA_PATH.contains(reactContext.getPackageName())) {
            this.DATA_PATH += reactContext.getPackageName() + File.separator;
        }
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
            promise.resolve("Recognition was canceled");
        } catch (Exception e) {
            Log.e(getName(), e.toString());
            promise.reject("An error occurred when trying to stop recognition", e.toString());
        }
    }

    @SuppressLint("StaticFieldLeak")
    @ReactMethod
    public void recognize(String path, String lang, @Nullable ReadableMap options, final Callback callback, final Promise promise) {
        Log.d(getName(), "recognize");

        try {
            prepareTesseract();
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);

            new Thread() {
                @Override
                public void run() {
                    tesseract = new TessBaseAPI(new TessBaseAPI.ProgressNotifier() {
                        @Override
                        public void onProgressValues(TessBaseAPI.ProgressValues progressValues) {
                            Log.d("progress " + progressValues.getPercent().toString());
                            callback(progressValues.getPercent());
                        }
                    });
                    tesseract.init(DATA_PATH, lang);
                    tesseract.setImage(bitmap);
                    tesseract.getHOCRText(0);

                    String recognizedText = tesseract.getUTF8Text();

                    tesseract.end();
                    promise.resolve(recognizedText);
                }

            }.start();

        } catch (Exception e) {
            Log.e(getName(), e.getMessage());
            promise.reject("Could not recognize text", e.toString());
        }
    }

    private void prepareDirectory(String path) {
        Log.d(getName(), "prepare directory");

        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.e(getName(), "Could not create directory (" + path + "), please make sure the app is allowed to write to external storage.");
            }
        } else {
            Log.d(getName(), "Created directory " + path);
        }
    }

    private void prepareTesseract() {
        Log.d(getName(), "prepare tesseract environment");

        try {
            prepareDirectory(DATA_PATH + DATA_FILES_DIRECTORY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        copyTessDataFiles(DATA_FILES_DIRECTORY);
    }

    private void copyTessDataFiles(String path) {
        Log.d(getName(), "copy tesseract data files");
        try {
            String fileList[] = reactContext.getAssets().list(path);

            for (String fileName : fileList) {
                String oldPath = path + File.separator + fileName;
                String newPath = DATA_PATH + oldPath;

                if (!(new File(newPath)).exists()) {
                    InputStream in = reactContext.getAssets().open(oldPath);
                    OutputStream out = new FileOutputStream(newPath);
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    Log.d(getName(), "Copied " + oldPath + " to " + newPath);
                }
            }
        } catch (IOException e) {
            Log.e(getName(), "Unable to copy files to tessdata", e.toString());
        }
    }
}
