package com.letsmake.atoz.design.activity;

import static com.letsmake.atoz.design.ratinghelper.RatingDialog.btnSubmit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.billingclient.api.Purchase;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.ads.NativeAds;
import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.letsmake.atoz.design.fragments.fragment_create;
import com.letsmake.atoz.design.fragments.fragment_home;
import com.letsmake.atoz.design.fragments.fragment_my_design;
import com.letsmake.atoz.design.fragments.fragment_templates;
import com.letsmake.atoz.design.fragments.fragment_work_poster;
import com.letsmake.atoz.design.ratinghelper.RatingDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BillingUpdatesListener {

    private ImageView iv_proads, iv_howtostart;

    TabBarView tabbar;
    List<Tab> tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.new_activity_main);

        iv_howtostart = findViewById(R.id.iv_howtostart);
        iv_proads = findViewById(R.id.iv_proads);
        YoYo.with(Techniques.Shake).duration(1000).repeat(500).playOn(iv_proads);

        tabbar = findViewById(R.id.tabbar);

        tabs = new ArrayList<>();
        tabs.add(new Tab(this, "Home", R.drawable.ic_bottom_home));
        tabs.add(new Tab(this, "Template", R.drawable.ic_template_list));
        tabs.add(new Tab(this, "Create", R.drawable.ic_create_scratch));
        tabs.add(new Tab(this, "My Edit", R.drawable.ic_my_work));
        tabs.add(new Tab(this, "My Work", R.drawable.ic_bottom_my_design));

        tabbar.setTabClickBackground(TabBarView.TabClickBackgroundValue.RIPPLE);

        replaceFragment(new fragment_home());

        tabbar.setTab(tabs).setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                switch (index) {
                    case 0:
                        replaceFragment(new fragment_home());
                        break;

                    case 1:
                        replaceFragment(new fragment_templates());
                        break;

                    case 2:
                        replaceFragment(new fragment_create());
                        break;

                    case 3:
                        replaceFragment(new fragment_my_design());
                        break;

                    case 4:
                        replaceFragment(new fragment_work_poster());
                        break;
                }
            }
        });

        iv_proads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubscriptionActivity.class));
            }
        });

        iv_howtostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=khuedRb9vLA")));
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void ratingdialog() {
        final RatingDialog ratingDialog = new RatingDialog(this);
        ratingDialog.setRatingDialogListener(new RatingDialog.RatingDialogInterFace() {
            @Override
            public void onDismiss() {

            }

            @Override
            public void onSubmit(float rating) {
                if (rating > 3) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }

            @Override
            public void onRatingChanged(float rating) {
                YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnSubmit);
            }

            @Override
            public void onExitClicked() {
                finishAffinity();
            }
        });

        ratingDialog.showDialog();
    }

    public void showRateUsDialog() {
        ratingdialog();
    }

    @Override
    public void onBackPressed() {
        showRateUsDialog();
    }

    @Override
    public void onBillingClientSetupFinished() {

    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchaseList) {
        boolean isActive = SubscriptionsUtil.isSubscriptionActive(purchaseList);

        if (!isActive) {

            // Adaptive_Banner
            new NativeAds(this).Adaptive_Banner(findViewById(R.id.adaptive_banner));

        }
    }

    @Override
    public void onPurchaseVerified() {

    }
}
