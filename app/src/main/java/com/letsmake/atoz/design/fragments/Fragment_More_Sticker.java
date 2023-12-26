package com.letsmake.atoz.design.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.stepstone.apprating.CKt;

import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.custom_adapter.Sticker_Adapter;
import com.letsmake.atoz.design.custom_view.Item_Grid_Space_Decorator;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.listener.On_Data_Snap_Listener;
import com.letsmake.atoz.design.listener.On_Load_More_Listener;
import com.letsmake.atoz.design.listener.RV_Load_More_Scroll;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.poster_builder.Data_Provider;

public class Fragment_More_Sticker extends Fragment {

    private ArrayList<BG_Image> category_list;
    private String color;
    private ProgressBar loading_view;
    private GridLayoutManager mLayoutManager;
    private int numColumns = 0;

    private On_Data_Snap_Listener onGetSnap;

    private RecyclerView recyclerView;
    private RelativeLayout rlAd;
    private RV_Load_More_Scroll scrollListener;
    private Sticker_Adapter stickerAdapter;
    private Data_Provider dataProvider;

    private void initializeCategory() {
        this.dataProvider = new Data_Provider();
        this.dataProvider.setStickerList(this.category_list);
        this.stickerAdapter = new Sticker_Adapter(getActivity(), this.dataProvider.get_Load_More_Sticker_Items(), getResources().getDimensionPixelSize(R.dimen.logo_image_size), getResources().getDimensionPixelSize(R.dimen.image_padding), this.color);
        this.mLayoutManager = new GridLayoutManager(getContext(), 3);
        this.recyclerView.setLayoutManager(this.mLayoutManager);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.addItemDecoration(new Item_Grid_Space_Decorator(3, 40, true));
        this.loading_view.setVisibility(View.GONE);
        this.recyclerView.setAdapter(this.stickerAdapter);
        this.stickerAdapter.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() {
            public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BG_Image> arrayList2, String str, Activity activity, String str2) {
                Fragment_More_Sticker.this.onGetSnap.onSnapFilter(0, 34, str, str2);
            }
        });
        this.mLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            public int getSpanSize(int i) {
                switch (Fragment_More_Sticker.this.stickerAdapter.getItemViewType(i)) {
                    case 0:
                        return 1;
                    case 1:
                        return 3;
                    default:
                        return -1;
                }
            }
        });

        this.scrollListener = new RV_Load_More_Scroll(this.mLayoutManager);
        this.scrollListener.set_Data_LoadMore_Listener(new On_Load_More_Listener() {
            public void onLoadMore() {
                Fragment_More_Sticker.this.LoadExtraStickerData();
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
    }

    public static Fragment_More_Sticker newInstance(ArrayList<BG_Image> arrayList, String str) {
        Fragment_More_Sticker fragmentMoreSticker = new Fragment_More_Sticker();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(CKt.DIALOG_DATA, arrayList);
        bundle.putString("color", str);
        fragmentMoreSticker.setArguments(bundle);
        return fragmentMoreSticker;
    }

    private void LoadExtraStickerData() {
        this.stickerAdapter.insertLoadingView();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Fragment_More_Sticker.this.stickerAdapter.hideLoadingView();
                Fragment_More_Sticker.this.stickerAdapter.insertData(Fragment_More_Sticker.this.dataProvider.getLoad_More_StickerItemsS());
                Fragment_More_Sticker.this.stickerAdapter.notifyDataSetChanged();
                Fragment_More_Sticker.this.scrollListener.set_Data_Loaded();
            }
        }, 3000);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.layout_root_fragment, viewGroup, false);

        this.recyclerView = inflate.findViewById(R.id.overlay_artwork);
        this.onGetSnap = (On_Data_Snap_Listener) getActivity();
        this.rlAd = inflate.findViewById(R.id.rl_ad);
        this.loading_view = inflate.findViewById(R.id.loading_view);
        this.category_list = getArguments().getParcelableArrayList(CKt.DIALOG_DATA);
        this.color = getArguments().getString("color");
        this.loading_view.setVisibility(View.GONE);
        this.stickerAdapter = new Sticker_Adapter(getActivity(), this.category_list, getResources().getDimensionPixelSize(R.dimen.logo_image_size), getResources().getDimensionPixelSize(R.dimen.image_padding), this.color);

        initializeCategory();

        return inflate;
    }

}
