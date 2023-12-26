package com.letsmake.atoz.design.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.utils.LogUtil;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionActivity extends AppCompatActivity
        implements View.OnClickListener, BillingUpdatesListener {


    private static final String TAG = "SubscriptionActivity";
    TextView txt_month, txt_6month, txt_year;
    View month_selected, sixmonth_select, year_selected;
    LinearLayout linear_monthly;
    LinearLayout linear_6month;
    LinearLayout linear_year;
    Button txt_buy;
    private BillingClient billingClient;

    SkuDetails skuDetails_final;
    public int final_sku;
    List<SkuDetails> skuDetailsList_final = new ArrayList<>();

    String video_name = "untitled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_subscription);

        VideoView videoView = (VideoView) findViewById(R.id.player_view);
        videoView.setVisibility(View.VISIBLE);
        String uriPath = "android.resource://" + getPackageName() + "/raw/" + video_name;
        Uri UrlPath = Uri.parse(uriPath);
        videoView.setMediaController(null);
        videoView.setVideoURI(UrlPath);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    videoView.requestFocus();
                    videoView.start();
                } catch (Exception e) {
                    System.out.printf("Error playing video %s\n", e);
                }
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        findViewById(R.id.actionCancel).setOnClickListener(this);
        txt_month = findViewById(R.id.txt_month);
        txt_6month = findViewById(R.id.txt_6month);
        txt_year = findViewById(R.id.txt_year);

        month_selected = findViewById(R.id.month_selected);
        sixmonth_select = findViewById(R.id.sixmonth_select);
        year_selected = findViewById(R.id.year_selected);

        linear_monthly = findViewById(R.id.linear_monthly);
        linear_6month = findViewById(R.id.linear_6month);
        linear_year = findViewById(R.id.linear_year);

        txt_buy = findViewById(R.id.txt_buy);

        txt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getpurchase(final_sku);
            }
        });

        linear_monthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_selected.setVisibility(View.VISIBLE);
                sixmonth_select.setVisibility(View.GONE);
                year_selected.setVisibility(View.GONE);
                final_sku = 0;
            }
        });

        linear_6month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_selected.setVisibility(View.GONE);
                sixmonth_select.setVisibility(View.VISIBLE);
                year_selected.setVisibility(View.GONE);
                final_sku = 1;
            }
        });

        linear_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_selected.setVisibility(View.GONE);
                sixmonth_select.setVisibility(View.GONE);
                year_selected.setVisibility(View.VISIBLE);
                final_sku = 2;
            }
        });

        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(
                        new PurchasesUpdatedListener() {
                            @Override
                            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                                    for (Purchase purchase : list) {
                                        verifySubPurchase(purchase);
                                    }
                                }
                            }
                        }
                ).build();
        establishConnection();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.actionCancel:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void establishConnection() {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    showProducts();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                establishConnection();
            }
        });
    }

    void showProducts() {
        List<String> skuList = new ArrayList<>();
        skuList.add("poster_yearly");
        skuList.add("poster_6month");
        skuList.add("poster_1month");
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                            for (SkuDetails skuDetails : skuDetailsList) {
                                if (skuDetails.getSku().equals("poster_1month")) {
                                    txt_month.setText(skuDetails.getPrice());
                                    //getpurchase(skuDetails);

                                } else if (skuDetails.getSku().equals("poster_6month")) {
                                    txt_6month.setText(skuDetails.getPrice());
                                    // getpurchase(skuDetails);
                                } else if (skuDetails.getSku().equals("poster_yearly")) {
                                    txt_year.setText(skuDetails.getPrice());
                                    //getpurchase(skuDetails);
                                }
                            }
                            skuDetailsList_final = skuDetailsList;
                        }
                    }
                });
    }

    private void getpurchase(int final_sku) {

        skuDetails_final = skuDetailsList_final.get(final_sku);

        launchPurchaseFlow(skuDetails_final);
    }

    void launchPurchaseFlow(SkuDetails skuDetails) {
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        billingClient.launchBillingFlow(SubscriptionActivity.this, billingFlowParams);
    }

    void verifySubPurchase(Purchase purchases) {
        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchases.getPurchaseToken())
                .build();

        billingClient.acknowledgePurchase(acknowledgePurchaseParams, new AcknowledgePurchaseResponseListener() {
            @Override
            public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Toast.makeText(SubscriptionActivity.this, "Please Restart application", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(BillingClient.SkuType.SUBS,
                new PurchasesResponseListener() {
                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            for (Purchase purchase : list) {
                                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                    verifySubPurchase(purchase);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onBillingClientSetupFinished() {

        Toast.makeText(this, "FINISHED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchases) {
        boolean isActive = SubscriptionsUtil.isSubscriptionActive(purchases);
        LogUtil.logDebug(TAG, "onPurchasesUpdated: " + isActive);

        if (isActive) {
            Intent mainIntent = new Intent(SubscriptionActivity.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainIntent);
            finish();
        }
    }

    @Override
    public void onPurchaseVerified() {
        Intent mainIntent = new Intent(SubscriptionActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        finish();
    }

}
