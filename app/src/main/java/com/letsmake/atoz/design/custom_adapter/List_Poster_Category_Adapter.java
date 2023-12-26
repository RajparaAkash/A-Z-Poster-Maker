package com.letsmake.atoz.design.custom_adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.imageloader.Poster_Glide_IMG_Loader;
import com.letsmake.atoz.design.listener.On_Item_Click_Listener;
import com.letsmake.atoz.design.poster_builder.Full_Poster_Thumb;

import static android.view.View.GONE;

public class List_Poster_Category_Adapter extends Adapter<List_Poster_Category_Adapter.PosterListViewHolder> {

    private ArrayList<Full_Poster_Thumb> fullPosterThumbs;
    private AppPreferenceClass appPreferenceClass;

    private boolean mPager, mHorizontal;

    int cat_id;
    Context context;
    On_Item_Click_Listener listener;
    String ratio;

    public List_Poster_Category_Adapter(Context context, int i, boolean z, boolean z2, ArrayList<Full_Poster_Thumb> arrayList, String str, On_Item_Click_Listener onItemClickListener) {
        this.mHorizontal = z;
        this.fullPosterThumbs = arrayList;
        this.mPager = z2;
        this.context = context;
        this.cat_id = i;
        this.ratio = str;
        this.appPreferenceClass = new AppPreferenceClass(context);
        this.listener = onItemClickListener;
    }

    public class PosterListViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        public ImageView imageView;
        ImageView ivLock;
        public ProgressBar mProgressBar;
        public TextView nameTextView;
        public TextView ratingTextView;
        public RelativeLayout rl_see_more;

        public PosterListViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = view.findViewById(R.id.imageView);
            this.ivLock = view.findViewById(R.id.iv_lock);
            this.nameTextView = view.findViewById(R.id.nameTextView);
            this.ratingTextView = view.findViewById(R.id.ratingTextView);
            this.mProgressBar = view.findViewById(R.id.progressBar1);
            this.rl_see_more = view.findViewById(R.id.rl_see_more);
        }

        @Override
        public void onClick(View view) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("==");
            stringBuilder.append((List_Poster_Category_Adapter.this.fullPosterThumbs.get(getAdapterPosition())).getPost_id());
            Log.d("BG_Images", stringBuilder.toString());
        }
    }

    @Override
    public PosterListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mPager) {
            return new PosterListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pager_adapter, viewGroup, false));
        }
        PosterListViewHolder posterListViewHolder;
        if (this.mHorizontal) {
            posterListViewHolder = new PosterListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customized_adapter, viewGroup, false));
        } else {
            posterListViewHolder = new PosterListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_vertical_adapter, viewGroup, false));
        }
        return posterListViewHolder;
    }

    @Override
    public int getItemViewType(int i) {
        return super.getItemViewType(i);
    }

    @Override
    public int getItemCount() {
        return this.fullPosterThumbs.size();
    }

    @Override
    public void onBindViewHolder(PosterListViewHolder posterListViewHolder, final int i) {
        if (i > 4) {
            posterListViewHolder.ivLock.setVisibility(GONE);
            posterListViewHolder.imageView.setVisibility(View.INVISIBLE);
            posterListViewHolder.rl_see_more.setVisibility(View.VISIBLE);
            posterListViewHolder.rl_see_more.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (List_Poster_Category_Adapter.this.listener != null) {
                        List_Poster_Category_Adapter.this.listener.onItemClick(i);
                    }
                }
            });
            return;
        }
        if (i <= 4 || this.appPreferenceClass.getInt(AppConstants.isRated, 0) != 0) {
            posterListViewHolder.ivLock.setVisibility(GONE);
        } else {
            posterListViewHolder.ivLock.setVisibility(GONE);
        }
        new Poster_Glide_IMG_Loader(posterListViewHolder.imageView, posterListViewHolder.mProgressBar).loadPosterImgFromStr(((Full_Poster_Thumb) this.fullPosterThumbs.get(i)).getPost_thumb(), new RequestOptions().centerCrop().priority(Priority.HIGH));
        posterListViewHolder.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                //   ((PosterCatActivity) List_Poster_Category_Adapter.this.context).openPosterActivity(((Full_Poster_Thumb) List_Poster_Category_Adapter.this.fullPosterThumbs.get(i)).getPost_id(), List_Poster_Category_Adapter.this.cat_id);
            }
        });
    }


}