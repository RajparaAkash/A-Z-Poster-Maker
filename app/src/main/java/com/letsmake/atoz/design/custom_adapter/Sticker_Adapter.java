package com.letsmake.atoz.design.custom_adapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.IntegerVersionSignature;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver;

public class Sticker_Adapter extends Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {

    private static final String TAG = "Sticker_Adapter";

    private ArrayList<BG_Image> category_list;
    private AppPreferenceClass appPreference;
    private int cellLimit, cellPadding, cellSize;

    String color;

    Activity context;

    public boolean isDownloadProgress = true;

    public OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;
    int size = 0;


    public class StickerViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        ProgressBar downloadProgress;
        ImageView imageView;
        RelativeLayout imgDownload;
        RelativeLayout layout;
        TextView name;

        public StickerViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.item_image);
            this.imgDownload = view.findViewById(R.id.imgDownload);
            this.name = view.findViewById(R.id.name);
            this.layout = view.findViewById(R.id.lay);
            this.downloadProgress = view.findViewById(R.id.downloadProgress);
        }
    }

    public class LoadingHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public LoadingHolder(View view) {
            super(view);
        }
    }


    public void DownoloadSticker(String str, String str2, String str3) {
        AndroidNetworking.download(str, str2, str3).build().startDownload(new DownloadListener() {
            public void onDownloadComplete() {
                Sticker_Adapter.this.isDownloadProgress = true;
                Sticker_Adapter.this.notifyDataSetChanged();
                Log.e(Sticker_Adapter.TAG, "onDownloadComplete: ");
            }

            public void onError(ANError aNError) {
                Sticker_Adapter.this.isDownloadProgress = true;
                Log.e(Sticker_Adapter.TAG, "onError: ");
                Sticker_Adapter.this.notifyDataSetChanged();
                Toast.makeText(Sticker_Adapter.this.context, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public long getItemId(int i) {
        return (long) i;
    }

    public Sticker_Adapter(Activity activity, ArrayList<BG_Image> arrayList, int i, int i2, String str) {
        this.context = activity;
        this.appPreference = new AppPreferenceClass(this.context);
        this.category_list = arrayList;
        this.cellSize = i;
        this.cellPadding = i2;
        this.cellLimit = 0;
        this.color = str;
    }

    @Override
    public int getItemCount() {
        int size = this.category_list.size();
        int i = this.cellLimit;
        return i > 0 ? Math.min(size, i) : size;
    }

    public void insertData(List<BG_Image> list) {
        notifyDataSetChanged();
    }

    public void insertLoadingView() {
        new Handler().post(new Runnable() {
            public void run() {
                Sticker_Adapter.this.category_list.add(null);
                Sticker_Adapter stickerAdapter = Sticker_Adapter.this;
                stickerAdapter.notifyItemInserted(stickerAdapter.category_list.size() - 1);
            }
        });
    }

    public void hideLoadingView() {
        ArrayList arrayList = this.category_list;
        arrayList.remove(arrayList.size() - 1);
        notifyItemRemoved(this.category_list.size());
    }

    public static String getFileNameFromUrl(String str) {
        return str.substring(str.lastIndexOf(47) + 1).split("\\?")[0].split("#")[0];
    }

    public void setItemClickCallback(OnClickCallback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    public void setLayoutParams(int i) {
        this.size = i;
    }

    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new StickerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row_sticker_list, viewGroup, false));
        }
        return new LoadingHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cure_progress_view, viewGroup, false));
    }

    public int getItemViewType(int i) {
        return this.category_list.get(i) == null ? 1 : 0;
    }


    @Override
    public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == 0) {
            final StickerViewHolder stickerViewHolder2 = (StickerViewHolder) viewHolder;
            final BG_Image BG_Image = (BG_Image) this.category_list.get(i);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(AppConstants.BASE_URL_BG);
            stringBuilder.append("/Sticker_List/");
            stringBuilder.append(BG_Image.getBGImage_url());
            String stringBuilder2 = stringBuilder.toString();


            String[] split = Uri.parse(stringBuilder2).getPath().split("/");
            final String str = split[split.length - 2];
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("==");
            stringBuilder3.append(str);
            Log.e("url", stringBuilder3.toString());
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(this.appPreference.getString(AppConstants.sdcardPath));
            stringBuilder3.append("/cat/");
            stringBuilder3.append(str);
            stringBuilder3.append("/");
            stringBuilder3.append(getFileNameFromUrl(stringBuilder2));
            File file = new File(stringBuilder3.toString());
            if (file.exists()) {
                stickerViewHolder2.downloadProgress.setVisibility(View.GONE);
                stickerViewHolder2.imgDownload.setVisibility(View.GONE);
                Glide.with(this.context).load(file.getPath()).thumbnail(0.1f).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AppConstants.getAPPVersionInfo())).dontAnimate().fitCenter().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(stickerViewHolder2.imageView);
            } else {
                stickerViewHolder2.downloadProgress.setVisibility(View.GONE);
                stickerViewHolder2.imgDownload.setVisibility(View.VISIBLE);
                Glide.with(this.context).load(stringBuilder2).thumbnail(0.1f).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AppConstants.getAPPVersionInfo())).dontAnimate().fitCenter().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(stickerViewHolder2.imageView);
            }
            stickerViewHolder2.imgDownload.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (!NetworkConnectivityReceiver.isConnected()) {
                        Toast.makeText(Sticker_Adapter.this.context, "No Internet Connection!!!", Toast.LENGTH_SHORT).show();
                    } else if (Sticker_Adapter.this.isDownloadProgress) {
                        Sticker_Adapter.this.isDownloadProgress = false;
                        stickerViewHolder2.downloadProgress.setVisibility(View.VISIBLE);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(AppConstants.BASE_URL_BG);
                        stringBuilder.append("/");
                        stringBuilder.append(BG_Image.getBGImage_url());
                        String stringBuilder2 = stringBuilder.toString();
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(Sticker_Adapter.this.appPreference.getString(AppConstants.sdcardPath));
                        stringBuilder3.append("/cat/");
                        stringBuilder3.append(str);
                        stringBuilder3.append("/");
                        File file = new File(stringBuilder3.toString());
                        DownoloadSticker(stringBuilder2, file.getPath(), Sticker_Adapter.getFileNameFromUrl(stringBuilder2));
                    } else {
                        Toast.makeText(Sticker_Adapter.this.context, "Please wait..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            stickerViewHolder2.layout.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(AppConstants.BASE_URL_STICKER);
                    stringBuilder.append("/");
                    stringBuilder.append(BG_Image.getBGImage_url());
                    String stringBuilder2 = stringBuilder.toString();
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(Sticker_Adapter.this.appPreference.getString(AppConstants.sdcardPath));
                    stringBuilder3.append("/cat/");
                    stringBuilder3.append(str);
                    stringBuilder3.append("/");
                    stringBuilder3.append(Sticker_Adapter.getFileNameFromUrl(stringBuilder2));
                    File file = new File(stringBuilder3.toString());
                    if (file.exists()) {
                        Sticker_Adapter.this.mSingleCallback.onClickCallBack(null, Sticker_Adapter.this.category_list, file.getPath(), Sticker_Adapter.this.context, Sticker_Adapter.this.color);
                    }
                }
            });
        }
    }

}
