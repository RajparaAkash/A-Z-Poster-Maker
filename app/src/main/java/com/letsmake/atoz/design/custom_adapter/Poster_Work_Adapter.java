package com.letsmake.atoz.design.custom_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.listener.On_Click_Callback;


public class Poster_Work_Adapter extends BaseAdapter {

    public On_Click_Callback<ArrayList<String>, Integer, String, Context> mSingleCallback;

    private File[] listFile;
    private int screenWidth;

    Context context;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        DemoViewHolder demoViewHolder;
        View inflate;
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            demoViewHolder = new DemoViewHolder();
            inflate = layoutInflater.inflate(R.layout.item_grid_thumbnail, null);
            demoViewHolder.imageview = inflate.findViewById(R.id.image);
            demoViewHolder.imgShare = inflate.findViewById(R.id.imgSharePoster);
            inflate.setTag(demoViewHolder);
        } else {
            inflate = view;
            demoViewHolder = (DemoViewHolder) view.getTag();
        }

        demoViewHolder.imageview.setId(i);
        Glide.with(this.context).load(listFile[i]).thumbnail(0.1f).apply(new RequestOptions().dontAnimate().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(demoViewHolder.imageview);

        demoViewHolder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSocialMediashare(listFile[i]);
            }
        });

        return inflate;
    }

    public void toSocialMediashare(File file) {
        try {
            Uri parse = Uri.parse(file.getPath());
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("*/*");
            intent.putExtra("android.intent.extra.STREAM", parse);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(context.getString(R.string.share_text));
            stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
            stringBuilder2.append(context.getPackageName());
            intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
            context.startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Poster_Work_Adapter(Context context, File[] fileArr, int i) {
        this.context = context;
        listFile = fileArr;
        this.screenWidth = i;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    public void setItemClickCallback(On_Click_Callback onClickCallback) {
        this.mSingleCallback = onClickCallback;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        if (listFile == null)
            return 0;

        return listFile.length;
    }

}
