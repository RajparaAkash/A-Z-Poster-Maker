package com.letsmake.atoz.design.fragments;

import static android.os.Build.VERSION.SDK_INT;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.activity.MyPosterActivity;
import com.letsmake.atoz.design.activity.PreviewActivity;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.custom_adapter.Poster_Work_Adapter;
import com.letsmake.atoz.design.imageloader.Glide_Image_Utils;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class fragment_work_poster extends Fragment {

    public Typeface typefaceTextBold;
    public Typeface typefaceTextNormal;

    public String TAG = "MyPosterActivity";
    public File[] listFile;

    public Context context;

    public int count = 0;

    public Poster_Work_Adapter imageAdapter;

    public GridView imagegrid;

    private TextView no_image;
    private AppPreferenceClass appPreferenceClass;

    public RelativeLayout rel_text;

    public int screenWidth;

    public int spostion;
    private TextView tvTitle;

    RecyclerView rvForTemplateList;
    HomeCardAdapter homeCardAdapter;

    public SweetAlertDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_work_poster, container, false);

        this.typefaceTextBold = Typeface.createFromAsset(getActivity().getAssets(), "font/Montserrat-SemiBold.ttf");
        this.typefaceTextNormal = Typeface.createFromAsset(getActivity().getAssets(), "font/Montserrat-Medium.ttf");

        // AdjustFontBold((ViewGroup) view.findViewById(16908290));
        this.appPreferenceClass = new AppPreferenceClass(getActivity());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels - Glide_Image_Utils.convertDpToPx(getActivity(), 10.0f);
        this.no_image = view.findViewById(R.id.no_image);
        this.rel_text = view.findViewById(R.id.rel_text);

      //  AdHelper.showInterstitial(getActivity());

        tvTitle = view.findViewById(R.id.tvTitle);
        Typeface custom_title = Typeface.createFromAsset(getActivity().getAssets(), "font/cabin.ttf");
        tvTitle.setTypeface(custom_title);

        this.no_image.setTypeface(setBoldFont());
        this.imagegrid = view.findViewById(R.id.gridView);

        this.imagegrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                spostion = i;
                Intent intent = new Intent(getActivity(), PreviewActivity.class);
                intent.putExtra("uri", listFile[i].getAbsolutePath());
                intent.putExtra("way", "Gallery");
                startActivity(intent);
            }
        });

        rvForTemplateList = view.findViewById(R.id.rvForTemplateList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvForTemplateList.setLayoutManager(layoutManager);

        homeCardAdapter = new HomeCardAdapter();

        return view;
    }

    public void setupProgress() {
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#D81B60"));
        pDialog.setTitleText("Getting...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ViewHolder> {

        HomeCardAdapter() {
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_card_share_work_templates, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
            viewHolder.mThumbnail.setId(i);

            Glide.with(getActivity()).load(listFile[i]).thumbnail(0.1f).apply(new RequestOptions().dontAnimate()).into(viewHolder.mThumbnail);

            PushDownAnim.setPushDownAnimTo(viewHolder.mThumbnail);
            viewHolder.mThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toSocialMediashare(listFile[i]);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (listFile == null)
                return 0;
            return listFile.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView mThumbnail;
            ProgressBar progressBar;
            ImageView imgDeletePoster;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                cardView = itemView.findViewById(R.id.cardViewHome);
                mThumbnail = itemView.findViewById(R.id.iv_image);
                imgDeletePoster = itemView.findViewById(R.id.iVDelete);
                progressBar = itemView.findViewById(R.id.progressBar1);
            }
        }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestStoragePermission();
    }

    public boolean permission() {
/*
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
*/

        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == 2000) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    Toast.makeText(getActivity(),"Permission allowed",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),"Please allow permission",Toast.LENGTH_SHORT).show();
                }
            }
        }
*/
    }

    private void requestStoragePermission() {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        //  getImageAndView();
                        new LordDataOperationAsync().execute(new String[]{""});

                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        showSettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(getActivity(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(getActivity()).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        //  getImageAndView();
                        new LordDataOperationAsync().execute(new String[]{""});

                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        showSettingsDialog();
                    }
                }

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


    public void openSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
        startActivityForResult(intent, 101);
    }

    public void setMyFontNormal(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setMyFontNormal((ViewGroup) childAt);
            } else if (childAt instanceof TextView) {
                ((TextView) childAt).setTypeface(this.typefaceTextNormal);
            } else if (childAt instanceof Button) {
                ((Button) childAt).setTypeface(this.typefaceTextNormal);
            } else if (childAt instanceof EditText) {
                ((EditText) childAt).setTypeface(this.typefaceTextNormal);
            }
        }
    }

    public Typeface setBoldFont() {
        return this.typefaceTextBold;
    }

    public Typeface setNormalFont() {
        return this.typefaceTextNormal;
    }

    public class LordDataOperationAsync extends AsyncTask<String, Void, String> {
        @Override
        public void onPreExecute() {

        }

        @Override
        public String doInBackground(String... strArr) {

          //  setupProgress();

            try {
                getFromSdcard();
                context = getActivity();
                if (listFile != null) {

                   // Thread.sleep(1000);
                }
            } catch (Exception e) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("run: ");
                stringBuilder.append(e);
                Log.e(str, stringBuilder.toString());
            }
            return "yes";
        }

        @Override
        public void onPostExecute(String str) {

/*
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
*/

            rvForTemplateList.setAdapter(homeCardAdapter);

            //  imagegrid.setAdapter(imageAdapter);
            if (count == 0) {
                rel_text.setVisibility(View.VISIBLE);
            } else {
                rel_text.setVisibility(View.GONE);
            }
        }
    }


    public void showOptionsDialog(final int i) {
        final Dialog dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
        dialog.setContentView(R.layout.layout_delete_dialog);
        dialog.setCancelable(false);
        TextView textView = dialog.findViewById(R.id.txtDescription);
        Button button = dialog.findViewById(R.id.btnDelete);
        Button button2 = dialog.findViewById(R.id.btnCancel);
        ((TextView) dialog.findViewById(R.id.txtTitle)).setTypeface(setBoldFont());
        textView.setTypeface(setNormalFont());
        button.setTypeface(setBoldFont());
        button2.setTypeface(setBoldFont());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (deleteFile(Uri.parse(MyPosterActivity.listFile[i].getAbsolutePath()))) {
                    listFile = null;
//                    getImageAndView();

                    new LordDataOperationAsync().execute(new String[]{""});

                    dialog.dismiss();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean deleteFile(Uri uri) {
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
                    z = deleteFile(Uri.parse(file.getName()));
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getActivity().getPackageName());
            stringBuilder.append(".provider");
            getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", FileProvider.getUriForFile(getActivity(), stringBuilder.toString(), file)));
        } catch (Exception unused) {
            Log.e(TAG, "deleteTempFile: ");
        }
        return z;
    }

    private void getFromSdcard() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
        stringBuilder.append("/Poster Design");
        File file = new File(stringBuilder.toString());
        if (file.isDirectory()) {
            listFile = file.listFiles();
            File[] fileArr = listFile;
            this.count = fileArr.length;
            Arrays.sort(fileArr, new Comparator<File>() {
                public int compare(File file, File file2) {
                    if (SDK_INT >= 19) {
                        return (file2.lastModified() > file.lastModified() ? 1 : (file2.lastModified() == file.lastModified() ? 0 : -1));
                    }
                    return (file2.lastModified() > file.lastModified() ? 1 : (file2.lastModified() == file.lastModified() ? 0 : -1));
                }
            });
        }
    }


}
