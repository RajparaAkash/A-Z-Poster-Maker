package com.letsmake.atoz.design.custom_adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.handler.DB_Handler;
import com.letsmake.atoz.design.handler.Handle_Bitmap_Object;
import com.letsmake.atoz.design.handler.Template_InfoData;

public class Poster_Design_Adapter extends ArrayAdapter<Template_InfoData> {

    private static final String TAG = "DesignTemplateAdapter";

    private int height;
    private String catName;

    Context context;

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        DesignPosterViewHolder designPosterViewHolder;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_poster_design_thumbnail, null);
            designPosterViewHolder = new DesignPosterViewHolder(view);
            view.setTag(designPosterViewHolder);
        } else {
            designPosterViewHolder = (DesignPosterViewHolder) view.getTag();
        }
        Template_InfoData templateInfoData = (Template_InfoData) getItem(i);
        if (this.catName.equals("MY_TEMP")) {
            designPosterViewHolder.imgDeletePoster.setVisibility(View.VISIBLE);
            try {
                if (templateInfoData.getTHUMB_INFO_URI().toString().contains("thumb")) {
                    Glide.with(this.context).load(new File(templateInfoData.getTHUMB_INFO_URI()).getAbsoluteFile()).thumbnail(0.1f).apply(new RequestOptions().dontAnimate().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(designPosterViewHolder.mThumbnail);
                } else if (templateInfoData.getTHUMB_INFO_URI().toString().contains("raw")) {
                    Glide.with(this.context).load(findBitmapDataObject(Uri.parse(templateInfoData.getTHUMB_INFO_URI()).getPath()).imageByteArray).thumbnail(0.1f).apply(new RequestOptions().dontAnimate().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(designPosterViewHolder.mThumbnail);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                designPosterViewHolder.mThumbnail.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.no_image));
            }
        } else {
            Glide.with(this.context).load(this.context.getResources().getIdentifier(templateInfoData.getTHUMB_INFO_URI(), "drawable", this.context.getPackageName())).thumbnail(0.1f).apply(new RequestOptions().dontAnimate().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(designPosterViewHolder.mThumbnail);
        }
        designPosterViewHolder.imgDeletePoster.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Poster_Design_Adapter.this.showDesignOptionsDialog(i);
            }
        });
        return view;
    }

    class DesignPosterViewHolder {
        ImageView imgDeletePoster;
        ImageView mThumbnail;

        public DesignPosterViewHolder(View view) {
            this.mThumbnail = view.findViewById(R.id.image);
            this.imgDeletePoster = view.findViewById(R.id.iVDelete);
        }
    }

    public Poster_Design_Adapter(Context context, List<Template_InfoData> list, String str, int i) {
        super(context, 0, list);
        this.context = context;
        this.catName = str;
        this.height = i;
    }

    private Handle_Bitmap_Object findBitmapDataObject(String str) {
        try {
            return (Handle_Bitmap_Object) new ObjectInputStream(new FileInputStream(new File(str))).readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private void showDesignOptionsDialog(final int i) {
        final Dialog dialog = new Dialog(this.context, R.style.ThemeWithCorners);
        dialog.setContentView(R.layout.layout_delete_dialog);
        dialog.setCancelable(false);
        Button button = dialog.findViewById(R.id.btnCancel);
        (dialog.findViewById(R.id.btnDelete)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Template_InfoData templateInfoData = Poster_Design_Adapter.this.getItem(i);
                DB_Handler dbHandler = DB_Handler.getDatabaseHandler(Poster_Design_Adapter.this.context);
                boolean deleteTemplateInfo = dbHandler.remove_Template_Info(templateInfoData.getTEMPLATE_INFO_ID());
                dbHandler.close();
                if (deleteTemplateInfo) {
                    rmeoveFile(Uri.parse(templateInfoData.getTHUMB_INFO_URI()));
                    remove(templateInfoData);
                    notifyDataSetChanged();
                    dialog.dismiss();
                    return;
                }
                Toast.makeText(Poster_Design_Adapter.this.context, Poster_Design_Adapter.this.context.getResources().getString(R.string.del_error_toast), 0).show();
            }
        });
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public boolean rmeoveFile(Uri uri) {
        boolean z = false;
        try {
            File file = new File(uri.getPath());
            z = file.delete();
            if (file.exists()) {
                try {
                    z = file.getCanonicalFile().delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (file.exists()) {
                    z = this.context.getApplicationContext().deleteFile(file.getName());
                }
            }
            Context context = this.context;
            Context context2 = getContext();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getContext().getApplicationContext().getPackageName());
            stringBuilder.append(".provider");
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", FileProvider.getUriForFile(context2, stringBuilder.toString(), file)));
        } catch (Exception e2) {
            String str = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("deleteTempFile: ");
            stringBuilder2.append(e2);
            Log.e(str, stringBuilder2.toString());
        }
        return z;
    }


}
