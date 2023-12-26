package com.letsmake.atoz.design.custom_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.IntegerVersionSignature;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.listener.On_Item_Click_Listener;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver;

public class Adapter_Stiker extends Adapter<Adapter_Stiker.ViewHolder> {

    public OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;
    public ArrayList<BG_Image> BG_Images;
    public AppPreferenceClass appPreference;

    private boolean mHorizontal, mPager, isDownloadProgress = true;

    String color;
    Context context;
    int flagForActivity;
    On_Item_Click_Listener listener;

    SharedPreferences preferences;

    public class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements OnClickListener {
        public ImageView imageView;
        RelativeLayout imgDownload;
        ImageView ivLock;
        ProgressBar mProgressBar;
        TextView nameTextView;
        TextView ratingTextView;
        RelativeLayout rl_see_more;

        public void onClick(View view) {
        }

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imgDownload = view.findViewById(R.id.imgDownload);
            this.imageView = view.findViewById(R.id.imageView);
            this.ivLock = view.findViewById(R.id.iv_lock);
            this.nameTextView = view.findViewById(R.id.nameTextView);
            this.ratingTextView = view.findViewById(R.id.ratingTextView);
            this.mProgressBar = view.findViewById(R.id.progressBar1);
            this.rl_see_more = view.findViewById(R.id.rl_see_more);
        }
    }

    public Adapter_Stiker(Context context, boolean z, boolean z2, ArrayList<BG_Image> arrayList, int i, String str, On_Item_Click_Listener onItemClickListener) {
        this.mHorizontal = z;
        this.BG_Images = arrayList;
        this.mPager = z2;
        this.context = context;
        this.flagForActivity = i;
        this.appPreference = new AppPreferenceClass(this.context);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.color = str;
        this.listener = onItemClickListener;
    }



    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() {
            public void onDownloadComplete() {
                Adapter_Stiker.this.isDownloadProgress = true;
                Adapter_Stiker.this.notifyDataSetChanged();
            }

            public void onError(ANError aNError) {
                Adapter_Stiker.this.isDownloadProgress = true;
                Adapter_Stiker.this.notifyDataSetChanged();
                Toast.makeText(Adapter_Stiker.this.context, "Network Error", 0).show();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mPager) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_pager_adapter, viewGroup, false));
        }
        ViewHolder viewHolder;
        if (this.mHorizontal) {
            viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sticker_adapters, viewGroup, false));
        } else {
            viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_vertical_adapter, viewGroup, false));
        }
        return viewHolder;
    }

    public static String findFileNameFromUrl(String str) {
        return str.substring(str.lastIndexOf(47) + 1).split("\\?")[0].split("#")[0];
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override
    public int getItemViewType(int i) {
        return super.getItemViewType(i);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final BG_Image BG_Image = (BG_Image) this.BG_Images.get(i);
        if (i == this.BG_Images.size() - 1) {
            viewHolder.imgDownload.setVisibility(View.GONE);
            viewHolder.mProgressBar.setVisibility(View.GONE);
            viewHolder.ivLock.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.INVISIBLE);
            viewHolder.rl_see_more.setVisibility(View.VISIBLE);
            viewHolder.rl_see_more.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (Adapter_Stiker.this.listener != null) {
                        Adapter_Stiker.this.listener.onItemClick(i);
                    }
                }
            });
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppConstants.BASE_URL_BG);
        stringBuilder.append("/Sticker_List/");
        stringBuilder.append(BG_Image.getBGImage_url());
        String stringBuilder2 = stringBuilder.toString();
        String[] split = Uri.parse(stringBuilder2).getPath().split("/");
        final String str = split[split.length - 2];
        viewHolder.rl_see_more.setVisibility(View.GONE);
        viewHolder.imageView.setVisibility(View.VISIBLE);
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(this.appPreference.getString(AppConstants.sdcardPath));
        stringBuilder3.append("/cat/");
        stringBuilder3.append(str);
        stringBuilder3.append("/");
        stringBuilder3.append(findFileNameFromUrl(stringBuilder2));
        File file = new File(stringBuilder3.toString());
        if (file.exists()) {
            viewHolder.imgDownload.setVisibility(View.GONE);
            viewHolder.mProgressBar.setVisibility(View.GONE);
            Glide.with(this.context).load(file.getPath()).thumbnail(0.1f).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AppConstants.getAPPVersionInfo())).dontAnimate().fitCenter().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(viewHolder.imageView);
        } else {
            viewHolder.imgDownload.setVisibility(View.VISIBLE);
            viewHolder.mProgressBar.setVisibility(View.GONE);
            Glide.with(this.context).load(stringBuilder2).thumbnail(0.1f).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AppConstants.getAPPVersionInfo())).dontAnimate().fitCenter().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(viewHolder.imageView);
        }
        viewHolder.imgDownload.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!NetworkConnectivityReceiver.isConnected()) {
                    Toast.makeText(Adapter_Stiker.this.context, "No Internet Connection!!!", 0).show();
                } else if (Adapter_Stiker.this.isDownloadProgress) {
                    Adapter_Stiker.this.isDownloadProgress = false;
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(AppConstants.BASE_URL_BG);
                    stringBuilder.append("/Sticker_List/");
                    stringBuilder.append(BG_Image.getBGImage_url());
                    String stringBuilder2 = stringBuilder.toString();
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(Adapter_Stiker.this.appPreference.getString(AppConstants.sdcardPath));
                    stringBuilder3.append("/cat/");
                    stringBuilder3.append(str);
                    stringBuilder3.append("/");
                    File file = new File(stringBuilder3.toString());
                    String fileNameFromUrl = Adapter_Stiker.findFileNameFromUrl(stringBuilder2);
                    viewHolder.imgDownload.setVisibility(View.GONE);
                    Adapter_Stiker.this.DownoloadSticker(stringBuilder2, file.getPath(), fileNameFromUrl);
                } else {
                    Toast.makeText(Adapter_Stiker.this.context, "Please wait..", 0).show();
                }
            }
        });
        viewHolder.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(AppConstants.BASE_URL_BG);
                stringBuilder.append("/Sticker_List/");
                stringBuilder.append(BG_Image.getBGImage_url());
                String stringBuilder2 = stringBuilder.toString();
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(Adapter_Stiker.this.appPreference.getString(AppConstants.sdcardPath));
                stringBuilder3.append("/cat/");
                stringBuilder3.append(str);
                stringBuilder3.append("/");
                stringBuilder3.append(Adapter_Stiker.findFileNameFromUrl(stringBuilder2));
                File file = new File(stringBuilder3.toString());
                if (file.exists()) {
                    Adapter_Stiker.this.mSingleCallback.onClickCallBack(null, Adapter_Stiker.this.BG_Images, file.getPath(), (FragmentActivity) Adapter_Stiker.this.context, Adapter_Stiker.this.color);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.BG_Images.size();
    }
}
