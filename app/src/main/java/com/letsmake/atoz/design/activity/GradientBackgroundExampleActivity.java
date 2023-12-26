//package com.letsmake.atoz.design.activity;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Typeface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.Settings;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.content.ContextCompat;
//
//import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
//import com.codemybrainsout.onboarder.AhoyOnboarderCard;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;
//
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.DexterError;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.PermissionRequestErrorListener;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//import com.letsmake.atoz.design.R;
//import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
//import com.letsmake.atoz.design.editor_intelligence.AppConstants;
//import com.letsmake.atoz.design.receiver.NetworkConnectivityReceiver;
//
//import static android.os.Build.VERSION.SDK_INT;
//
//public class GradientBackgroundExampleActivity extends AhoyOnboarderActivity {
//
//    AppPreferenceClass appPreferenceClass;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        appPreferenceClass = new AppPreferenceClass(this);
//
//        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Choose Templates", "10000+ Professional design templates", R.drawable.intro_selection);
//        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Customize It", "Easy way to design your poster, banner, flyer, story, logo & invitation cards", R.drawable.intro_design);
//        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Share With Friends", "Expand your business globally with designed posters", R.drawable.intro_share);
//
//        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
//        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
//        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
//
//        List<AhoyOnboarderCard> pages = new ArrayList<>();
//
//        pages.add(ahoyOnboarderCard1);
//        pages.add(ahoyOnboarderCard2);
//        pages.add(ahoyOnboarderCard3);
//
//        for (AhoyOnboarderCard page : pages) {
//            page.setTitleColor(R.color.white);
//            page.setDescriptionColor(R.color.grey_200);
//        }
//
//        setFinishButtonTitle("Finish");
//        showNavigationControls(true);
//        setGradientBackground();
//
//        //set the button style you created
//        setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
//
//        Typeface face = Typeface.createFromAsset(getAssets(), "font/Roboto Light.ttf");
//        setFont(face);
//
//        setOnboardPages(pages);
//    }
//
//    public boolean permission() {
///*
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            return Environment.isExternalStorageManager();
//        }
//*/
//
//        return false;
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onFinishButtonPressed() {
//        /* Changed 26/05/2023 */
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//            Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
//                @Override
//                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                        //  MainActivity.this.makeStickerDir();
//                        if (NetworkConnectivityReceiver.isConnected()) {
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            appPreferenceClass.putInt(AppConstants.isFirstTimeINTRO, 1);
//                            finish();
//                        } else {
//                            networkError();
//                        }
//
//                    } else {
//                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
//                            showSettingsDialog();
//                        }
//                    }
//                }
//
//                @Override
//                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//                    permissionToken.continuePermissionRequest();
//                }
//            }).withErrorListener(new PermissionRequestErrorListener() {
//                public void onError(DexterError dexterError) {
//                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
//                }
//            }).onSameThread().check();
//        } else {
//            Dexter.withActivity(this).withPermissions("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").withListener(new MultiplePermissionsListener() {
//                @Override
//                public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
//                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                        //  MainActivity.this.makeStickerDir();
//                        if (NetworkConnectivityReceiver.isConnected()) {
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            appPreferenceClass.putInt(AppConstants.isFirstTimeINTRO, 1);
//                            finish();
//                        } else {
//                            networkError();
//                        }
//
//                    } else {
//                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
//                            showSettingsDialog();
//                        }
//                    }
//                }
//
//                @Override
//                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
//                    permissionToken.continuePermissionRequest();
//                }
//            }).withErrorListener(new PermissionRequestErrorListener() {
//                public void onError(DexterError dexterError) {
//                    Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
//                }
//            }).onSameThread().check();
//        }
//    }
//
//    public void showSettingsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Need Permissions");
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
//        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                openSettings();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        builder.show();
//    }
//
//    public void openSettings() {
//        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
//        intent.setData(Uri.fromParts("package", getPackageName(), null));
//        startActivityForResult(intent, 101);
//    }
//
//    public void networkError() {
//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("No Internet connected?").setContentText("make sure your internet connection is working.").setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismissWithAnimation();
//            }
//        }).show();
//    }
//}
