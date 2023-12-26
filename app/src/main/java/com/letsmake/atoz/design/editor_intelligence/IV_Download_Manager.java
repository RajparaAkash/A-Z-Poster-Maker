package com.letsmake.atoz.design.editor_intelligence;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IV_Download_Manager {

    public static final String DOT = ".";
    public static final String FORWARD_SLASH = "/";
    public static final Object LOCK = new Object();
    public static final String LOG_TAG = "IV_Download_Manager";
    public static ArrayList<String> downloadImages;
    public static IV_Download_Manager sInstance;
    public Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());
    public HashMap<IV_DownloadTask, Callback> callbacks = new HashMap();
    public Context context;
    public ThreadPoolExecutor threadPoolExecutor;

    public IV_Download_Manager(Context context) {
        if (sInstance == null) {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            this.context = context.getApplicationContext();
            int i = availableProcessors * 2;
            this.threadPoolExecutor = new ThreadPoolExecutor(i, i, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
            return;
        }
        throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
    }

    public static IV_Download_Manager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new IV_Download_Manager(context);
                }
            }
        }
        return sInstance;
    }

    private class IV_Download_Runnable implements Runnable {
        IV_DownloadTask imageDownloadTask;

        IV_Download_Runnable(IV_DownloadTask imageDownloadTask) {
            this.imageDownloadTask = imageDownloadTask;
            if (imageDownloadTask == null) {
                throw new InvalidParameterException("IVTask is null");
            }
        }

        @Override
        public void run() {
            switch (this.imageDownloadTask.IVTask) {
                case DELETE:
                    IV_Download_Manager.this.deleteTempFolder(new File(this.imageDownloadTask.folderPath));
                    IV_Download_Manager.this.post_Success(this.imageDownloadTask);
                    return;
                case DOWNLOAD:
                    download_Images_From_URI(this.imageDownloadTask);
                    return;
                default:
                    return;
            }
        }

        private void download_Images_From_URI(IV_DownloadTask imageDownloadTask) {
            for (String str : imageDownloadTask.urls) {
                Bitmap access$200 = IV_Download_Manager.this.start_IV_Download(str);
                if (access$200 == null) {
                    IV_Download_Manager.this.post_Failure(this.imageDownloadTask, ImageSaveFailureReason.NETWORK);
                    return;
                } else if (!IV_Download_Manager.exportBitmap_Image(access$200, this.imageDownloadTask.folderPath, str)) {
                    IV_Download_Manager.this.post_Failure(imageDownloadTask, ImageSaveFailureReason.FILE);
                    return;
                }
            }
            IV_Download_Manager.this.post_Success(imageDownloadTask);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IV_Download_Runnable) {
                return this.imageDownloadTask.equals(((IV_Download_Runnable) obj).imageDownloadTask);
            }
            return super.equals(obj);
        }
    }


    public interface Extensions {
        public static final String JPEG = "jpeg";
        public static final String PNG = "png";
        public static final String WEBP = "webp";
    }

    public interface Callback {
        void onFailure(ImageSaveFailureReason imageSaveFailureReason);

        void onSuccess(IV_DownloadTask imageDownloadTask, ArrayList<String> arrayList);
    }



    public enum ImageSaveFailureReason {
        NETWORK,
        FILE
    }

    public enum IVTask {
        DOWNLOAD,
        DELETE
    }


    public void addDownloadTask(IV_DownloadTask imageDownloadTask) {
        if (this.callbacks.containsKey(imageDownloadTask)) {
            Log.e(LOG_TAG, "Have another IVTask to process with same Tag. Rejecting");
            return;
        }
        this.threadPoolExecutor.execute(new IV_Download_Runnable(imageDownloadTask));
        this.callbacks.put(imageDownloadTask, imageDownloadTask.callback.get());
    }

    public void post_Success(final IV_DownloadTask imageDownloadTask) {
        final Callback callback = (Callback) imageDownloadTask.callback.get();
        if (callback != null) {
            this.MAIN_HANDLER.post(new Runnable() {
                public void run() {
                    callback.onSuccess(imageDownloadTask, IV_Download_Manager.downloadImages);
                }
            });
        }
        this.callbacks.remove(imageDownloadTask);
    }

    public void post_Failure(IV_DownloadTask imageDownloadTask, final ImageSaveFailureReason imageSaveFailureReason) {
        final Callback callback = (Callback) imageDownloadTask.callback.get();
        if (callback != null) {
            this.MAIN_HANDLER.post(new Runnable() {
                public void run() {
                    callback.onFailure(imageSaveFailureReason);
                }
            });
        }
        this.callbacks.remove(imageDownloadTask);
    }


    public void deleteTempFolder(File file) {
        try {
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.list().length == 0) {
                file.delete();
            } else {
                for (String file2 : file.list()) {
                    deleteTempFolder(new File(file, file2));
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } catch (Exception unused) {
        }
    }

    public static String get_FileName_From_Url(String str) {
        return str.substring(str.lastIndexOf(FORWARD_SLASH), str.length());
    }

    public static String get_HashCode_Based_FileName(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.hashCode());
        stringBuilder.append(DOT);
        stringBuilder.append(get_File_Extension(get_FileName_From_Url(str)));
        return stringBuilder.toString();
    }

    public static class IV_DownloadTask {

        private Object tag;
        List<String> urls;
        WeakReference<Callback> callback;
        String folderPath;
        IVTask IVTask;

        public IV_DownloadTask(Object obj, IVTask IVTask, List<String> list, String str, Callback callback) {
            this.tag = obj;
            this.IVTask = IVTask;
            this.urls = list;
            this.folderPath = str;
            this.callback = new WeakReference(callback);
            IV_Download_Manager.downloadImages = new ArrayList();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IV_DownloadTask) {
                return this.tag.equals(((IV_DownloadTask) obj).tag);
            }
            return super.equals(obj);
        }

        public int hashCode() {
            return this.tag.hashCode();
        }
    }


    public static String get_File_Extension(String str) {
        String str2 = "";
        if (str == null || str.isEmpty()) {
            return str2;
        }
        int lastIndexOf = str.lastIndexOf(DOT);
        if (lastIndexOf != -1 && lastIndexOf < str.length()) {
            str2 = str.substring(lastIndexOf + 1);
        }
        return str2;
    }


    public static CompressFormat find_CompressFormat_From_FileName(String str) {
        Object obj;
        str = get_File_Extension(str);
        int hashCode = str.hashCode();
        CompressFormat bmp = null;
        if (hashCode == 3268712) {
            if (str.equals(Extensions.JPEG)) {
                bmp = CompressFormat.JPEG;
            }
        } else if (hashCode == 3645340 && str.equals(Extensions.WEBP)) {
            bmp = CompressFormat.WEBP;
        } else {
            bmp = CompressFormat.PNG;
        }
        return bmp;
    }


    public static boolean exportBitmap_Image(Bitmap bitmap, String str, String str2) {
        File file = new File(str);
        if (!file.exists() && !file.mkdir()) {
            return false;
        }
        try {
            str2 = get_HashCode_Based_FileName(str2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(FORWARD_SLASH);
            stringBuilder.append(str2);
            str = stringBuilder.toString();
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmap.compress(find_CompressFormat_From_FileName(str2), 100, fileOutputStream);
            fileOutputStream.close();
            downloadImages.add(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Bitmap start_IV_Download(String str) {
        try {
            return Glide.with(this.context).asBitmap().load(Uri.parse(str)).apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).skipMemoryCache(true)).submit().get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
