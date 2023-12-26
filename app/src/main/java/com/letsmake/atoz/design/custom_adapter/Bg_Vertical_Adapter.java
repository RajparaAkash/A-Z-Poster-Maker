package com.letsmake.atoz.design.custom_adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.github.rubensousa.gravitysnaphelper.GravityPagerSnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper.SnapListener;

import java.util.ArrayList;
import java.util.List;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.SelectBGIMGActivity;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.listener.On_Item_Click_Listener;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.poster_builder.Snap_Info;

public class Bg_Vertical_Adapter extends Adapter<RecyclerView.ViewHolder> implements SnapListener {

    private int flagForActivity;
    private ArrayList<Object> mSnaps;
    private RecyclerView recyclerView;

    public OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;

    Activity context;

    public class LoadingHolder extends RecyclerView.ViewHolder {
        LoadingHolder(View view) {
            super(view);
        }
    }


    public static class BGVerticalViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView seeMoreTextView;
        TextView snapTextView;

        public BGVerticalViewHolder(View view) {
            super(view);
            this.snapTextView = view.findViewById(R.id.snapTextView);
            this.seeMoreTextView = view.findViewById(R.id.seeMoreTextView);
            this.recyclerView = view.findViewById(R.id.recyclerView);
        }
    }

    public void onSnap(int i) {
    }

    public Bg_Vertical_Adapter(Activity activity, ArrayList<Object> arrayList, RecyclerView recyclerView, int i) {
        this.mSnaps = arrayList;
        this.context = activity;
        this.flagForActivity = i;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemViewType(int i) {
        if (this.mSnaps.get(i) == null) {
            return 0;
        }
        return 1;
    }

    public void InsertData(List<Object> list) {
        notifyDataSetChanged();
    }

    public void insertLoadingView() {
        new Handler().post(new Runnable() {
            public void run() {
                mSnaps.add(null);
                Bg_Vertical_Adapter bgVerticalAdapter = Bg_Vertical_Adapter.this;
                notifyItemInserted(bgVerticalAdapter.mSnaps.size() - 1);
            }
        });
    }

    public class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

        UnifiedNativeAdViewHolder(View view) {
            super(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int i2 = i;
        switch (getItemViewType(i2)) {
            case 1:
                ArrayList arrayList;
                final BGVerticalViewHolder BGVerticalViewHolder2 = (BGVerticalViewHolder) viewHolder;
                final Snap_Info snapInfo = (Snap_Info) this.mSnaps.get(i2);
                boolean contains = snapInfo.getText().toUpperCase().contains("WHITE");
                BGVerticalViewHolder2.snapTextView.setText(snapInfo.getText().replace("white", "").toUpperCase());
                BGVerticalViewHolder2.recyclerView.setOnFlingListener(null);
                boolean z = true;
                if (snapInfo.getGravity() == GravityCompat.START || snapInfo.getGravity() == GravityCompat.END) {
                    BGVerticalViewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(BGVerticalViewHolder2.recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
                    new GravitySnapHelper(snapInfo.getGravity()).attachToRecyclerView(BGVerticalViewHolder2.recyclerView);
                } else if (snapInfo.getGravity() == 1 || snapInfo.getGravity() == 16) {
                    BGVerticalViewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(BGVerticalViewHolder2.recyclerView.getContext(), snapInfo.getGravity() == 1 ? RecyclerView.HORIZONTAL : RecyclerView.VERTICAL, false));
                    new LinearSnapHelper().attachToRecyclerView(BGVerticalViewHolder2.recyclerView);
                } else if (snapInfo.getGravity() == 17) {
                    BGVerticalViewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(BGVerticalViewHolder2.recyclerView.getContext(), RecyclerView.HORIZONTAL, false));
                    new GravityPagerSnapHelper(GravityCompat.START).attachToRecyclerView(BGVerticalViewHolder2.recyclerView);
                } else {
                    BGVerticalViewHolder2.recyclerView.setLayoutManager(new LinearLayoutManager(BGVerticalViewHolder2.recyclerView.getContext()));
                    new GravitySnapHelper(snapInfo.getGravity()).attachToRecyclerView(BGVerticalViewHolder2.recyclerView);
                }
                if (snapInfo.getBG_Images().size() > 3) {
                    BGVerticalViewHolder2.seeMoreTextView.setVisibility(View.VISIBLE);
                } else {
                    BGVerticalViewHolder2.seeMoreTextView.setVisibility(View.GONE);
                }
                ArrayList arrayList2 = new ArrayList();
                if (snapInfo.getBG_Images().size() >= 6) {
                    for (int i3 = 0; i3 < 6; i3++) {
                        arrayList2.add(snapInfo.getBG_Images().get(i3));
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = snapInfo.getBG_Images();
                }
                Context context;
                boolean z2;
                if (this.flagForActivity == 1) {
                    context = this.context;
                    z2 = snapInfo.getGravity() == GravityCompat.START || snapInfo.getGravity() == GravityCompat.END || snapInfo.getGravity() == 1;
                    if (snapInfo.getGravity() != 17) {
                        z = false;
                    }
                    BGVerticalViewHolder2.recyclerView.setAdapter(new Basic_Adapters(context, z2, z, arrayList, this.flagForActivity, i, new On_Item_Click_Listener() {
                        public void onItemClick(int i) {
                            BGVerticalViewHolder2.seeMoreTextView.performClick();
                        }
                    }));
                } else {
                    context = this.context;
                    z2 = snapInfo.getGravity() == GravityCompat.START || snapInfo.getGravity() == GravityCompat.END || snapInfo.getGravity() == 1;
                    if (snapInfo.getGravity() != 17) {
                        z = false;
                    }
                    Basic_Adapters basicAdapters = new Basic_Adapters(context, z2, z, arrayList, this.flagForActivity, i, new On_Item_Click_Listener() {
                        public void onItemClick(int i) {
                            BGVerticalViewHolder2.seeMoreTextView.performClick();
                        }
                    });
                    BGVerticalViewHolder2.recyclerView.setAdapter(basicAdapters);
                    basicAdapters.setItemClickCallback(new OnClickCallback<ArrayList<String>, Integer, String, Activity, String>() {
                        public void onClickCallBack(ArrayList<String> arrayList, ArrayList<BG_Image> arrayList2, String str, Activity activity, String str2) {
                            Bg_Vertical_Adapter.this.mSingleCallback.onClickCallBack(null, arrayList2, str, Bg_Vertical_Adapter.this.context, "");
                        }
                    });
                }
                BGVerticalViewHolder2.seeMoreTextView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (Bg_Vertical_Adapter.this.flagForActivity == 1) {
                            ((SelectBGIMGActivity) Bg_Vertical_Adapter.this.context).itemClickSeeMoreAdapter(snapInfo.getBG_Images(), snapInfo.getText());
                        } else {
                            Bg_Vertical_Adapter.this.mSingleCallback.onClickCallBack(null, snapInfo.getBG_Images(), "", Bg_Vertical_Adapter.this.context, "");
                        }
                    }
                });
                return;
            case 2:
                UnifiedNativeAdViewHolder unifiedNativeAdViewHolder = (UnifiedNativeAdViewHolder) viewHolder;
                return;
            default:
        }
    }


    public void removeLoadingView() {
        ArrayList arrayList = this.mSnaps;
        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(this.mSnaps.size());
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 1:
                return new BGVerticalViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.efficient_snap_adapter, viewGroup, false));
            case 2:
                return new UnifiedNativeAdViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_frame_layout, viewGroup, false));
            default:
                return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cure_progress_view, viewGroup, false));
        }
    }

    @Override
    public int getItemCount() {
        return this.mSnaps.size();
    }
}
