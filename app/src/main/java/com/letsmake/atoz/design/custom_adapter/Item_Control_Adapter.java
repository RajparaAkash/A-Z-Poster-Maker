package com.letsmake.atoz.design.custom_adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.util.Pair;

import com.woxthebox.draglistview.DragItemAdapter;

import java.util.ArrayList;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.custom_sticker.IntelligentTVAutoFit;
import com.letsmake.atoz.design.custom_view.AutoStickerView;
import com.letsmake.atoz.design.custom_view.TextView_ReSized;
import com.letsmake.atoz.design.imageloader.Glide_Image_Utils;

public class Item_Control_Adapter extends DragItemAdapter<Pair<Long, View>, Item_Control_Adapter.ViewHolder> {
    Activity activity;

    private boolean mDragOnLongPress;

    private int mGrabHandleId, mLayoutId;


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);

        final View view = ( this.mItemList.get(i)).second;
        try {
            if (view instanceof AutoStickerView) {
                View childAt = ((AutoStickerView) view).getChildAt(1);
                Bitmap createBitmap = Bitmap.createBitmap(childAt.getWidth(), childAt.getHeight(), Config.ARGB_8888);
                childAt.draw(new Canvas(createBitmap));
                float[] fArr = new float[9];
                ((ImageView) childAt).getImageMatrix().getValues(fArr);
                float f = fArr[0];
                float f2 = fArr[4];
                Drawable drawable = ((ImageView) childAt).getDrawable();
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int round = Math.round(((float) intrinsicWidth) * f);
                int round2 = Math.round(((float) intrinsicHeight) * f2);
                viewHolder.mImage.setImageBitmap(Bitmap.createBitmap(createBitmap, (createBitmap.getWidth() - round) / 2, (createBitmap.getHeight() - round2) / 2, round, round2));
                viewHolder.mImage.setRotationY(childAt.getRotationY());
                viewHolder.mImage.setTag(this.mItemList.get(i));
                viewHolder.mImage.setAlpha(1.0f);
                viewHolder.textView.setText(" ");

/*
                View viewn = view;
                ((StickerView) viewn).isMultiTouchEnabled = ((StickerView) viewn).useDefaultTouchListener(false);
                viewHolder.img_lock.setImageResource(R.drawable.btn_layer_lock);
*/

            }
            if (view instanceof IntelligentTVAutoFit) {
                viewHolder.textView.setText(((TextView_ReSized) ((IntelligentTVAutoFit) view).getChildAt(2)).getText());
                viewHolder.textView.setTypeface(((TextView_ReSized) ((IntelligentTVAutoFit) view).getChildAt(2)).getTypeface());
                viewHolder.textView.setTextColor(((TextView_ReSized) ((IntelligentTVAutoFit) view).getChildAt(2)).getTextColors());
                viewHolder.textView.setGravity(17);
                viewHolder.textView.setMinTVTextSize(10.0f);
                if (((IntelligentTVAutoFit) view).getTVTextInfo().getTV_BG_COLOR() != 0) {
                    Bitmap createBitmap2 = Bitmap.createBitmap(150, 150, Config.ARGB_8888);
                    new Canvas(createBitmap2).drawColor(((IntelligentTVAutoFit) view).getTVTextInfo().getTV_BG_COLOR());
                    viewHolder.mImage.setImageBitmap(createBitmap2);
                    viewHolder.mImage.setAlpha(((float) ((IntelligentTVAutoFit) view).getTVTextInfo().getTV_BG_ALPHA()) / 255.0f);
                } else if (((IntelligentTVAutoFit) view).getTVTextInfo().getTV_BG_DRAWABLE().equals("0")) {
                    viewHolder.mImage.setAlpha(1.0f);
                    viewHolder.mImage.setImageResource(R.drawable.bg_trans);
                } else {
                    viewHolder.mImage.setImageBitmap(Glide_Image_Utils.getGlideTiledBitmap(this.activity, this.activity.getResources().getIdentifier(((IntelligentTVAutoFit) view).getTVTextInfo().getTV_BG_DRAWABLE(), "drawable", this.activity.getPackageName()), 150, 150));
                    viewHolder.mImage.setAlpha(((float) ((IntelligentTVAutoFit) view).getTVTextInfo().getTV_BG_ALPHA()) / 255.0f);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (view instanceof AutoStickerView) {
            if (((AutoStickerView) view).isMultiTouchEnabled) {
                viewHolder.img_lock.setImageResource(R.drawable.poster_layer_unlock);
            } else {
                viewHolder.img_lock.setImageResource(R.drawable.btn_layer_lock);
            }
        }
        if (view instanceof IntelligentTVAutoFit) {
            if (((IntelligentTVAutoFit) view).isMultiTouchEnabled) {
                viewHolder.img_lock.setImageResource(R.drawable.poster_layer_unlock);
            } else {
                viewHolder.img_lock.setImageResource(R.drawable.btn_layer_lock);
            }
        }
        viewHolder.img_lock.setOnClickListener(new OnClickListener() {
            public void onClick(View viewanother) {
                Log.e("###", "clicked");

                MediaPlayer.create(activity, R.raw.lock_unlock).start();

                View view2 = view;
                if (view2 instanceof AutoStickerView) {
                    if (((AutoStickerView) view2).isMultiTouchEnabled) {
                        View view3 = view;
                        ((AutoStickerView) view3).isMultiTouchEnabled = ((AutoStickerView) view3).applyDefaultTouchListener(false);
                        viewHolder.img_lock.setImageResource(R.drawable.btn_layer_lock);
                    } else {
                        View view4 = view;
                        ((AutoStickerView) view4).isMultiTouchEnabled = ((AutoStickerView) view4).applyDefaultTouchListener(true);
                        viewHolder.img_lock.setImageResource(R.drawable.poster_layer_unlock);
                    }
                }
                View view5 = view;
                if (view5 instanceof IntelligentTVAutoFit) {
                    if (((IntelligentTVAutoFit) view5).isMultiTouchEnabled) {
                        View view6 = view;
                        ((IntelligentTVAutoFit) view6).isMultiTouchEnabled = ((IntelligentTVAutoFit) view6).setTvDefaultTouchListener(false);
                        viewHolder.img_lock.setImageResource(R.drawable.btn_layer_lock);
                        return;
                    }
                    View view7 = view;
                    ((IntelligentTVAutoFit) view7).isMultiTouchEnabled = ((IntelligentTVAutoFit) view7).setTvDefaultTouchListener(true);
                    viewHolder.img_lock.setImageResource(R.drawable.poster_layer_unlock);
                }
            }
        });
    }

    public Item_Control_Adapter(Activity activity, ArrayList<Pair<Long, View>> arrayList, int i, int i2, boolean z) {
        this.mLayoutId = i;
        this.mGrabHandleId = i2;
        this.activity = activity;
        this.mDragOnLongPress = z;
        setItemList(arrayList);

    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.mLayoutId, viewGroup, false));
    }


    public long getUniqueItemId(int i) {
        return ( ( mItemList.get(i)).first).longValue();
    }

    class ViewHolder extends DragItemAdapter.ViewHolder {
        ImageView img_lock;
        ImageView mImage;
        TextView_ReSized textView;

        public void onItemClicked(View view) {
        }

        public boolean onItemLongClicked(View view) {
            return true;
        }

        ViewHolder(View view) {
            super(view, Item_Control_Adapter.this.mGrabHandleId, Item_Control_Adapter.this.mDragOnLongPress);
            this.mImage = view.findViewById(R.id.image1);
            this.img_lock = view.findViewById(R.id.img_lock);
            this.textView = view.findViewById(R.id.auto_fit_edit_text);
        }
    }
}