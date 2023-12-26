package com.letsmake.atoz.design.activity;

import static com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver.isConnected;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.ads.InterstitialAdsGoogle;
import com.letsmake.atoz.design.ads.InterstitialAdsSplash;
import com.letsmake.atoz.design.ads.MyDataSaved;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

    AppPreferenceClass appPreferenceClass;

    ImageView imgLogo;
    TextView tvTagline;

    MyDataSaved myDataSaved;
    public static AppOpenAd.AppOpenAdLoadCallback appOpenAdLoadCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.new_activity_splash);

        appPreferenceClass = new AppPreferenceClass(this);

        myDataSaved = new MyDataSaved(this);
        if (isConnected()) {
            getdata();
        } else {
            networkError();
        }

        tvTagline = findViewById(R.id.tvTagline);
        Typeface custom_title = Typeface.createFromAsset(getAssets(), "font/cabin.ttf");
        tvTagline.setTypeface(custom_title);
        tvTagline.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_slide_up));

        imgLogo = findViewById(R.id.imgLogo);
        imgLogo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bubble));
    }

    public void getdata() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.googleusercontent.com/macros/echo?user_content_key=tg_MV0u2czureCuaeLijZzyceEEKoswR8LmxW0d3WWj53Zr4v3nogw1oEX0MSnX2JFZL69ckc86e9mYgCBj2qMTap7T4Gmedm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnKc0gr789jBV-0ZWjXyMH6y4SS0-h4aZju1uVqkb3gpNU_iTpzRcbe7wTuoaJ2O-tWxKUJpccLSiFMd1euXBhYNXe4r9LV9ARtz9Jw9Md8uu&lib=M-uwUIplcRNceTGemOKNEffDV560l0pgM", response -> {

            try {
                JSONObject object = new JSONObject(response);
                JSONArray jsonArray = new JSONArray(object.getString("app"));

                if (!jsonArray.getJSONObject(0).getString("ad_status").isEmpty() && !jsonArray.getJSONObject(0).getString("ad_status").equals("null")) {
                    myDataSaved.set_ad_status(jsonArray.getJSONObject(0).getString("ad_status"));
                }
                if (!jsonArray.getJSONObject(0).getString("click_flag").isEmpty() && !jsonArray.getJSONObject(0).getString("click_flag").equals("null")) {
                    myDataSaved.set_click_flag(jsonArray.getJSONObject(0).getString("click_flag"));
                }
                if (!jsonArray.getJSONObject(0).getString("ad_style").isEmpty() && !jsonArray.getJSONObject(0).getString("ad_style").equals("null")) {
                    myDataSaved.set_ad_style(jsonArray.getJSONObject(0).getString("ad_style"));
                }
                if (!jsonArray.getJSONObject(0).getString("ad_time").isEmpty() && !jsonArray.getJSONObject(0).getString("ad_time").equals("null")) {
                    myDataSaved.set_ad_time(jsonArray.getJSONObject(0).getString("ad_time"));
                }
                if (!jsonArray.getJSONObject(0).getString("splash_ad_style").isEmpty() && !jsonArray.getJSONObject(0).getString("splash_ad_style").equals("null")) {
                    myDataSaved.set_splash_ad_style(jsonArray.getJSONObject(0).getString("splash_ad_style"));
                }
                if (!jsonArray.getJSONObject(0).getString("ad_click").isEmpty() && !jsonArray.getJSONObject(0).getString("ad_click").equals("null")) {
                    myDataSaved.set_ad_click(jsonArray.getJSONObject(0).getString("ad_click"));
                }
                if (!jsonArray.getJSONObject(0).getString("google_appopen").isEmpty() && !jsonArray.getJSONObject(0).getString("google_appopen").equals("null")) {
                    myDataSaved.set_google_appopen(jsonArray.getJSONObject(0).getString("google_appopen"));
                }
                if (!jsonArray.getJSONObject(0).getString("google_interstitial").isEmpty() && !jsonArray.getJSONObject(0).getString("google_interstitial").equals("null")) {
                    myDataSaved.set_google_interstitial(jsonArray.getJSONObject(0).getString("google_interstitial"));
                }
                if (!jsonArray.getJSONObject(0).getString("google_native").isEmpty() && !jsonArray.getJSONObject(0).getString("google_native").equals("null")) {
                    myDataSaved.set_google_native(jsonArray.getJSONObject(0).getString("google_native"));
                }
                if (!jsonArray.getJSONObject(0).getString("google_banner").isEmpty() && !jsonArray.getJSONObject(0).getString("google_banner").equals("null")) {
                    myDataSaved.set_google_banner(jsonArray.getJSONObject(0).getString("google_banner"));
                }
                if (!jsonArray.getJSONObject(0).getString("google_reward").isEmpty() && !jsonArray.getJSONObject(0).getString("google_reward").equals("null")) {
                    myDataSaved.set_google_reward(jsonArray.getJSONObject(0).getString("google_reward"));
                }

                if (myDataSaved.get_ad_status().equalsIgnoreCase("on")) {
                    InterstitialAdsGoogle.loadGoogleInterstitialAd(SplashActivity.this);
                }
                nextGo();

            } catch (JSONException e) {
                e.printStackTrace();
                nextGo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            nextGo();
        });

        requestQueue.add(stringRequest);
    }

    public void networkError() {
        Toast.makeText(this, "No Internet connected?", Toast.LENGTH_SHORT).show();
    }

    public void nextGo() {
        if (myDataSaved.get_ad_status().equalsIgnoreCase("on")) {
            if (myDataSaved.get_splash_ad_style().equalsIgnoreCase("open")) {
                AppOpenAdMethod();
            } else {
                new InterstitialAdsSplash().Show_Ads(SplashActivity.this, new InterstitialAdsSplash.AdCloseListener() {
                    @Override
                    public void onAdClosed() {
                        intentHome();
                    }
                });
            }
        } else {
            intentHome();
        }
    }

    private void AppOpenAdMethod() {
        try {
            String string = myDataSaved.get_google_appopen();
            try {
                appOpenAdLoadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                intentHome();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                intentHome();
                            }
                        };
                        appOpenAd.show(SplashActivity.this);
                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);

                        new InterstitialAdsSplash().Show_Ads(SplashActivity.this, new InterstitialAdsSplash.AdCloseListener() {
                            @Override
                            public void onAdClosed() {
                                intentHome();
                            }
                        });
                    }
                };
                AppOpenAd.load(this, string, new AdRequest.Builder().build(), 1, appOpenAdLoadCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void intentHome() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
