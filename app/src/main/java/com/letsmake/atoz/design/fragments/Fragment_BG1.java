package com.letsmake.atoz.design.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.stepstone.apprating.CKt;

import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.custom_adapter.Backgrounds_Adapter;
import com.letsmake.atoz.design.custom_view.Item_Grid_Space_Decorator;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.editor_intelligence.OnClickSnapListener;
import com.letsmake.atoz.design.listener.On_Load_More_Listener;
import com.letsmake.atoz.design.listener.RV_Load_More_Scroll;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.poster_builder.Data_Provider;

public class Fragment_BG1 extends Fragment {

    private ArrayList<BG_Image> category_list;

    private Data_Provider dataProvider;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar loading_view;
    private RV_Load_More_Scroll scrollListener;
    private Backgrounds_Adapter backgroundsAdapter;
    private String categoryName;
    private int category,orientation, size;
    private RecyclerView recyclerView;
    private RelativeLayout rlAd;
    private float screenHeight, screenWidth;

    private OnClickSnapListener onGetSnap;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_root_fragment, viewGroup, false);

        this.recyclerView =  inflate.findViewById(R.id.overlay_artwork);
        this.onGetSnap = (OnClickSnapListener) getActivity();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = (float) displayMetrics.widthPixels;
        screenHeight = (float) displayMetrics.heightPixels;

        this.category_list = getArguments().getParcelableArrayList(CKt.DIALOG_DATA);
        this.loading_view =  inflate.findViewById(R.id.loading_view);
        this.rlAd =  inflate.findViewById(R.id.rl_ad);

        initialize_Category();

        return inflate;
    }

    public static Fragment_BG1 newInstance(ArrayList<BG_Image> arrayList) {
        Fragment_BG1 fragmentBG1 = new Fragment_BG1();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CKt.DIALOG_DATA, arrayList);
        fragmentBG1.setArguments(bundle);
        return fragmentBG1;
    }

    private void initialize_Category() {
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
                Fragment_BG1.this.onGetSnap.onClickSnapFilter(0, 1001, str, "");
            }
        });

        this.gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int i) {
                switch (Fragment_BG1.this.backgroundsAdapter.getItemViewType(i)) {
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
                Fragment_BG1.this.LoadBGImageMoreData();
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
    }


    public void LoadBGImageMoreData() {
        this.backgroundsAdapter.showLoadingView();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Fragment_BG1.this.backgroundsAdapter.hideLoadingView();
                Fragment_BG1.this.backgroundsAdapter.insertdata(Fragment_BG1.this.dataProvider.get_Load_More_Itemss());
                Fragment_BG1.this.backgroundsAdapter.notifyDataSetChanged();
                Fragment_BG1.this.scrollListener.set_Data_Loaded();
            }
        }, 3000);
    }
}
