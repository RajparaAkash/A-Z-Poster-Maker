package com.letsmake.atoz.design.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.Purchase;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsmake.atoz.design.activity.ViewPagerActivity;
import com.letsmake.atoz.design.ads.InterstitialAds;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;

import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.model.HomeModel;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.letsmake.atoz.design.Application;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.poster_builder.Key_Poster;
import com.letsmake.atoz.design.poster_builder.Poster_Data_List;
import com.letsmake.atoz.design.poster_builder.Poster_With_List;
import com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver;
import com.thekhaeng.pushdownanim.PushDownAnim;


public class fragment_home extends Fragment implements MaterialSearchBar.OnSearchActionListener, BillingUpdatesListener {

    private static final String TAG = "PosterCatActivity";

    public static ArrayList<Poster_Data_List> posterDatas = new ArrayList();

    public static String appkey = "";

    private String ratio = "0";
    private int catID = 0;

    private ArrayList<HomeModel> categorieList, tempList;
    private TextView tvTitle;

    public boolean isActive;

    private Activity activity;

    private RecyclerView recycleViewForHomeActivity;
    private HomeCardAdapter homeCardAdapter;

    private MaterialSearchBar searchBar;

    private static final int RC_SIGN_IN = 9001;

    public SweetAlertDialog pDialog;
    AppPreferenceClass appPreferenceClass;

    

    

    public void setupProgress() {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#D81B60"));
        pDialog.setTitleText("Initializing...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_fragment_home, container, false);

        activity = getActivity();

        

        tvTitle = view.findViewById(R.id.tvTitle);
        Typeface custom_title = Typeface.createFromAsset(activity.getAssets(), "font/cabin.ttf");
        tvTitle.setTypeface(custom_title);

        appPreferenceClass = new AppPreferenceClass(getActivity());

        initRecyclerView(view);

        //  ImageView IvOffer = view.findViewById(R.id.iv_offer);
        //  Glide.with(getActivity()).asGif().load(R.drawable.upgrade_premium).into(IvOffer);
/*
        IvOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SubscriptionActivity.class));
            }
        });
*/

        if (posterDatas.size() == 0) {
            if (NetworkConnectivityReceiver.isConnected()) {
                setupProgress();
                getPosKeyAndCall1();
            } else {
                networkError();
            }
        } else {
            setupAdapter();
        }

        searchBar = view.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (homeCardAdapter != null)
                    homeCardAdapter.updateData(tempList, searchBar.getText().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }


    public void getPosKeyAndCall1() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppConstants.BASE_URL_POSTER);
        stringBuilder.append("apps_key");
        Application.getInstance().addToRequestQueue(new StringRequest(1, stringBuilder.toString(), new Response.Listener<String>() {
            public void onResponse(String str) {
                try {
                    str = (new Gson().fromJson(str, Key_Poster.class)).getKey();
                    appkey = str;
                    getPosterList(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error: ");
                stringBuilder.append(volleyError.getMessage());
                Log.e(str, stringBuilder.toString());
                AppConstants.BASE_URL_POSTER = AppConstants.BASE_URL_POSTER_SECOND;
                AppConstants.BASE_URL_STICKER = AppConstants.BASE_URL_STICKER_SECOND;
                AppConstants.BASE_URL_BG = AppConstants.BASE_URL_BG_SECOND;
                AppConstants.BASE_URL = AppConstants.BASE_URL_SECOND;
                AppConstants.stickerURL = AppConstants.stickerURL_SECOND;
                AppConstants.fURL = AppConstants.fURL_SECOND;
                AppConstants.bgURL = AppConstants.bgURL_SECOND;
                Application.getInstance().cancelPendingRequests(TAG);

                //   getPosKeyAndCall1();
                restartApp();

            }
        }) {
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("device", "1");
                return hashMap;
            }
        }, TAG);
    }

    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }

    public void getPosterList(String str) {
        final String str2 = str;
        Application.getInstance().addToRequestQueue(new StringRequest(1, "http://bhargav.fadootutorial.com/apipro/swiperCat2", new Response.Listener<String>() {
            public void onResponse(String str) {

                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }

                posterDatas.clear();
                try {
                    try {
                        Iterator it = (new Gson().fromJson(str, Poster_With_List.class)).getData().iterator();
                        while (it.hasNext()) {
                            posterDatas.add((Poster_Data_List) it.next());
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("==");
                        stringBuilder.append(posterDatas.size());
                        Log.e("pos cat ArrayList", stringBuilder.toString());
                        setupAdapter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error: ");
                stringBuilder.append(volleyError.getMessage());
                Log.e(str, stringBuilder.toString());
                AppConstants.BASE_URL_POSTER = AppConstants.BASE_URL_POSTER_SECOND;
                AppConstants.BASE_URL_STICKER = AppConstants.BASE_URL_STICKER_SECOND;
                AppConstants.BASE_URL_BG = AppConstants.BASE_URL_BG_SECOND;
                AppConstants.BASE_URL = AppConstants.BASE_URL_SECOND;
                AppConstants.stickerURL = AppConstants.stickerURL_SECOND;
                AppConstants.fURL = AppConstants.fURL_SECOND;
                AppConstants.bgURL = AppConstants.bgURL_SECOND;
                Application.getInstance().cancelPendingRequests(TAG);

                restartApp();
                //   getPosKeyAndCall1();
            }
        }) {
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("key", str2);
                hashMap.put("device", "1");
                hashMap.put("cat_id", String.valueOf(catID));
                hashMap.put("package", "com.postermakerflyerdesigner");
                hashMap.put("ratio", ratio);
                return hashMap;
            }
        }, TAG);
    }

    public void initRecyclerView(View view) {
        categorieList = new ArrayList<HomeModel>();

        recycleViewForHomeActivity = view.findViewById(R.id.recycleViewForHomeActivity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycleViewForHomeActivity.setLayoutManager(layoutManager);
    }

    public void setupAdapter() {

        categorieList = new ArrayList<>();
        tempList = new ArrayList<>();
        for (int i = 0; i < posterDatas.size(); i++) {

            HomeModel homeModel = new HomeModel();
            homeModel.setIcon(posterDatas.get(i).getPOSTERThumb_img());
            homeModel.setTitle(posterDatas.get(i).getPOSTERCat_name());

            categorieList.add(homeModel);
        }

        tempList = categorieList;

        homeCardAdapter = new HomeCardAdapter(categorieList);
        recycleViewForHomeActivity.setAdapter(homeCardAdapter);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    public void networkError() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE).setTitleText("No Internet connected?").setContentText("Make sure your internet connection is working.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
                getActivity().finishAffinity();
            }
        }).show();
    }

    public void restartApp() {
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE).setTitleText("Something went wrong!").setContentText("Please Turn On Internet Connection and Reopen App").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.cancel();
                if (NetworkConnectivityReceiver.isConnected()) {
                    setupProgress();
                    getPosKeyAndCall1();
                } else {
                    networkError();
                }
            }
        }).show();
    }

    @Override
    public void onBillingClientSetupFinished() {

    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchases) {
        isActive = SubscriptionsUtil.isSubscriptionActive(purchases);
    }

    @Override
    public void onPurchaseVerified() {

    }


    public boolean permission() {
/*
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
*/

        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Toast.makeText(getActivity(), "Permission allowed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Please allow permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
*/
    }

    class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ViewHolder> {

        ArrayList<HomeModel> itemList;

        HomeCardAdapter(ArrayList<HomeModel> items) {
            itemList = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_card_item_home, viewGroup, false);
            return new HomeCardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeCardAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "font/cabin.ttf");
            viewHolder.txtMainHeading.setTypeface(custom_font);
            viewHolder.txtMainHeading.setText("#" + itemList.get(i).getTitle());
            viewHolder.txtMainHeading.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_slide_up));

            PushDownAnim.setPushDownAnimTo(viewHolder.cardView);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Changed 26/05/2023 */
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (NetworkConnectivityReceiver.isConnected()) {
                                        String title = itemList.get(i).getTitle();

                                        int finalPOS = 0;

                                        for (int i = 0; i < posterDatas.size(); i++) {
                                            if (title.equals(posterDatas.get(i).getPOSTERCat_name())) {
                                                finalPOS = i;
                                            }
                                        }

                                        if (!isActive) {
                                            int finalPOS1 = finalPOS;
                                            new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                                @Override
                                                public void onAdClosed() {
                                                    Log.e("@@@", "1A");
                                                    Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                                    intent.putExtra("position", finalPOS1);
                                                    intent.putExtra("cat_id", Integer.parseInt(posterDatas.get(finalPOS1).getPOSTERCat_id()));
                                                    intent.putExtra("cateName", posterDatas.get(finalPOS1).getPOSTERCat_name());
                                                    intent.putExtra("ratio", 0);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                            intent.putExtra("position", finalPOS);
                                            intent.putExtra("cat_id", Integer.parseInt(posterDatas.get(finalPOS).getPOSTERCat_id()));
                                            intent.putExtra("cateName", posterDatas.get(finalPOS).getPOSTERCat_name());
                                            intent.putExtra("ratio", 0);
                                            startActivity(intent);
                                        }

                                    } else {
                                        networkError();
                                    }

                                } else {
                                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                        showSettingsDialog();
                                    }
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).withErrorListener(new PermissionRequestErrorListener() {
                            public void onError(DexterError dexterError) {

                            }
                        }).onSameThread().check();
                    } else {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (NetworkConnectivityReceiver.isConnected()) {
                                        String title = itemList.get(i).getTitle();

                                        int finalPOS = 0;

                                        for (int i = 0; i < posterDatas.size(); i++) {
                                            if (title.equals(posterDatas.get(i).getPOSTERCat_name())) {
                                                finalPOS = i;
                                            }
                                        }

                                        if (!isActive) {
                                            int finalPOS1 = finalPOS;
                                            new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                                @Override
                                                public void onAdClosed() {
                                                    Log.e("@@@", "1A");
                                                    Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                                    intent.putExtra("position", finalPOS1);
                                                    intent.putExtra("cat_id", Integer.parseInt(posterDatas.get(finalPOS1).getPOSTERCat_id()));
                                                    intent.putExtra("cateName", posterDatas.get(finalPOS1).getPOSTERCat_name());
                                                    intent.putExtra("ratio", 0);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                            intent.putExtra("position", finalPOS);
                                            intent.putExtra("cat_id", Integer.parseInt(posterDatas.get(finalPOS).getPOSTERCat_id()));
                                            intent.putExtra("cateName", posterDatas.get(finalPOS).getPOSTERCat_name());
                                            intent.putExtra("ratio", 0);
                                            startActivity(intent);
                                        }

                                    } else {
                                        networkError();
                                    }

                                } else {
                                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                        showSettingsDialog();
                                    }
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).withErrorListener(new PermissionRequestErrorListener() {
                            public void onError(DexterError dexterError) {

                            }
                        }).onSameThread().check();
                    }
                }
            });

            Glide.with(activity).load(itemList.get(i).getIcon()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.iv_icon);

            switch (i % 6) {
                case 0:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.blue_active));
                    break;
                case 1:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.green_active));
                    break;
                case 2:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.blue_grey_active));
                    break;
                case 3:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.orange_active));
                    break;
                case 4:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.purple_active));
                    break;
                case 5:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.red_active));
                    break;
                default:
                    viewHolder.LLOut.setBackgroundColor(getResources().getColor(R.color.red_active));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if (itemList == null) {
                Toast.makeText(getActivity(), "Please re-open this app", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return itemList.size();
        }


        private void updateData(ArrayList<HomeModel> datas, String text) {
            ArrayList<HomeModel> newNames = new ArrayList<>();

            if (text.length() == 0) {
                newNames.addAll(datas);
            } else {
                for (HomeModel name : datas) {
                    if (name.getTitle().toLowerCase().contains(text))
                        newNames.add(name);
                }
            }

            itemList = newNames;
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView iv_icon;
            LinearLayout LLOut;
            TextView txtMainHeading;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMainHeading = itemView.findViewById(R.id.txtMainHeading);
                cardView = itemView.findViewById(R.id.cardViewHome);
                iv_icon = itemView.findViewById(R.id.iv_icon);
                LLOut = itemView.findViewById(R.id.LLOut);
            }
        }
    }

    public void openSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
        startActivityForResult(intent, 101);
    }

    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

}
