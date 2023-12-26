package com.letsmake.atoz.design.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.Purchase;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.letsmake.atoz.design.Application;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.IntegerVersionSignature;
import com.letsmake.atoz.design.ads.InterstitialAds;
import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.letsmake.atoz.design.poster_builder.BG_Image;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragment_stickers extends Fragment implements BillingUpdatesListener {

    private ArrayList<BG_Image> stickerImages;

    RecyclerView RvStickerItems;
    private Activity context;
    boolean isActive;

    private int category_id = 0;

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public static fragment_stickers newInstance() {
        return new fragment_stickers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity();


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_fragment_sticker_list, container, false);

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            Init(view);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void Init(View view) {
        context = getActivity();

        RvStickerItems = view.findViewById(R.id.RvStickerItems);
        RvStickerItems.setLayoutManager(new GridLayoutManager(context, 3));

        stickerImages = Fragment_Get_Stickers.thumbnail_bg.get(category_id).getCategory_list();

        if (stickerImages != null) {
            StickerAdapter stickerAdapter = new StickerAdapter();
            RvStickerItems.setAdapter(stickerAdapter);
        }

    }

    @Override
    public void onBillingClientSetupFinished() {

    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchases) {
        isActive = SubscriptionsUtil.isSubscriptionActive(purchases);
    }

    @Override
    public void onPurchaseVerified() {

    }

    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_sticker, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            ViewHolder viewHolder = (ViewHolder) holder;

            Glide.with(getActivity()).load("http://bhargav.fadootutorial.com/uploads/" + stickerImages.get(position).getBGImage_url()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).signature(new IntegerVersionSignature(AppConstants.getAPPVersionInfo())).dontAnimate().override(200, 200).fitCenter().placeholder((int) R.drawable.no_image).error((int) R.drawable.no_image)).into(viewHolder.IvStickerImage);

            PushDownAnim.setPushDownAnimTo(holder.IvStickerImage);
            holder.IvStickerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!isActive) {

                        new InterstitialAds().Show_Ads(getActivity(), new InterstitialAds.AdCloseListener() {
                            @Override
                            public void onAdClosed() {
                                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setMessage(getResources().getString(R.string.plzwait));
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                fragment_stickers fragment_stickers = fragment_stickers.this;
                                final File cacheFolder = fragment_stickers.getCacheFolder(fragment_stickers.getContext());
                                Application.getInstance().addToRequestQueue(new ImageRequest("http://bhargav.fadootutorial.com/uploads/" + stickerImages.get(position).getBGImage_url(), new Response.Listener<Bitmap>() {
                                    public void onResponse(Bitmap bitmap) {
                                        try {
                                            progressDialog.dismiss();
                                            try {
                                                File file = new File(cacheFolder, "localFileName.png");
                                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                                fileOutputStream.flush();
                                                fileOutputStream.close();
                                                try {
                                                    Fragment_BGImg.onGetSnap.onSnapFilter(0, 34, file.getAbsolutePath(), "");
                                                } catch (Exception e) {
                                                    try {
                                                        e.printStackTrace();
                                                    } catch (NullPointerException e2) {
                                                        e2.printStackTrace();
                                                    }
                                                }
                                            } catch (FileNotFoundException e3) {
                                                e3.printStackTrace();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        } catch (Exception e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                }, 0, 0, null, new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError volleyError) {
                                        progressDialog.dismiss();
                                    }
                                }));
                            }
                        });
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage(getResources().getString(R.string.plzwait));
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        fragment_stickers fragment_stickers = fragment_stickers.this;
                        final File cacheFolder = fragment_stickers.getCacheFolder(fragment_stickers.getContext());
                        Application.getInstance().addToRequestQueue(new ImageRequest("http://bhargav.fadootutorial.com/uploads/" + stickerImages.get(position).getBGImage_url(), new Response.Listener<Bitmap>() {
                            public void onResponse(Bitmap bitmap) {
                                try {
                                    progressDialog.dismiss();
                                    try {
                                        File file = new File(cacheFolder, "localFileName.png");
                                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                                        fileOutputStream.flush();
                                        fileOutputStream.close();
                                        try {
                                            Fragment_BGImg.onGetSnap.onSnapFilter(0, 34, file.getAbsolutePath(), "");
                                        } catch (Exception e) {
                                            try {
                                                e.printStackTrace();
                                            } catch (NullPointerException e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                    } catch (FileNotFoundException e3) {
                                        e3.printStackTrace();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                            }
                        }, 0, 0, null, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError volleyError) {
                                progressDialog.dismiss();
                            }
                        }));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (stickerImages == null) {
                return 0;
            }
            return stickerImages.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView IvStickerImage;
            ProgressBar progressBar;

            ViewHolder(View itemView) {
                super(itemView);
                IvStickerImage = itemView.findViewById(R.id.IvStickerImage);
                progressBar = itemView.findViewById(R.id.progress);
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
