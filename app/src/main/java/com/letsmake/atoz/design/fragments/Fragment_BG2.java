package com.letsmake.atoz.design.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.stepstone.apprating.CKt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.letsmake.atoz.design.Application;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.custom_adapter.Backgrounds_Adapter;
import com.letsmake.atoz.design.custom_view.Item_Grid_Space_Decorator;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.listener.On_Data_Snap_Listener;
import com.letsmake.atoz.design.listener.On_Load_More_Listener;
import com.letsmake.atoz.design.listener.RV_Load_More_Scroll;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.poster_builder.Data_Provider;

public class Fragment_BG2 extends Fragment {

    private On_Data_Snap_Listener onGetSnap;

    private ArrayList<BG_Image> category_list;
    private GridLayoutManager gridLayoutManager;

    private Backgrounds_Adapter backgroundsAdapter;
    private ProgressBar loading_view;
    private RecyclerView recyclerView;
    private RelativeLayout rlAd;
    private float screenHeight, screenWidth;

    public RV_Load_More_Scroll scrollListener;
    int size;
    Data_Provider dataProvider;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup, @NonNull Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_root_fragment, viewGroup, false);

        this.recyclerView =  inflate.findViewById(R.id.overlay_artwork);
        this.loading_view =  inflate.findViewById(R.id.loading_view);
        this.onGetSnap = (On_Data_Snap_Listener) getActivity();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = (float) displayMetrics.widthPixels;
        screenHeight = (float) displayMetrics.heightPixels;

        this.category_list = getArguments().getParcelableArrayList(CKt.DIALOG_DATA);
        this.rlAd =  inflate.findViewById(R.id.rl_ad);

        initializeCategory();

        return inflate;
    }

    public File get_BG_Cache_Folder(Context context) {
        File file;
        if (Environment.getExternalStorageState().equals("mounted")) {
            file = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        } else {
            file = null;
        }
        return !file.isDirectory() ? context.getCacheDir() : file;
    }

    public static Fragment_BG2 newInstance(ArrayList<BG_Image> arrayList) {
        Fragment_BG2 fragmentBG2 = new Fragment_BG2();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CKt.DIALOG_DATA, arrayList);
        fragmentBG2.setArguments(bundle);
        return fragmentBG2;
    }



    public void LoadMore_BG_Data() {
        this.backgroundsAdapter.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Fragment_BG2.this.backgroundsAdapter.hideLoadingView();
                Fragment_BG2.this.backgroundsAdapter.insertdata(Fragment_BG2.this.dataProvider.get_Load_More_Itemss());
                Fragment_BG2.this.backgroundsAdapter.notifyDataSetChanged();
                Fragment_BG2.this.scrollListener.set_Data_Loaded();
            }
        }, 3000);
    }

    private void initializeCategory() {
        this.dataProvider = new Data_Provider();
        this.dataProvider.apply_Background_List(this.category_list);
        this.gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        this.recyclerView.setLayoutManager(this.gridLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new Item_Grid_Space_Decorator(3, 40, true));
        this.backgroundsAdapter = new Backgrounds_Adapter(getActivity(), this.dataProvider.get_Load_More_Items());
        this.loading_view.setVisibility(View.GONE);
        this.recyclerView.setAdapter(this.backgroundsAdapter);
        this.backgroundsAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() {
            public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BG_Image> arrayList2, String str, Activity activity, String str2) {
                String str3 = CKt.DIALOG_DATA;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("==");
                stringBuilder.append(str);
                Log.e(str3, stringBuilder.toString());
                final ProgressDialog progressDialog = new ProgressDialog(Fragment_BG2.this.getContext());
                progressDialog.setMessage(Fragment_BG2.this.getResources().getString(R.string.plzwait));
                progressDialog.setCancelable(false);
                progressDialog.show();
                Fragment_BG2 fragmentBG2 = Fragment_BG2.this;
                final File cacheFolder = fragmentBG2.get_BG_Cache_Folder(fragmentBG2.getContext());
                Application.getInstance().addToRequestQueue(new ImageRequest(str, new Listener<Bitmap>() {
                    public void onResponse(Bitmap bitmap) {
                        try {
                            progressDialog.dismiss();
                            try {
                                File file = new File(cacheFolder, "localFileName.png");
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                                try {
                                    Fragment_BG2.this.onGetSnap.onSnapFilter(0, 104, file.getAbsolutePath(), "");
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e2) {
                                e2.printStackTrace();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                }, 0, 0, null, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                    }
                }));
            }
        });

        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int i) {
                switch (Fragment_BG2.this.backgroundsAdapter.getItemViewType(i)) {
                    case 0:
                        return 1;
                    case 1:
                        return 3;
                    default:
                        return -1;
                }
            }
        });

        this.scrollListener = new RV_Load_More_Scroll(this.gridLayoutManager);
        this.scrollListener.set_Data_LoadMore_Listener(new On_Load_More_Listener() {
            public void onLoadMore() {
                Fragment_BG2.this.LoadMore_BG_Data();
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
    }

}
