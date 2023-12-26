package com.letsmake.atoz.design.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.IntegerVersionSignature;
import com.letsmake.atoz.design.activity.SelectBGIMGActivity;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.editor_intelligence.OnClickCallback;
import com.letsmake.atoz.design.poster_builder.BG_Image;

public class fragment_design_backgrounds extends Fragment {

    private ArrayList<BG_Image> BG_Images;

    RecyclerView RvItems;
    private Activity context;

    private int category_id = 0;


    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public static fragment_design_backgrounds newInstance() {
        return new fragment_design_backgrounds();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_fragment_bg_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Init(view);
    }

    private void Init(View view) {
        context = getActivity();

        RvItems = view.findViewById(R.id.RvItems);
        RvItems.setLayoutManager(new GridLayoutManager(context, 3));

        BG_Images = Fragment_BGImg.thumbnail_bg.get(category_id).getCategory_list();

        if (BG_Images != null) {
            StickerAdapter emojiAdapter = new StickerAdapter();
            RvItems.setAdapter(emojiAdapter);
        }

    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        public OnClickCallback<ArrayList<String>, Integer, String, Activity, String> mSingleCallback;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_backgorunds, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            Glide.with(getActivity()).load("http://bhargav.fadootutorial.com/uploads/" + BG_Images.get(position).getBGImage_url()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AppConstants.getAPPVersionInfo())).dontAnimate().override(200, 200).fitCenter().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(viewHolder.IvStickerImage);

            holder.IvStickerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((SelectBGIMGActivity) context).performNextTask("http://bhargav.fadootutorial.com/uploads/" + BG_Images.get(position).getBGImage_url());
                }
            });
        }

        @Override
        public int getItemCount() {
            if (BG_Images == null) {
                return 0;
            }
            return BG_Images.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView IvStickerImage;
            ProgressBar progressBar;

            ViewHolder(View itemView) {
                super(itemView);
                IvStickerImage = itemView.findViewById(R.id.IvStickerImage);
                progressBar = itemView.findViewById(R.id.progress);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }


    public File getCacheFolder(Context context) {
        File file;
        if (Environment.getExternalStorageState().equals("mounted")) {
            file = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if (!file.isDirectory()) {
                file.mkdirs();
            }
        } else {
            file = null;
        }
        return !file.isDirectory() ? context.getCacheDir() : file;
    }

}
