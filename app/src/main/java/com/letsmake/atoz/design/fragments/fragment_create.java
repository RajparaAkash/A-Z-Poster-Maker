package com.letsmake.atoz.design.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.billingclient.api.Purchase;
import com.google.android.gms.common.Scopes;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.ChooseSizeActivity;
import com.letsmake.atoz.design.activity.PosterMAKERActivity;
import com.letsmake.atoz.design.activity.SubscriptionActivity;

import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.model.ScratchModel;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;


public class fragment_create extends Fragment implements BillingUpdatesListener {

    Typeface typefaceTextBold, typefaceTextNormal;

    TextView tvTitle;
    ImageView iVCreate;

    RecyclerView rvScratchList;
    HomeCardAdapter homeCardAdapter;

    public static String width, height;

    int[] scratchImages = {R.drawable.poster_custom_size, R.drawable.poster_standard_flyer, R.drawable.poster_instagram_square, R.drawable.poster_instagram_portrait, R.drawable.poster_instagram_story, R.drawable.poster_pinterest, R.drawable.poster_twitter_cover, R.drawable.poster_twitter_post, R.drawable.poster_snapchat_geofilter, R.drawable.poster_facebook_square, R.drawable.poster_facebook_cover, R.drawable.poster_youtube_thumbnail, R.drawable.poster_cinematic, R.drawable.poster_linkedin_background, R.drawable.poster_cover_photo, R.drawable.poster_linkedin_cover, R.drawable.poster_1_2, R.drawable.poster_3_4, R.drawable.poster_9_16, R.drawable.poster_5_4, R.drawable.poster_3_2, R.drawable.poster_16_9};
    String[] ratioImages = {"1:1", "13:16", "1:1", "4:5", "9:16", "3:4", "3:1", "2:1", "9:16", "1:1", "16:9", "16:9", "16:9", "4:1", "16:9", "16:9", "1:2", "3:4", "9:16", "5:4", "3:2", "16:9"};
    public static String[] bgMakers = {"1200:1200", "1300:1600", "1200:1200", "1200:1600", "1350:2400", "1200:1600", "1500:500", "1400:700", "1350:2400", "1400:1400", "1280:720", "1360:765", "1280:720", "1200:300", "1280:720", "1360:765", "1000:2000", "1200:1600", "1350:2400", "1200:1600", "1200:800", "1280:720"};

    ArrayList<ScratchModel> scratchModelArrayList;

    int newWidth;

    public boolean isActive;


    @Override
    public void onDestroy() {
        
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_fragment_create, container, false);

        

        scratchModelArrayList = new ArrayList<ScratchModel>();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        newWidth = size.x;
        newWidth = newWidth / 2;

        tvTitle = view.findViewById(R.id.tvTitle);
        Typeface custom_title = Typeface.createFromAsset(getActivity().getAssets(), "font/cabin.ttf");
        tvTitle.setTypeface(custom_title);

        rvScratchList = view.findViewById(R.id.rvScratchList);
        //
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rvScratchList.setLayoutManager(layoutManager);

        homeCardAdapter = new HomeCardAdapter(scratchImages);
        rvScratchList.setAdapter(homeCardAdapter);

        /*PulsatorLayout pulsator = view.findViewById(R.id.pulsator);
        pulsator.start();
*/
        iVCreate = view.findViewById(R.id.iVCreate);

        iVCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Changed 26/05/2023 */
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                Intent intent = new Intent(getActivity(), ChooseSizeActivity.class);
                                startActivity(intent);
                            } else {
                                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).withErrorListener(new PermissionRequestErrorListener() {
                        public void onError(DexterError dexterError) {
                            Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                        }
                    }).onSameThread().check();
                } else {
                    Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                Intent intent = new Intent(getActivity(), ChooseSizeActivity.class);
                                startActivity(intent);
                            } else {
                                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).withErrorListener(new PermissionRequestErrorListener() {
                        public void onError(DexterError dexterError) {
                            Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                        }
                    }).onSameThread().check();
                }
            }
        });

        return view;
    }

    public boolean permission() {
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addTextEditorDialog(String ratio) {

        typefaceTextBold = Typeface.createFromAsset(getActivity().getAssets(), "font/Montserrat-SemiBold.ttf");
        typefaceTextNormal = Typeface.createFromAsset(getActivity().getAssets(), "font/Montserrat-Medium.ttf");

        final Dialog dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
        dialog.setContentView(R.layout.dialog_create_scratch_widhight);
        dialog.setCancelable(false);
        TextView textView = dialog.findViewById(R.id.txtTitle);
        textView.setTypeface(typefaceTextBold);
        EditText edtWidth = dialog.findViewById(R.id.edt_width);
        edtWidth.setTypeface(typefaceTextNormal);
        EditText edtHeight = dialog.findViewById(R.id.edt_height);
        edtHeight.setTypeface(typefaceTextNormal);
        Button button = dialog.findViewById(R.id.btnCancelDialog);
        button.setTypeface(typefaceTextNormal);
        Button button2 = dialog.findViewById(R.id.btnAddTextSDialog);
        button2.setTypeface(typefaceTextNormal);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (edtWidth.getText().toString().isEmpty()) {
                    edtWidth.setError("Enter valid width");
                } else if ((Integer.parseInt(edtWidth.getText().toString()) < 500) || (Integer.parseInt(edtWidth.getText().toString()) > 1400)) {
                    edtWidth.setError("Width should be less than 1400 & greater than 500");
                } else if (edtHeight.getText().toString().isEmpty()) {
                    edtHeight.setError("Enter valid height");
                } else if ((Integer.parseInt(edtHeight.getText().toString()) < 500) || (Integer.parseInt(edtHeight.getText().toString()) > 3000)) {
                    edtHeight.setError("Height should be less than 3000 & greater than 500");
                } else {

                    width = edtWidth.getText().toString();
                    height = edtHeight.getText().toString();

                    Intent intent = new Intent(getActivity(), PosterMAKERActivity.class);
                    intent.putExtra("ratio", ratio);
                    intent.putExtra("loadUserFrame", true);
                    intent.putExtra(Scopes.PROFILE, "http://fadootutorial.com/primeinfotech/sizebg/" + ratio);
                    intent.putExtra("hex", "");
                    intent.putExtra("pos", String.valueOf(0));
                    startActivity(intent);

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
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

/*
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
*/

    class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ViewHolder> {

        int[] itemList;

        HomeCardAdapter(int[] items) {
            itemList = items;
        }

        @NonNull
        @Override
        public HomeCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card_scratch_templates, viewGroup, false);
            return new HomeCardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HomeCardAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

            String ratio = ratioImages[i];

            float y = 1;
            if (ratio != null) {
                String[] widthheight = ratio.split(":");
                y = Float.parseFloat(widthheight[1]) / Float.parseFloat(widthheight[0]);
            }

            int newHeight = (int) (newWidth * y);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(newWidth, newHeight);
            viewHolder.image.requestLayout();
            viewHolder.image.setLayoutParams(params);

            viewHolder.image.setImageResource(itemList[i]);

            PushDownAnim.setPushDownAnimTo(viewHolder.cardView);
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Changed 26/05/2023 */
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (i == 0) {
                                        if (isActive) {
                                            addTextEditorDialog(ratio);
                                        } else {
                                            startActivity(new Intent(getActivity(), SubscriptionActivity.class));
                                        }
                                    } else {
                                        Intent intent = new Intent(getActivity(), PosterMAKERActivity.class);
                                        intent.putExtra("ratio", ratio);
                                        intent.putExtra("loadUserFrame", true);
                                        intent.putExtra(Scopes.PROFILE, "http://fadootutorial.com/primeinfotech/sizebg/" + ratio);
                                        intent.putExtra("hex", "");
                                        intent.putExtra("pos", String.valueOf(i));
                                        startActivity(intent);
                                    }

                                } else {
                                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                        showSettingsDialog();
                                    }
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).withErrorListener(new PermissionRequestErrorListener() {
                            public void onError(DexterError dexterError) {
                                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        }).onSameThread().check();
                    } else {
                        Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                                if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                    if (i == 0) {
                                        if (isActive) {
                                            addTextEditorDialog(ratio);
                                        } else {
                                            startActivity(new Intent(getActivity(), SubscriptionActivity.class));
                                        }
                                    } else {
                                        Intent intent = new Intent(getActivity(), PosterMAKERActivity.class);
                                        intent.putExtra("ratio", ratio);
                                        intent.putExtra("loadUserFrame", true);
                                        intent.putExtra(Scopes.PROFILE, "http://fadootutorial.com/primeinfotech/sizebg/" + ratio);
                                        intent.putExtra("hex", "");
                                        intent.putExtra("pos", String.valueOf(i));
                                        startActivity(intent);
                                    }

                                } else {
                                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                                        showSettingsDialog();
                                    }
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).withErrorListener(new PermissionRequestErrorListener() {
                            public void onError(DexterError dexterError) {
                                Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        }).onSameThread().check();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (itemList == null)
                return 0;
            return itemList.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardViewHome);
                image = itemView.findViewById(R.id.iv_image);
            }
        }
    }

    public void openSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
        startActivityForResult(intent, 101);
    }

    public void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
