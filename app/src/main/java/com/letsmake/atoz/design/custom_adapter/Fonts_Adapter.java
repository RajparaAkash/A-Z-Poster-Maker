package com.letsmake.atoz.design.custom_adapter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.app_utils.AppConfiguration;
import com.letsmake.atoz.design.listener.On_Click_Callback;

public class Fonts_Adapter extends BaseAdapter {

    private Activity mContext;

    private static final String TAG = "FontAdapter";
    private final String[] Imageid;
    private int arraySize;
    private File[] fontFile;

    private boolean isDownloadProgress = true;

    public On_Click_Callback<ArrayList<String>, Integer, String, Activity> mSingleCallback;
    int selPos = -1;

    public Fonts_Adapter(Activity activity, String[] strArr) {
        this.mContext = activity;
        this.Imageid = strArr;
        this.arraySize = strArr.length;
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public int getCount() {
        return this.arraySize;
    }

    @Override
    public Object getItem(int i) {
        return Integer.valueOf(0);
    }

    public void setItemClickCallback(On_Click_Callback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.item_grid_text_fonts, null);
            viewHolder = new ViewHolder();
            viewHolder.layItem = (RelativeLayout) view.findViewById(R.id.layItem);
            viewHolder.txtView = (TextView) view.findViewById(R.id.grid_text);
            viewHolder.txtDownloadFont = (ImageView) view.findViewById(R.id.txtDownloadFont);
            viewHolder.downloadProgress = (ProgressBar) view.findViewById(R.id.downloadProgress);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.downloadProgress.setVisibility(View.INVISIBLE);
        String[] strArr = this.Imageid;
        StringBuilder stringBuilder;
        if (i < strArr.length) {
            viewHolder.txtDownloadFont.setVisibility(View.GONE);
            if (i == 0) {
                viewHolder.txtView.setTypeface(Typeface.DEFAULT);
            } else {
                MimeTypeMap.getFileExtensionFromUrl(this.Imageid[i]);
                if (i > 24) {
                    try {
                        viewHolder.txtView.setText(this.Imageid[i]);
                    } catch (Exception unused) {
                        Log.e(TAG, "getView: font not found");
                    }
                }
                TextView textView = viewHolder.txtView;
                AssetManager assets = this.mContext.getAssets();
                stringBuilder = new StringBuilder();
                stringBuilder.append("font/");
                stringBuilder.append(this.Imageid[i]);
                textView.setTypeface(Typeface.createFromAsset(assets, stringBuilder.toString()));
            }
        } else {
            int length = i - strArr.length;
            File[] fileArr = this.fontFile;
            if (fileArr != null) {
                String substring = fileArr[length].getAbsolutePath().substring(this.fontFile[length].getAbsolutePath().lastIndexOf("/") + 1);
                stringBuilder = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(AppConfiguration.takeFileDir(this.mContext).getPath());
                stringBuilder2.append(File.separator);
                stringBuilder2.append("font/");
                stringBuilder.append(new File(stringBuilder2.toString()).getPath());
                stringBuilder.append("/");
                stringBuilder.append(substring);
                File file = new File(stringBuilder.toString());
                TextView textView2 = viewHolder.txtView;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(substring);
                stringBuilder3.append("");
                textView2.setText(stringBuilder3.toString());
                if (file.exists()) {
                    viewHolder.txtDownloadFont.setVisibility(View.GONE);
                    try {
                        viewHolder.txtView.setTypeface(Typeface.createFromFile(file));
                    } catch (RuntimeException unused2) {
                        Log.e(TAG, "getView: RuntimeException font not found");
                        viewHolder.txtView.setTypeface(Typeface.DEFAULT);
                    }
                }
            }
        }
        viewHolder.txtView.setTextColor(this.mContext.getResources().getColor(R.color.white));
        viewHolder.txtDownloadFont.setVisibility(View.GONE);
        viewHolder.txtView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (i < Fonts_Adapter.this.Imageid.length) {
                    Fonts_Adapter.this.mSingleCallback.onClickCallBack(null, Integer.valueOf(i), Fonts_Adapter.this.Imageid[i], Fonts_Adapter.this.mContext);
                    return;
                }
                int length = i - Fonts_Adapter.this.Imageid.length;
                if (Fonts_Adapter.this.fontFile != null) {
                    String substring = Fonts_Adapter.this.fontFile[length].getAbsolutePath().substring(Fonts_Adapter.this.fontFile[length].getAbsolutePath().lastIndexOf("/") + 1);
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(AppConfiguration.takeFileDir(Fonts_Adapter.this.mContext).getPath());
                    stringBuilder2.append(File.separator);
                    stringBuilder2.append("font/");
                    stringBuilder.append(new File(stringBuilder2.toString()).getPath());
                    stringBuilder.append("/");
                    stringBuilder.append(substring);
                    if (new File(stringBuilder.toString()).exists()) {
                        Fonts_Adapter.this.mSingleCallback.onClickCallBack(null, Integer.valueOf(i), substring, Fonts_Adapter.this.mContext);
                    }
                }
            }
        });
        return view;
    }

    public class ViewHolder {
        ProgressBar downloadProgress;
        RelativeLayout layItem;
        ImageView txtDownloadFont;
        TextView txtView;
    }


    public void setSelected(int i) {
        this.selPos = i;
        notifyDataSetChanged();
    }
}
