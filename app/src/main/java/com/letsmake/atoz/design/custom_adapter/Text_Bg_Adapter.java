package com.letsmake.atoz.design.custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.letsmake.atoz.design.R;

public class Text_Bg_Adapter extends Adapter<Text_Bg_Adapter.TextBGHolder> {
    Context context;
    int[] makeUpEditImage;
    int selected_position = 500;

    @Override
    public void onBindViewHolder(TextBGHolder textBGHolder, final int i) {
        Glide.with(this.context).load(Integer.valueOf(this.makeUpEditImage[i])).thumbnail(0.1f).apply(new RequestOptions().dontAnimate().centerCrop().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(textBGHolder.imageView);
        if (this.selected_position == i) {
            textBGHolder.viewImage.setVisibility(View.VISIBLE);
        } else {
            textBGHolder.viewImage.setVisibility(View.INVISIBLE);
        }
        textBGHolder.layout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Text_Bg_Adapter textBgAdapter = Text_Bg_Adapter.this;
                textBgAdapter.notifyItemChanged(textBgAdapter.selected_position);
                textBgAdapter = Text_Bg_Adapter.this;
                textBgAdapter.selected_position = i;
                textBgAdapter.notifyItemChanged(textBgAdapter.selected_position);
            }
        });
    }

    @Override
    public TextBGHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        TextBGHolder textBGHolder = new TextBGHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleview_bg_adapter, viewGroup, false));
        viewGroup.setId(i);
        viewGroup.setFocusable(false);
        viewGroup.setFocusableInTouchMode(false);
        return textBGHolder;
    }

    public static class TextBGHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView viewImage, imageView;

        public TextBGHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.item_image);
            this.viewImage = view.findViewById(R.id.view_image);
            this.layout = view.findViewById(R.id.lay);
        }
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return this.makeUpEditImage.length;
    }

    public void setSelected(int i) {
        this.selected_position = i;
        notifyDataSetChanged();
    }

    public Text_Bg_Adapter(Context context, int[] iArr) {
        this.context = context;
        this.makeUpEditImage = iArr;
    }

}
