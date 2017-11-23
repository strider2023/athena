package com.touchmenotapps.athena.quiz;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.touchmenotapps.athena.R;
import com.touchmenotapps.athena.common.interfaces.QuizOptionSelectionListener;
import com.touchmenotapps.athena.quiz.adapter.QuizAdapter;
import com.touchmenotapps.athena.quiz.bo.Quiz;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity implements QuizOptionSelectionListener {

    @BindView(R.id.quiz_card_stack)
    CardStackView cardStackView;

    private QuizAdapter quizAdapter;
    private List<Quiz> quizList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        quizList.add(new Quiz(this));
        quizList.add(new Quiz(this));
        quizList.add(new Quiz(this));
        quizList.add(new Quiz(this));
        quizList.add(new Quiz(this));

        quizAdapter = new QuizAdapter(this, this);
        cardStackView.setAdapter(quizAdapter);

        quizAdapter.setData(quizList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onOptionSelected(boolean isCorrect) {
        if(isCorrect) {
            swipeTop();
        } else {
            swipeLeft();
        }
    }

    public void swipeLeft() {
        /*List<TouristSpot> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }*/

        View target = cardStackView.getTopView();
        ValueAnimator rotation = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("rotation", -10f));
        rotation.setDuration(200);
        ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, 500f));
        translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotation, translateX, translateY);
        cardStackView.swipe(SwipeDirection.Left, set);
    }

    public void swipeTop() {
        /*List<TouristSpot> spots = extractRemainingTouristSpots();
        if (spots.isEmpty()) {
            return;
        }*/

        View target = cardStackView.getTopView();
        /*ValueAnimator translateX = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationX", 0f, -2000f));*/
        ValueAnimator translateY = ObjectAnimator.ofPropertyValuesHolder(
                target, PropertyValuesHolder.ofFloat("translationY", 0f, -2000f));
        //translateX.setStartDelay(100);
        translateY.setStartDelay(100);
        //translateX.setDuration(500);
        translateY.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(translateY);
        cardStackView.swipe(SwipeDirection.Left, set);
    }
}
