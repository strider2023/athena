package com.touchmenotapps.athena.quiz.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.touchmenotapps.athena.R;
import com.touchmenotapps.athena.common.interfaces.QuizOptionSelectionListener;
import com.touchmenotapps.athena.quiz.bo.Quiz;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by i7 on 23-11-2017.
 */

public class QuizAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Quiz> quizList = new ArrayList<>();
    private QuizOptionSelectionListener callback;
    private boolean isCorrect = false;

    public QuizAdapter (Context context, QuizOptionSelectionListener callback) {
        this.context = context;
        this.callback = callback;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Quiz> data) {
        quizList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public Quiz getItem(int position) {
        return quizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.view_quiz_card, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.question.setText("Question number " + String.valueOf(position));
        holder.option1.setOnClickListener(this);
        holder.option2.setOnClickListener(this);
        holder.option3.setOnClickListener(this);
        holder.option4.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quiz_option_1:
                isCorrect = true;
                break;
            case R.id.quiz_option_2:
                break;
            case R.id.quiz_option_3:
                break;
            case R.id.quiz_option_4:
                break;
        }
        callback.onOptionSelected(isCorrect);
    }

    static class ViewHolder {
        @BindView(R.id.quiz_question)
        AppCompatTextView question;
        @BindView(R.id.quiz_option_1)
        AppCompatTextView option1;
        @BindView(R.id.quiz_option_2)
        AppCompatTextView option2;
        @BindView(R.id.quiz_option_3)
        AppCompatTextView option3;
        @BindView(R.id.quiz_option_4)
        AppCompatTextView option4;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
