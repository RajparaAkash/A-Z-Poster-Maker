package com.letsmake.atoz.design.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.billingclient.api.Purchase;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.ads.NativeAds;
import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.letsmake.atoz.design.fragments.fragment_home;
import com.letsmake.atoz.design.fragments.fragment_viewpager_templates;
import com.letsmake.atoz.design.poster_builder.Full_Poster_Thumb;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements BillingUpdatesListener {

    public ArrayList<Full_Poster_Thumb> arrayList = new ArrayList<>();

    private ViewPager pager;
    private TabLayout tabs;
    private SectionsPagerAdapter adapter;

    String cat_name = "0";
    int pos, cat_id;

    ImageView iVBack, ivPro;
    TextView tvTitle;

    boolean isActive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.layout_viewpager_templates);

        pos = getIntent().getIntExtra("position", -1);
        cat_id = getIntent().getIntExtra("cat_id", -1);
        cat_name = getIntent().getStringExtra("cateName");

        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setupWithViewPager(pager);

        tvTitle = findViewById(R.id.tvTitle);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Raleway Bold.ttf");
        tvTitle.setTypeface(custom_font);

        adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        if (fragment_home.posterDatas.get(pos).getPoster_list() != null)
            arrayList = fragment_home.posterDatas.get(pos).getPoster_list();

        for (int i = 0; i < fragment_home.posterDatas.size(); i++) {
            fragment_viewpager_templates fragment_templates_viewPager = new fragment_viewpager_templates();
            fragment_templates_viewPager.setCategory_id(Integer.parseInt(fragment_home.posterDatas.get(i).getPOSTERCat_id()));
            fragment_templates_viewPager.setPos(i);
            fragment_templates_viewPager.setCategory_Name(fragment_home.posterDatas.get(i).getPOSTERCat_name());
            adapter.AddFragment(fragment_templates_viewPager, fragment_home.posterDatas.get(i).getPOSTERCat_name());
        }

        pager.setAdapter(adapter);
        pager.setCurrentItem(pos);

        iVBack = findViewById(R.id.iVBack);
        iVBack.setOnClickListener(view -> finish());

        ivPro = findViewById(R.id.ivPro);
        YoYo.with(Techniques.Shake).duration(1000).repeat(500).playOn(ivPro);

        ivPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPagerActivity.this, SubscriptionActivity.class));
            }
        });
    }

    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }

    

    private void changeTabsFont(Typeface typeface) {
        ViewGroup vg = (ViewGroup) tabs.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }
    }

    @Override
    public void onBillingClientSetupFinished() {

    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchaseList) {
        isActive = SubscriptionsUtil.isSubscriptionActive(purchaseList);

        if (!isActive) {

            // Adaptive_Banner
            new NativeAds(this).Adaptive_Banner(findViewById(R.id.adaptive_banner));

        }
    }

    @Override
    public void onPurchaseVerified() {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> list = new ArrayList<>();
        List<String> TitleList = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void AddFragment(Fragment fragment, String Title) {
            list.add(fragment);
            TitleList.add(Title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TitleList.get(position);
        }
    }

}
