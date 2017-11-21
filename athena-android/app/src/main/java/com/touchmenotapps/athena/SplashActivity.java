package com.touchmenotapps.athena;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.touchmenotapps.athena.framework.AppPreferences;
import com.touchmenotapps.athena.framework.ocr.OcrActivity;
import com.touchmenotapps.athena.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.splash_app_icon)
    ImageView splashIcon;

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        appPreferences = new AppPreferences(this);

        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(splashIcon);
        Glide.with(this)
                .load(R.raw.logo_anim)
                .into(imageViewTarget);

        if(!appPreferences.isUserLoggedIn()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 3000);
        } else {
            //buttonContainer.setVisibility(View.VISIBLE);
        }
    }
}
