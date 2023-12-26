package com.letsmake.atoz.design.imageloader;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader.Factory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class Progress_Module extends AppGlideModule {

    private static class GlideProgressModuleListener implements ResponseProgressModuleListener {
        private static final Map<String, UIonProgressModuleListener> LISTENERS = new HashMap();
        private static final Map<String, Long> PROGRESSES = new HashMap();
        private final Handler handler = new Handler(Looper.getMainLooper());

        GlideProgressModuleListener() {
        }

        static void miss(String str) {
            LISTENERS.remove(str);
            PROGRESSES.remove(str);
        }

        static void expect(String str, UIonProgressModuleListener uIonProgressModuleListener) {
            LISTENERS.put(str, uIonProgressModuleListener);
        }

        @Override
        public void update(HttpUrl httpUrl, long j, long j2) {
            String httpUrl2 = httpUrl.toString();
            UIonProgressModuleListener uIonProgressModuleListener = (UIonProgressModuleListener) LISTENERS.get(httpUrl2);
            if (uIonProgressModuleListener != null) {
                if (j2 <= j) {
                    miss(httpUrl2);
                }
                if (needsProgressDispatch(httpUrl2, j, j2, uIonProgressModuleListener.getGranualityPercentage())) {
                    final UIonProgressModuleListener uIonProgressModuleListener2 = uIonProgressModuleListener;
                    final long j3 = j;
                    final long j4 = j2;
                    this.handler.post(new Runnable() {
                        public void run() {
                            uIonProgressModuleListener2.onProgress(j3, j4);
                        }
                    });
                }
            }
        }

        private boolean needsProgressDispatch(String str, long j, long j2, float f) {
            if (f == 0.0f || j == 0 || j2 == j) {
                return true;
            }
            j = (long) (((((float) j) * 100.0f) / ((float) j2)) / f);
            Long l = (Long) PROGRESSES.get(str);
            if (l != null && j == l) {
                return false;
            }
            PROGRESSES.put(str, j);
            return true;
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
        registry.replace(GlideUrl.class, InputStream.class, new Factory(new Builder().addNetworkInterceptor(new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response proceed = chain.proceed(request);
                return proceed.newBuilder().body(new OkHttpProgressModuleResponseBody(request.url(), proceed.body(), new GlideProgressModuleListener())).build();
            }
        }).build()));
    }


    private interface ResponseProgressModuleListener {
        void update(HttpUrl httpUrl, long j, long j2);
    }

    public interface UIonProgressModuleListener {
        float getGranualityPercentage();

        void onProgress(long j, long j2);
    }

    public boolean isManifestParsingEnabled() {
        return false;
    }


    public static void forget(String str) {
        GlideProgressModuleListener.miss(str);
    }

    public static void expect(String str, UIonProgressModuleListener uIonProgressModuleListener) {
        GlideProgressModuleListener.expect(str, uIonProgressModuleListener);
    }

    private static class OkHttpProgressModuleResponseBody extends ResponseBody {
        private BufferedSource bufferedSource;
        public final ResponseProgressModuleListener progressListener;
        public final ResponseBody responseBody;
        public final HttpUrl url;

        OkHttpProgressModuleResponseBody(HttpUrl httpUrl, ResponseBody responseBody, ResponseProgressModuleListener responseProgressModuleListener) {
            this.url = httpUrl;
            this.responseBody = responseBody;
            this.progressListener = responseProgressModuleListener;
        }

        public MediaType contentType() {
            return this.responseBody.contentType();
        }

        public long contentLength() {
            return this.responseBody.contentLength();
        }

        public BufferedSource source() {
            if (this.bufferedSource == null) {
                this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
            }
            return this.bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0;

                public long read(Buffer buffer, long j) throws IOException {
                    long read = super.read(buffer, j);
                    long contentLength = OkHttpProgressModuleResponseBody.this.responseBody.contentLength();
                    if (read == -1) {
                        this.totalBytesRead = contentLength;
                    } else {
                        this.totalBytesRead += read;
                    }
                    OkHttpProgressModuleResponseBody.this.progressListener.update(OkHttpProgressModuleResponseBody.this.url, this.totalBytesRead, contentLength);
                    return read;
                }
            };
        }
    }

}