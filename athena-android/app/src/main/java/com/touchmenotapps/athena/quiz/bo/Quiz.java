package com.touchmenotapps.athena.quiz.bo;

import android.content.Context;

import com.touchmenotapps.athena.common.BaseDAO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by i7 on 23-11-2017.
 */

public class Quiz extends BaseDAO {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public Quiz(Context context) {
        super(context);
    }

    @Override
    public void parse(JSONParser jsonParser, JSONObject jsonObject) {

    }
}
