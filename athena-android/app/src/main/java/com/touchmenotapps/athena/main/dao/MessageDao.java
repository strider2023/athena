package com.touchmenotapps.athena.main.dao;

import android.content.Context;
import android.graphics.Bitmap;

import com.touchmenotapps.athena.common.BaseDAO;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Created by i7 on 22-11-2017.
 */

public class MessageDao extends BaseDAO {

    private String message;
    private String url;
    private Bitmap image;
    private boolean isUserInput;

    public MessageDao(Context context) {
        super(context);
    }

    public MessageDao(Context context, String message, boolean isUserInput) {
        super(context);
        this.message = message;
        this.isUserInput = isUserInput;
    }

    @Override
    protected void parse(JSONParser jsonParser, JSONObject jsonObject) {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUserInput() {
        return isUserInput;
    }

    public void setUserInput(boolean userInput) {
        isUserInput = userInput;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
