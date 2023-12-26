package com.letsmake.atoz.design.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.Scopes;
import com.letsmake.atoz.design.R;

public class ChooseSizeActivity extends AppCompatActivity {

    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_choose_size);

        ivBack = findViewById(R.id.iv_back);
        //  AdHelper.showInterstitial(getApplicationContext());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void goEditorScreen(View view) {
        if (view.getTag() != null) {
            String[] split = view.getTag().toString().split("#");

            Intent intent = new Intent(getApplicationContext(), PosterMAKERActivity.class);
            intent.putExtra("ratio", split[1]);
            intent.putExtra("loadUserFrame", true);
            intent.putExtra(Scopes.PROFILE, "http://fadootutorial.com/primeinfotech/sizebg/" + split[0]);
            intent.putExtra("hex", "");
            startActivity(intent);
        }
    }
}
