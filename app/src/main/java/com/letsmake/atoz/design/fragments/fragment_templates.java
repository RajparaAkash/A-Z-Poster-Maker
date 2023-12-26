package com.letsmake.atoz.design.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.billingclient.api.Purchase;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.Scopes;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsmake.atoz.design.Application;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.CategoryTemplateActivity;
import com.letsmake.atoz.design.activity.PosterMAKERActivity;
import com.letsmake.atoz.design.activity.SubscriptionActivity;
import com.letsmake.atoz.design.activity.ViewPagerActivity;
import com.letsmake.atoz.design.ads.InterstitialAds;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;

import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.editor_intelligence.IV_Download_Manager;
import com.letsmake.atoz.design.imageloader.Custom_Glide_IMG_Loader;
import com.letsmake.atoz.design.model.HomeModel;
import com.letsmake.atoz.design.poster_builder.Full_Poster_Thumb;
import com.letsmake.atoz.design.poster_builder.Poster_Co;
import com.letsmake.atoz.design.poster_builder.Poster_Datas;
import com.letsmake.atoz.design.poster_builder.StickerInfo;
import com.letsmake.atoz.design.poster_builder.Text_Info;
import com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;

public class fragment_templates extends Fragment implements MaterialSearchBar.OnSearchActionListener, BillingUpdatesListener {

    public ArrayList<Poster_Co> posterCos;

    public ArrayList<StickerInfo> stickerInfoArrayList;
    public ArrayList<String> url;
    public ArrayList<Text_Info> textInfoArrayList;

    boolean isActive;

    ImageView IvOffer;

    private Activity activity;

    private MaterialSearchBar searchBar;

    private SweetAlertDialog pDialog;
    private AppPreferenceClass appPreferenceClass;

    private RecyclerView rvHashTags;
    private HashTagAdapter hashTagAdapter;

    private RecyclerView rvMorePoster;
    private MorePosterAdapter adapterMorePosters;

    private ArrayList<HomeModel> categorieList, tempList;


    View view;

    int newWidth, newHeight, finalPos;
    String ratioNext;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_fragment_template, container, false);

        activity = getActivity();

        this.appPreferenceClass = new AppPreferenceClass(getActivity());


        searchBar = view.findViewById(R.id.searchBar);

        IvOffer = view.findViewById(R.id.iv_offer);
        Glide.with(activity).asGif().load(R.drawable.upgrade_premium).into(IvOffer);
        IvOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SubscriptionActivity.class));
            }
        });

        rvHashTags = view.findViewById(R.id.rvHashTags);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        rvHashTags.setLayoutManager(staggeredGridLayoutManager);

        rvMorePoster = view.findViewById(R.id.rvMorePoster);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvMorePoster.setLayoutManager(layoutManager);

        Display display = getActivity().getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        newWidth = size.x;
        newHeight = newWidth / 2;

        categorieList = new ArrayList<>();
        tempList = new ArrayList<>();
        for (int i = 0; i < fragment_home.posterDatas.size(); i++) {

            HomeModel homeModel = new HomeModel();
            homeModel.setIcon(fragment_home.posterDatas.get(i).getPOSTERThumb_img());
            homeModel.setTitle(fragment_home.posterDatas.get(i).getPOSTERCat_name());

            categorieList.add(homeModel);
        }

        tempList = categorieList;

        hashTagAdapter = new HashTagAdapter(categorieList);
        rvHashTags.setAdapter(hashTagAdapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rvHashTags.smoothScrollToPosition(Math.round(categorieList.size() / 2));
            }
        }, 900);

        adapterMorePosters = new MorePosterAdapter(categorieList);
        rvMorePoster.setAdapter(adapterMorePosters);

        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (hashTagAdapter != null)
                    hashTagAdapter.updateData(tempList, searchBar.getText());

                if (adapterMorePosters != null)
                    adapterMorePosters.updateData(tempList, searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void networkError() {
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE).setTitleText("No Internet connected?").setContentText("Make sure your internet connection is working.").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                activity.finishAffinity();
            }
        }).show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

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

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

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

    class MorePosterAdapter extends RecyclerView.Adapter<MorePosterAdapter.ViewHolder> {

        ArrayList<HomeModel> itemList;

        MorePosterAdapter(ArrayList<HomeModel> items) {
            itemList = items;
        }

        @NonNull
        @Override
        public MorePosterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_view_more, viewGroup, false);
            return new MorePosterAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "font/Raleway ExtraBold.ttf");
            viewHolder.txtMainHeading.setTypeface(custom_font);
            viewHolder.txtMainHeading.setText(itemList.get(position).getTitle());

            String title = itemList.get(position).getTitle();

            for (int i = 0; i < itemList.size(); i++) {
                if (title == fragment_home.posterDatas.get(i).getPOSTERCat_name()) {
                    finalPos = i;
                }
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            viewHolder.rvTemplateList.setLayoutManager(layoutManager);

            TemplateListAdapter templateListAdapter = new TemplateListAdapter(fragment_home.posterDatas.get(finalPos).getPoster_list(), finalPos);
            viewHolder.rvTemplateList.setAdapter(templateListAdapter);

            viewHolder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Changed 26/05/2023 */
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (NetworkConnectivityReceiver.isConnected()) {
                                        String title = itemList.get(position).getTitle();

                                        int finalPOS = 0;

                                        for (int i = 0; i < fragment_home.posterDatas.size(); i++) {
                                            if (title.equals(fragment_home.posterDatas.get(i).getPOSTERCat_name())) {
                                                finalPOS = i;
                                            }
                                        }

                                        if (!isActive) {
                                            int finalPOS1 = finalPOS;
                                            new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                                @Override
                                                public void onAdClosed() {
                                                    Intent intent = new Intent(new Intent(getActivity(), CategoryTemplateActivity.class));
                                                    intent.putExtra("position", finalPOS1);
                                                    intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_id()));
                                                    intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_name());
                                                    intent.putExtra("ratio", 0);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(new Intent(getActivity(), CategoryTemplateActivity.class));
                                            intent.putExtra("position", finalPOS);
                                            intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS).getPOSTERCat_id()));
                                            intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS).getPOSTERCat_name());
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
                                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        }).onSameThread().check();
                    } else {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (NetworkConnectivityReceiver.isConnected()) {
                                        String title = itemList.get(position).getTitle();

                                        int finalPOS = 0;

                                        for (int i = 0; i < fragment_home.posterDatas.size(); i++) {
                                            if (title.equals(fragment_home.posterDatas.get(i).getPOSTERCat_name())) {
                                                finalPOS = i;
                                            }
                                        }

                                        if (!isActive) {
                                            int finalPOS1 = finalPOS;
                                            new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                                @Override
                                                public void onAdClosed() {
                                                    Intent intent = new Intent(new Intent(getActivity(), CategoryTemplateActivity.class));
                                                    intent.putExtra("position", finalPOS1);
                                                    intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_id()));
                                                    intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_name());
                                                    intent.putExtra("ratio", 0);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(new Intent(getActivity(), CategoryTemplateActivity.class));
                                            intent.putExtra("position", finalPOS);
                                            intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS).getPOSTERCat_id()));
                                            intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS).getPOSTERCat_name());
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
                                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        }).onSameThread().check();
                    }
                }
            });
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
            TextView txtMainHeading, tvViewALL;
            RecyclerView rvTemplateList;
            ImageView ivMore;


            ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMainHeading = itemView.findViewById(R.id.txtMainHeading);
                tvViewALL = itemView.findViewById(R.id.tvViewALL);
                ivMore = itemView.findViewById(R.id.iv_more);
                rvTemplateList = itemView.findViewById(R.id.rvTemplateList);
            }
        }

    }

    class TemplateListAdapter extends RecyclerView.Adapter<TemplateListAdapter.ViewHolder> {

        ArrayList<Full_Poster_Thumb> itemList;
        int finalPos1;

        TemplateListAdapter(ArrayList<Full_Poster_Thumb> items, int position) {
            itemList = items;
            finalPos1 = position;
        }

        @NonNull
        @Override
        public TemplateListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.template_card_item_list, viewGroup, false);
            return new TemplateListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

            Full_Poster_Thumb fullPosterThumb = itemList.get(position);
            String ratio = fullPosterThumb.get_POST_Ratio();
            int pro = fullPosterThumb.getPRO();

            if (pro == 1) {
                viewHolder.iv_pro.setVisibility(View.VISIBLE);
            } else {
                viewHolder.iv_pro.setVisibility(View.GONE);
            }

            float y = 1f;
            String[] ratioArray = ratio.split(":");

            y = Float.parseFloat(ratioArray[1]) / Float.parseFloat(ratioArray[0]);

            newWidth = Math.round(newHeight * (1 / y));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(newWidth, newHeight);
            viewHolder.image.requestLayout();
            viewHolder.image.setLayoutParams(params);

            new Custom_Glide_IMG_Loader(viewHolder.image, viewHolder.progressBar).loadImgFromUrl(fullPosterThumb.getPost_thumb(), new RequestOptions().priority(Priority.HIGH));

            int category_id = Integer.parseInt(fragment_home.posterDatas.get(finalPos1).getPOSTERCat_id());

            PushDownAnim.setPushDownAnimTo(viewHolder.cardView);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isActive) {
                        if (NetworkConnectivityReceiver.isConnected()) {
                            requestStoragePermission(fullPosterThumb.getPost_id(), category_id);
                        } else {
                            networkError();
                        }
                    } else {
                        if (pro == 1) {
                            startActivity(new Intent(activity, SubscriptionActivity.class));
                        } else {
                            if (NetworkConnectivityReceiver.isConnected()) {
                                requestStoragePermission(fullPosterThumb.getPost_id(), category_id);
                            } else {
                                networkError();
                            }
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (itemList == null) {
                Toast.makeText(getActivity(), "Please re-open this app", Toast.LENGTH_SHORT).show();
                return 0;
            }
            return itemList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView image, iv_pro;
            ProgressBar progressBar;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.iv_image1);
                cardView = itemView.findViewById(R.id.cardViewHome1);
                iv_pro = itemView.findViewById(R.id.iv_pro1);
                progressBar = itemView.findViewById(R.id.progressBar1);
            }
        }
    }

    public void makeStickerDir() {
        this.appPreferenceClass = new AppPreferenceClass(activity);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder.append("/.Poster Design Stickers/sticker");
        File file = new File(stringBuilder.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder2.append("/.Poster Design Stickers/sticker/bg");
        File file2 = new File(stringBuilder2.toString());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder2.append("/.Poster Design Stickers/sticker/font");
        file2 = new File(stringBuilder2.toString());
        if (!file2.exists()) {
            file2.mkdirs();
        }
        for (int i = 0; i < 29; i++) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
            stringBuilder3.append("/.Poster Design Stickers/sticker/cat");
            stringBuilder3.append(i);
            File file3 = new File(stringBuilder3.toString());
            if (!file3.exists()) {
                file3.mkdirs();
            }
        }
        for (int i2 = 0; i2 < 11; i2++) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
            stringBuilder4.append("/.Poster Design Stickers/sticker/art");
            stringBuilder4.append(i2);
            File file4 = new File(stringBuilder4.toString());
            if (!file4.exists()) {
                file4.mkdirs();
            }
        }
        appPreferenceClass.putString(AppConstants.sdcardPath, file.getPath());
        String str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("onCreate: ");
        stringBuilder.append(AppConstants.sdcardPath);
        //  Log.e(str, stringBuilder.toString());
    }

    public void setupProgress() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#D81B60"));
        pDialog.setTitleText("Downloading Templates");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void getPosKeyAndCall(int i, int i2) {
        loadPoster(fragment_home.appkey, i, i2);
    }

    public void deleteRecursive(File file) {
        try {
            if (file.isDirectory()) {
                for (File deleteRecursive : file.listFiles()) {
                    deleteRecursive(deleteRecursive);
                }
            }
            file.delete();
        } catch (NullPointerException e) {
            //  e.printStackTrace();
        }
    }

    public void loadPoster(String str, int i, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppConstants.BASE_URL_POSTER);
        stringBuilder.append("poster");
        final String str2 = str;
        final int i3 = i;
        final int i4 = i2;
        Application.getInstance().addToRequestQueue(new StringRequest(Request.Method.POST, stringBuilder.toString(), new Response.Listener<String>() {
            public void onResponse(String str) {
                try {
                    Poster_Datas posterDatas = new Gson().fromJson(str, Poster_Datas.class);
                    posterCos = new ArrayList();
                    posterCos = posterDatas.getData();
                    textInfoArrayList = (posterCos.get(0)).getPOSTERText_info();
                    stickerInfoArrayList = (posterCos.get(0)).getPOSTERSticker_info();
                    Poster_Co posterCo = posterCos.get(0);
                    ratioNext = posterCo.getPOSTERRatio();
                    url = new ArrayList();
                    url.add(posterCo.getPOSTERBack_image());
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("==");
                    stringBuilder.append(stickerInfoArrayList.size());

                    //  Log.e("sticker", stringBuilder.toString());

                    for (int i = 0; i < stickerInfoArrayList.size(); i++) {
                        if (!((StickerInfo) stickerInfoArrayList.get(0)).getSTICKER_image().equals("")) {
                            ArrayList arrayList = url;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(AppConstants.BASE_URL_STICKER);
                            stringBuilder2.append(((StickerInfo) stickerInfoArrayList.get(i)).getSTICKER_image());
                            arrayList.add(stringBuilder2.toString());
                        }
                    }
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
                    stringBuilder3.append("/.PosterResorces/");
                    String stringBuilder4 = stringBuilder3.toString();
                    File file = new File(stringBuilder4);
                    if (file.exists()) {
                        deleteRecursive(file);
                        file.mkdir();
                    } else {
                        file.mkdir();
                    }
                    IV_Download_Manager.getInstance(activity).addDownloadTask(new IV_Download_Manager.IV_DownloadTask(this, IV_Download_Manager.IVTask.DOWNLOAD, url, stringBuilder4, new IV_Download_Manager.Callback() {
                        public void onSuccess(IV_Download_Manager.IV_DownloadTask imageDownloadTask, ArrayList<String> arrayList) {
                            try {
                                if (pDialog != null && pDialog.isShowing()) {
                                    pDialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.d(IV_Download_Manager.class.getSimpleName(), "Image save success news ");
                            int i = 0;
                            while (i < arrayList.size()) {
                                try {
                                    if (i == 0) {
                                        posterCos.get(i).setPOSTERBack_image(arrayList.get(i));
                                    } else {
                                        (stickerInfoArrayList.get(i - 1)).setSTICKER_image(arrayList.get(i));
                                    }
                                    i++;
                                } catch (IndexOutOfBoundsException e2) {
                                    e2.printStackTrace();
                                    return;
                                }
                            }

                            File file = new File((posterCos.get(0)).getPOSTERBack_image());
                            if (!file.exists()) {
                                Log.d("not exist", "not exist");
                            } else if (file.length() == 0) {
                                Log.d("File Empty", "File does not have any content");
                            } else {

                                if (!isActive) {
                                    new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                        @Override
                                        public void onAdClosed() {
                                            Intent intent = new Intent(activity, PosterMAKERActivity.class);
                                            intent.putParcelableArrayListExtra("template", posterCos);
                                            intent.putParcelableArrayListExtra("text", textInfoArrayList);
                                            intent.putParcelableArrayListExtra("sticker", stickerInfoArrayList);
                                            intent.putExtra(Scopes.PROFILE, "Background");
                                            intent.putExtra("catId", 1);
                                            intent.putExtra("loadUserFrame", false);
                                            intent.putExtra("sizeposition", 0/* size postion */);
                                            intent.putExtra("ratio", ratioNext);
                                            intent.putExtra("Temp_Type", "MY_TEMP");
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    Intent intent = new Intent(activity, PosterMAKERActivity.class);
                                    intent.putParcelableArrayListExtra("template", posterCos);
                                    intent.putParcelableArrayListExtra("text", textInfoArrayList);
                                    intent.putParcelableArrayListExtra("sticker", stickerInfoArrayList);
                                    intent.putExtra(Scopes.PROFILE, "Background");
                                    intent.putExtra("catId", 1);
                                    intent.putExtra("loadUserFrame", false);
                                    intent.putExtra("sizeposition", 0/* size postion */);
                                    intent.putExtra("ratio", ratioNext);
                                    intent.putExtra("Temp_Type", "MY_TEMP");
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(IV_Download_Manager.ImageSaveFailureReason imageSaveFailureReason) {
                            String simpleName = IV_Download_Manager.class.getSimpleName();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Image save fail news ");
                            stringBuilder.append(imageSaveFailureReason);
                            Log.d(simpleName, stringBuilder.toString());
                            try {
                                if (pDialog != null && pDialog.isShowing()) {
                                    pDialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }));
                } catch (JsonSyntaxException | NullPointerException e) {
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
                try {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("key", str2);
                hashMap.put("device", "1");
                hashMap.put("cat_id", String.valueOf(i3));
                hashMap.put("post_id", String.valueOf(i4));

                return hashMap;
            }
        });
    }

    private void requestStoragePermission(final int i, final int i2) {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {

                        makeStickerDir();
                        setupProgress();
                        getPosKeyAndCall(i2, i);
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        showSettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {

                        makeStickerDir();
                        setupProgress();
                        getPosKeyAndCall(i2, i);
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        showSettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(activity, "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        }
    }


    class HashTagAdapter extends RecyclerView.Adapter<HashTagAdapter.ViewHolder> {

        ArrayList<HomeModel> itemList;

        HashTagAdapter(ArrayList<HomeModel> items) {
            itemList = items;
        }

        @NonNull
        @Override
        public HashTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_title_home, viewGroup, false);
            return new HashTagAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "font/cabin.ttf");
            viewHolder.txtMainHeading.setTypeface(custom_font);
            viewHolder.txtMainHeading.setText("#" + itemList.get(position).getTitle());
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
                                        String title = itemList.get(position).getTitle();

                                        int finalPOS = 0;

                                        for (int i = 0; i < fragment_home.posterDatas.size(); i++) {
                                            if (title.equals(fragment_home.posterDatas.get(i).getPOSTERCat_name())) {
                                                finalPOS = i;
                                            }
                                        }

                                        if (!isActive) {
                                            int finalPOS1 = finalPOS;
                                            new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                                @Override
                                                public void onAdClosed() {
                                                    Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                                    intent.putExtra("position", finalPOS1);
                                                    intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_id()));
                                                    intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_name());
                                                    intent.putExtra("ratio", 0);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                            intent.putExtra("position", finalPOS);
                                            intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS).getPOSTERCat_id()));
                                            intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS).getPOSTERCat_name());
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
                                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        }).onSameThread().check();
                    } else {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (NetworkConnectivityReceiver.isConnected()) {
                                        String title = itemList.get(position).getTitle();

                                        int finalPOS = 0;

                                        for (int i = 0; i < fragment_home.posterDatas.size(); i++) {
                                            if (title.equals(fragment_home.posterDatas.get(i).getPOSTERCat_name())) {
                                                finalPOS = i;
                                            }
                                        }

                                        if (!isActive) {
                                            int finalPOS1 = finalPOS;
                                            new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                                                @Override
                                                public void onAdClosed() {
                                                    Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                                    intent.putExtra("position", finalPOS1);
                                                    intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_id()));
                                                    intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS1).getPOSTERCat_name());
                                                    intent.putExtra("ratio", 0);
                                                    startActivity(intent);
                                                }
                                            });
                                        } else {
                                            Intent intent = new Intent(new Intent(getActivity(), ViewPagerActivity.class));
                                            intent.putExtra("position", finalPOS);
                                            intent.putExtra("cat_id", Integer.parseInt(fragment_home.posterDatas.get(finalPOS).getPOSTERCat_id()));
                                            intent.putExtra("cateName", fragment_home.posterDatas.get(finalPOS).getPOSTERCat_name());
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
                                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        }).onSameThread().check();
                    }
                }
            });

            switch (position % 6) {
                case 0:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.blue_active));
                    break;
                case 1:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.green_active));
                    break;
                case 2:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.blue_grey_active));
                    break;
                case 3:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.orange_active));
                    break;
                case 4:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.purple_active));
                    break;
                case 5:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.red_active));
                    break;
                default:
                    viewHolder.linearlayout.setBackgroundColor(getResources().getColor(R.color.red_active));
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
            LinearLayout linearlayout;
            TextView txtMainHeading;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtMainHeading = itemView.findViewById(R.id.txtMainHeading);
                cardView = itemView.findViewById(R.id.cardViewHome);
                linearlayout = itemView.findViewById(R.id.linearlayout);
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
