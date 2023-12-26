package com.letsmake.atoz.design.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat.IntentBuilder;
import androidx.core.content.FileProvider;
import androidx.print.PrintHelper;

import com.android.billingclient.api.Purchase;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import com.letsmake.atoz.design.ads.NativeAds;
import com.letsmake.atoz.design.billing.BillingUpdatesListener;
import com.letsmake.atoz.design.billing.SubscriptionsUtil;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import com.letsmake.atoz.design.R;
import com.letsmake.atoz.design.app_utils.AppPreferenceClass;
import com.letsmake.atoz.design.editor_intelligence.AppConstants;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class PreviewActivity extends AppCompatActivity implements OnClickListener, RatingDialogListener, BillingUpdatesListener {

    private static final String TAG = "PreviewActivity";
    private static final String PLAY_CONSTANT = "http://play.google.com/store/apps/details?id=";
    private static final String MARKET_CONSTANT = "market://details?id=";

    private int REQUEST_FOR_GOOGLE_PLUS = 0;

    public AppPreferenceClass appPreference;
    private RelativeLayout btnBack, removeWaterMark;
    private ImageView iVPrint, iVHome, imageView, btnSharewMessanger, btnShareMoreImage, btnShareTwitter, btnShareWhatsapp, btnShareIntagram, btnShareHike, btnShareGooglePlus, btnMoreApp, btnShareFacebook;
    private TextView txtToolbar;
    public Typeface typefaceTextNormal, typefaceTextBold;

    private String oldpath;
    public Uri phototUri = null;
    public File pictureFile;
    ViewGroup root;

    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(@Nullable Bundle bundle) {
        getWindow().setFlags(1024, 1024);
        super.onCreate(bundle);
        setContentView(R.layout.new_activity_share_image);

        this.root = findViewById(R.id.main);
        this.appPreference = new AppPreferenceClass(this);

        this.typefaceTextBold = Typeface.createFromAsset(getAssets(), "font/Montserrat-SemiBold.ttf");
        this.typefaceTextNormal = Typeface.createFromAsset(getAssets(), "font/Montserrat-Medium.ttf");

        findViewByID();

        if (getIntent().getExtras().getString("way").equals("Gallery")) {
            this.removeWaterMark.setVisibility(View.GONE);
        }

        initialization();
        exportBitmap();

        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.congratulations_saved_rating);

            if (this.appPreference.getInt(AppConstants.isRated, 0) == 0) {
                mediaPlayer.start();
                customRateDialog();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

        if(mediaPlayer != null)
            mediaPlayer.pause();

        
        super.onDestroy();
    }

    public void onNegativeButtonClicked() {
    }


    public void onNeutralButtonClicked() {
    }

    public Typeface applyBoldFont() {
        return this.typefaceTextBold;
    }

    private void findViewByID() {
        btnBack = findViewById(R.id.btn_back);
        txtToolbar = findViewById(R.id.txt_toolbar);

        Typeface custom_title = Typeface.createFromAsset(getAssets(), "font/cabin.ttf");
        txtToolbar.setTypeface(custom_title);

        iVHome = findViewById(R.id.iVHome);
        iVPrint = findViewById(R.id.iVPrint);

        btnShareFacebook = findViewById(R.id.btnShareFacebook);
        btnShareIntagram = findViewById(R.id.btnShareIntagram);
        btnShareWhatsapp = findViewById(R.id.btnShareWhatsapp);
        btnShareGooglePlus = findViewById(R.id.btnShareGooglePlus);
        btnSharewMessanger = findViewById(R.id.btnSharewMessanger);
        btnShareTwitter = findViewById(R.id.btnShareTwitter);
        btnShareHike = findViewById(R.id.btnShareHike);
        btnShareMoreImage = findViewById(R.id.btnShareMoreImage);

        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareFacebook);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareIntagram);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareWhatsapp);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareGooglePlus);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnSharewMessanger);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareTwitter);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareHike);
        YoYo.with(Techniques.Shake).duration(1200).repeat(2).playOn(btnShareMoreImage);

        btnBack.setOnClickListener(this);
        iVPrint.setOnClickListener(this);
        iVHome.setOnClickListener(this);
        btnShareFacebook.setOnClickListener(this);
        btnShareIntagram.setOnClickListener(this);
        btnShareWhatsapp.setOnClickListener(this);
        btnShareGooglePlus.setOnClickListener(this);
        btnSharewMessanger.setOnClickListener(this);
        btnShareTwitter.setOnClickListener(this);
        btnShareHike.setOnClickListener(this);
        btnShareMoreImage.setOnClickListener(this);
        txtToolbar.setTypeface(applyBoldFont());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iVPrint) {
           /* StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://play.google.com/store/apps/details?id=");
            stringBuilder.append(getPackageName());
            moreAppClick(stringBuilder.toString());
     */
            doPhotoPrint();
        } else if (id == R.id.btn_back) {
            finish();
        } else {
            switch (id) {
                case R.id.btnShareFacebook /*2131296398*/:
                    shareWithFacebook(this.pictureFile.getPath());
                    return;
                case R.id.btnShareGooglePlus /*2131296399*/:
                    shareWithGooglePlus(this.pictureFile.getPath());
                    return;
                case R.id.btnShareHike /*2131296400*/:
                    shareWithHike(this.pictureFile.getPath());
                    return;
                case R.id.btnShareIntagram /*2131296401*/:
                    sendOnInstagram(this.pictureFile.getPath());
                    return;
                case R.id.iVHome /*2131296402*/:
                    //   toSocialMediashare();
                    Intent frameCategory = new Intent(PreviewActivity.this, MainActivity.class);
                    frameCategory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    frameCategory.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(frameCategory);
                    finish();
                    return;
                case R.id.btnShareMoreImage /*2131296403*/:
                    shareImageALL(this.pictureFile.getPath());
                    return;
                case R.id.btnShareTwitter /*2131296404*/:
                    shareWithTwitter(this.pictureFile.getPath());
                    return;
                case R.id.btnShareWhatsapp /*2131296405*/:
                    shareToWhatsapp(this.pictureFile.getPath());
                    return;
                case R.id.btnSharewMessanger /*2131296406*/:
                    shareOnMessanger(this.pictureFile.getPath());
                    return;
                default:
                    toSocialMediashare();
            }
        }
    }

    public void initialization() {
        this.imageView = findViewById(R.id.image);
        PushDownAnim.setPushDownAnimTo(imageView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.oldpath = extras.getString("uri");
            if (this.oldpath.equals("")) {
                Toast.makeText(this, getResources().getString(R.string.picUpImg), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                this.phototUri = Uri.parse(this.oldpath);
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.picUpImg), Toast.LENGTH_SHORT).show();
            finish();
        }
        try {
            this.pictureFile = new File(this.phototUri.getPath());
            this.imageView.setImageBitmap(BitmapFactory.decodeFile(this.pictureFile.getAbsolutePath(), new Options()));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                this.imageView.setImageURI(this.phototUri);
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        if (this.appPreference.getBoolean("removeWatermark", false)) {
            this.removeWaterMark.setVisibility(View.GONE);
        }
    }


    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }


    public void moreAppClick(String str) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (ActivityNotFoundException unused) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://play.google.com/store/search?q=pub:");
            stringBuilder.append(getResources().getString(R.string.app_name));
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
        }
    }

    public void toSocialMediashare() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getCacheDir());
            stringBuilder.append("/share_icon.png");

            Uri parse = Uri.parse(stringBuilder.toString());
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("*/*");
            intent.putExtra("android.intent.extra.STREAM", parse);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(getString(R.string.share_text));
            stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
            stringBuilder2.append(getPackageName());
            intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doPhotoPrint() {
        PrintHelper printHelper = new PrintHelper(PreviewActivity.this);
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        if (pictureFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath());
            printHelper.printBitmap("Print Document", myBitmap);
        }
    }

    public void customRateDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Rate ME")
                .setNegativeButtonText("LATER")
                //   .setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList(new String[]{"Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!"}))
                .setDefaultRating(5)
                .setTitle("Do You Love App?")
                .setDescription("Please rate us with stars and must give feedback")
                .setCommentInputEnabled(false).setStarColor(R.color.yellow)
                .setNoteDescriptionTextColor(R.color.text_color)
                .setTitleTextColor(R.color.text_color)
                .setDescriptionTextColor(R.color.text_color)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.hintTextColor)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(this)
                .show();
    }

    private void rateAppOnPlay() {
        StringBuilder stringBuilder;
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append(MARKET_CONSTANT);
            stringBuilder.append(getApplicationContext().getPackageName());
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
        } catch (ActivityNotFoundException unused) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(PLAY_CONSTANT);
            stringBuilder.append(getApplicationContext().getPackageName());
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())));
        }
        appPreference.putInt(AppConstants.isRated, 1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void shareToWhatsapp(String str) {
        if (getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.whatsapp") != null) {
            try {
                Uri parse = Uri.parse(str);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("*/*");
                intent.setPackage("com.whatsapp");
                intent.putExtra("android.intent.extra.STREAM", parse);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(getString(R.string.share_text));
                stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                stringBuilder2.append(getPackageName());
                intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
                startActivity(Intent.createChooser(intent, "Share via"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareWithGooglePlus(String str) {
        if (getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.google.android.apps.plus") != null) {
            try {
                IntentBuilder type = IntentBuilder.from(this).setType("image/jpeg");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getApplicationContext().getPackageName());
                stringBuilder.append(".provider");
                startActivityForResult(type.setStream(FileProvider.getUriForFile(this, stringBuilder.toString(), new File(str))).getIntent().setPackage("com.google.android.apps.plus"), this.REQUEST_FOR_GOOGLE_PLUS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Google Plus not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareWithHike(String str) {
        if (getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.bsb.hike") != null) {
            try {
                Uri parse = Uri.parse(str);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("*/*");
                intent.setPackage("com.bsb.hike");
                intent.putExtra("android.intent.extra.STREAM", parse);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(getString(R.string.share_text));
                stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                stringBuilder2.append(getPackageName());
                intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
                startActivity(Intent.createChooser(intent, "Share via"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            Toast.makeText(this, "Hike not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareWithTwitter(String str) {
        try {
            if (getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.twitter.android") != null) {
                try {
                    Uri parse = Uri.parse(str);
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("*/*");
                    intent.setPackage("com.twitter.android");
                    intent.putExtra("android.intent.extra.STREAM", parse);
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(getString(R.string.share_text));
                    stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                    stringBuilder2.append(getPackageName());
                    intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
                    startActivity(Intent.createChooser(intent, "Share via"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "Twitter not installed", Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(getApplicationContext(), "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareWithFacebook(String str) {
        if (getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.facebook.katana") != null) {
            try {
                Uri parse = Uri.parse(str);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("*/*");
                intent.setPackage("com.facebook.katana");
                intent.putExtra("android.intent.extra.STREAM", parse);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(getString(R.string.share_text));
                stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                stringBuilder2.append(getPackageName());
                intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
                startActivity(Intent.createChooser(intent, "Share via"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(this, "Facebook not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnMessanger(String str) {
        if (getPackageManager().getLaunchIntentForPackage("com.facebook.orca") != null) {
            try {
                MediaScannerConnection.scanFile(getApplicationContext(), new String[]{str}, null, new OnScanCompletedListener() {
                    public void onScanCompleted(String str, Uri uri) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("image/gif");
                        intent.setPackage("com.facebook.orca");
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(getResources().getString(R.string.share_text));
                        stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                        stringBuilder2.append(getPackageName());

                        intent.putExtra(Intent.EXTRA_TEXT, stringBuilder2.toString());
                        intent.putExtra("android.intent.extra.STREAM", uri);
                        intent.addFlags(524288);
                        PreviewActivity.this.startActivity(Intent.createChooser(intent, "Test"));
                    }
                });
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(getApplicationContext(), "You don't seem to have twitter installed on this device", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Facebook Messanger not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendOnInstagram(String str) {
        if (getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.instagram.android") != null) {
            try {
                Uri parse = Uri.parse(str);
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("*/*");
                intent.setPackage("com.instagram.android");
                intent.putExtra("android.intent.extra.STREAM", parse);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(getString(R.string.share_text));
                stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
                stringBuilder2.append(getPackageName());
                intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
                startActivity(Intent.createChooser(intent, "Share via"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(this, "Instagram not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareImageALL(String str) {
        try {
            Uri parse = Uri.parse(str);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("*/*");
            intent.putExtra("android.intent.extra.STREAM", parse);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(getString(R.string.share_text));
            stringBuilder2.append("\nhttps://play.google.com/store/apps/details?id=");
            stringBuilder2.append(getPackageName());
            intent.putExtra("android.intent.extra.TEXT", stringBuilder2.toString());
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exportBitmap() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.plzwait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    PreviewActivity.this.pictureFile = new File(PreviewActivity.this.phototUri.getPath());
                    try {
                        if (!PreviewActivity.this.pictureFile.exists()) {
                            PreviewActivity.this.pictureFile.createNewFile();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
                        PosterMAKERActivity.withoutWatermark.compress(CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        MediaScannerConnection.scanFile(PreviewActivity.this, new String[]{PreviewActivity.this.pictureFile.getAbsolutePath()}, null, new OnScanCompletedListener() {
                            public void onScanCompleted(String str, Uri uri) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Scanned ");
                                stringBuilder.append(str);
                                stringBuilder.append(":");
                                Log.i("ExternalStorage", stringBuilder.toString());
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("-> uri=");
                                stringBuilder2.append(uri);
                                Log.i("ExternalStorage", stringBuilder2.toString());
                            }
                        });
                        PreviewActivity previewActivity = PreviewActivity.this;
                        PreviewActivity previewActivity2 = PreviewActivity.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(PreviewActivity.this.getApplicationContext().getPackageName());
                        stringBuilder.append(".provider");
                        previewActivity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", FileProvider.getUriForFile(previewActivity2, stringBuilder.toString(), PreviewActivity.this.pictureFile)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Thread.sleep(1000);
                } catch (Exception unused) {
                }
                progressDialog.dismiss();
            }
        }).start();
        progressDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                Options options = new Options();
                options.inPreferredConfig = Config.ARGB_8888;
                options.inSampleSize = 2;
                PreviewActivity.this.imageView.setImageBitmap(BitmapFactory.decodeFile(PreviewActivity.this.pictureFile.getAbsolutePath(), options));
            }
        });
    }

    @Override
    public void onPositiveButtonClicked(int i, String str) {
        if (i > 3) {
            rateAppOnPlay();
        }
        this.appPreference.putInt(AppConstants.isRated, 1);
    }

    @Override
    public void onBillingClientSetupFinished() {

    }

    @Override
    public void onPurchasesUpdated(List<Purchase> purchaseList) {
        boolean isActive = SubscriptionsUtil.isSubscriptionActive(purchaseList);

        if (!isActive) {

            // Adaptive_Banner
            new NativeAds(this).Adaptive_Banner(findViewById(R.id.adaptive_banner));

        }
    }

    @Override
    public void onPurchaseVerified() {

    }
}
