package com.letsmake.atoz.design.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.ads.InterstitialAds;
import com.letsmake.atoz.design.app_utils.AppConfig;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.custom_adapter.Poster_Design_Adapter;
import com.letsmake.atoz.design.handler.DB_Handler;
import com.letsmake.atoz.design.handler.Template_InfoData;
import com.letsmake.atoz.design.imageloader.Glide_Image_Utils;

public class MyDesignActivity extends ShapeActivity {

    public ArrayList<Template_InfoData> templateList = new ArrayList();

    public Poster_Design_Adapter posterDesignAdapter;
    public GridView gridView;
    private RelativeLayout imagBack;
    LordDataAsync loadDataAsync = null;
    ProgressBar progress_bar;
    public int spoisiton, heightItemGrid = 50, widthItemGrid = 50;
    private TextView txtTitle, txt_dialog;

    String catName = "MY_TEMP";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        setContentView((int) R.layout.new_activity_my_design);

        AppConfig.storeInt("flow", 1, this);
        this.appPreferenceClass = new AppPreferenceClass(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.widthItemGrid = ((int) ((float) (displayMetrics.widthPixels - Glide_Image_Utils.convertDpToPx(this, 10.0f)))) / 2;
        this.heightItemGrid = ((int) ((float) (displayMetrics.heightPixels - Glide_Image_Utils.convertDpToPx(this, 10.0f)))) / 2;

        this.txtTitle = findViewById(R.id.txtTitle);
        this.txtTitle.setTypeface(adjustFontBold());
        this.imagBack = findViewById(R.id.btn_back);
        this.imagBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.gridView = findViewById(R.id.gridview);
        this.progress_bar = findViewById(R.id.progress_bar);
        this.progress_bar.setVisibility(View.GONE);
        this.txt_dialog = findViewById(R.id.txt_dialog);
        requestIOStoragePermission();

        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MyDesignActivity.this.spoisiton = i;
                new InterstitialAds().Show_Ads(MyDesignActivity.this, new InterstitialAds.AdCloseListener() {
                    @Override
                    public void onAdClosed() {
                        Intent intent = new Intent(MyDesignActivity.this, PosterMAKERActivity.class);
                        intent.putExtra("position", MyDesignActivity.this.spoisiton);
                        intent.putExtra("loadUserFrame", false);
                        intent.putExtra("Temp_Type", MyDesignActivity.this.catName);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public class LordDataAsync extends AsyncTask<String, Void, String> {

        @Override
        public void onPreExecute() {
            MyDesignActivity.this.progress_bar.setVisibility(View.VISIBLE);
        }

        @Override
        public String doInBackground(String... strArr) {
            try {
                MyDesignActivity.this.templateList.clear();
                DB_Handler dbHandler = DB_Handler.getDatabaseHandler(MyDesignActivity.this);
                if (MyDesignActivity.this.catName.equals("MY_TEMP")) {
                    MyDesignActivity.this.templateList = dbHandler.find_Template_List_Des("USER");
                }
                dbHandler.close();
            } catch (NullPointerException unused) {
            }
            return "yes";
        }

        @Override
        public void onPostExecute(String str) {
            try {
                MyDesignActivity.this.progress_bar.setVisibility(View.GONE);
                if (MyDesignActivity.this.templateList.size() != 0) {
                    MyDesignActivity.this.posterDesignAdapter = new Poster_Design_Adapter(MyDesignActivity.this, MyDesignActivity.this.templateList, MyDesignActivity.this.catName, MyDesignActivity.this.widthItemGrid);
                    MyDesignActivity.this.gridView.setAdapter(MyDesignActivity.this.posterDesignAdapter);
                }
                if (MyDesignActivity.this.catName.equals("MY_TEMP")) {
                    if (MyDesignActivity.this.templateList.size() == 0) {
                        MyDesignActivity.this.txt_dialog.setText(MyDesignActivity.this.getResources().getString(R.string.NoDesigns));
                    } else if (MyDesignActivity.this.templateList.size() <= 4) {
                        MyDesignActivity.this.txt_dialog.setText(MyDesignActivity.this.getResources().getString(R.string.DesignOptionsInstruction));
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void requestIOStoragePermission() {
        /* Changed 26/05/2023 */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        MyDesignActivity.this.makeStickerDir();
                        MyDesignActivity myDesignActivity = MyDesignActivity.this;
                        myDesignActivity.loadDataAsync = new LordDataAsync();
                        MyDesignActivity.this.loadDataAsync.execute(new String[]{""});
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        MyDesignActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(MyDesignActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        } else {
            Dexter.withActivity(this).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        MyDesignActivity.this.makeStickerDir();
                        MyDesignActivity myDesignActivity = MyDesignActivity.this;
                        myDesignActivity.loadDataAsync = new LordDataAsync();
                        MyDesignActivity.this.loadDataAsync.execute(new String[]{""});
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                        MyDesignActivity.this.displaySettingsDialog();
                    }
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    permissionToken.continuePermissionRequest();
                }
            }).withErrorListener(new PermissionRequestErrorListener() {
                public void onError(DexterError dexterError) {
                    Toast.makeText(MyDesignActivity.this.getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                }
            }).onSameThread().check();
        }
    }

    public void displaySettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                MyDesignActivity.this.viewSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void viewSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivityForResult(intent, 101);
    }
}
