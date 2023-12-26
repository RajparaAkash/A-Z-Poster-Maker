package com.letsmake.atoz.design.custom_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.SelectBGIMGActivity;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.imageloader.Quick_Glide_IMG_Loader1;
import com.letsmake.atoz.design.listener.On_Item_Click_Listener;
import com.letsmake.atoz.design.poster_builder.BG_Image;

public class Basic_Adapters extends Adapter<Basic_Adapters.ViewHolder> {

    private ArrayList<BG_Image> BG_Images;

    public OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;

    Context context;

    private boolean mHorizontal, mPager;
    private int index, flagForActivity;

    On_Item_Click_Listener listener;
    SharedPreferences preferences;

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {

        ImageView ivLock, imageView;
        ProgressBar mProgressBar;
        TextView ratingTextView, nameTextView;
        RelativeLayout rl_see_more;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = view.findViewById(R.id.imageView);
            this.ivLock = view.findViewById(R.id.iv_lock);
            this.nameTextView = view.findViewById(R.id.nameTextView);
            this.ratingTextView = view.findViewById(R.id.ratingTextView);
            this.mProgressBar = view.findViewById(R.id.progressBar1);
            this.rl_see_more = view.findViewById(R.id.rl_see_more);
        }
    }

    public Basic_Adapters(Context context, boolean z, boolean z2, ArrayList<BG_Image> arrayList, int i, int i2, On_Item_Click_Listener onItemClickListener) {
        this.mHorizontal = z;
        this.BG_Images = arrayList;
        this.mPager = z2;
        this.context = context;
        this.flagForActivity = i;
        this.index = i2;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.listener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mPager) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pager_adapter, viewGroup, false));
        }
        ViewHolder viewHolder;
        if (this.mHorizontal) {
            viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customized_adapter, viewGroup, false));
        } else {
            viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_vertical_adapter, viewGroup, false));
        }
        return viewHolder;
    }


    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override
    public int getItemViewType(int i) {
        return super.getItemViewType(i);
    }

    @Override
    public int getItemCount() {
        return this.BG_Images.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        BG_Image BG_Image = (BG_Image) this.BG_Images.get(i);
        if (i > 4) {
            viewHolder.ivLock.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.rl_see_more.setVisibility(View.VISIBLE);
            viewHolder.rl_see_more.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (Basic_Adapters.this.listener != null) {
                        Basic_Adapters.this.listener.onItemClick(i);
                    }
                }
            });
            return;
        }
        RequestOptions priority;
        if (this.index > 1) {
            priority = new RequestOptions().priority(Priority.HIGH);
        } else {
            priority = new RequestOptions().priority(Priority.HIGH);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppConstants.BASE_URL_BG);
        stringBuilder.append("/");
        stringBuilder.append((this.BG_Images.get(i)).getBGImage_url());
        final String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(AppConstants.BASE_URL_BG);
        stringBuilder3.append("/");
        stringBuilder3.append((this.BG_Images.get(i)).getThumb_url());
        new Quick_Glide_IMG_Loader1(viewHolder.imageView, viewHolder.mProgressBar).loadImageFromStr(stringBuilder3.toString(), priority);
        if (i <= 11 || this.preferences.getBoolean("isAdsDisabled", false)) {
            viewHolder.ivLock.setVisibility(View.GONE);
        } else {
            viewHolder.ivLock.setVisibility(View.GONE);
        }
        viewHolder.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Basic_Adapters.this.flagForActivity == 1) {
                    ((SelectBGIMGActivity) Basic_Adapters.this.context).performNextTask(stringBuilder2);
                } else {
                    Basic_Adapters.this.mSingleCallback.onClickCallBack(null, Basic_Adapters.this.BG_Images, stringBuilder2, (FragmentActivity) Basic_Adapters.this.context, "");
                }
            }
        });
    }

}
