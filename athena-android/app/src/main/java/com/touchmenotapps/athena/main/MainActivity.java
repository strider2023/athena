package com.touchmenotapps.athena.main;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.touchmenotapps.athena.R;
import com.touchmenotapps.athena.framework.ocr.OcrActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chatList)
    RecyclerView imageView;
    @BindView(R.id.launchOptions)
    ImageButton imageButton;
    @BindView(R.id.options_view)
    CardView optionsView;
    @BindView(R.id.options_button_container)
    LinearLayout optionsButtons;

    Animation alphaAnimation;
    private float pixelDensity;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pixelDensity = getResources().getDisplayMetrics().density;
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
    }

    @OnClick(R.id.search_by_text_scan)
    public void launchOCRCapturer() {
        startActivity(new Intent(this, OcrActivity.class));
    }

    @OnClick(R.id.launchOptions)
    public void showOptionsView(View view) {
        /*
         MARGIN_RIGHT = 16;
         FAB_BUTTON_RADIUS = 28;
         */
        int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= ((28 * pixelDensity) + (16 * pixelDensity));
        int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());

        if (flag) {
            imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
            imageButton.setImageResource(R.drawable.ic_close_black_24dp);

            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams) optionsView.getLayoutParams();
            parameters.height = imageView.getHeight();
            optionsView.setLayoutParams(parameters);

            Animator anim = ViewAnimationUtils.createCircularReveal(optionsView, x, y, 0, hypotenuse);
            anim.setDuration(700);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) { }

                @Override
                public void onAnimationEnd(Animator animator) {
                    optionsButtons.setVisibility(View.VISIBLE);
                    optionsButtons.startAnimation(alphaAnimation);
                }

                @Override
                public void onAnimationCancel(Animator animator) { }

                @Override
                public void onAnimationRepeat(Animator animator) { }
            });

            optionsView.setVisibility(View.VISIBLE);
            anim.start();

            flag = false;
        } else {
            imageButton.setBackgroundResource(R.drawable.rounded_button);
            imageButton.setImageResource(R.drawable.ic_apps_black_24dp);

            Animator anim = ViewAnimationUtils.createCircularReveal(optionsView, x, y, hypotenuse, 0);
            anim.setDuration(400);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) { }

                @Override
                public void onAnimationEnd(Animator animator) {
                    optionsView.setVisibility(View.GONE);
                    optionsButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) { }

                @Override
                public void onAnimationRepeat(Animator animator) { }
            });
            anim.start();
            flag = true;
        }
    }
}
