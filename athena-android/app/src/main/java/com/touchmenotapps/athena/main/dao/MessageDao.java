package com.touchmenotapps.athena.main.dao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.touchmenotapps.athena.common.BaseDAO;
import com.touchmenotapps.athena.common.constants.AppConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.ByteArrayOutputStream;

/**
 * Created by i7 on 22-11-2017.
 */

public class MessageDao extends BaseDAO {

    private String message;
    private String url;
    private Bitmap image;
    private String imageUrl;
    private String question;
    private boolean isUserInput;
    private boolean hasSimulation;

    private JSONParser jsonParser;

    public MessageDao(Context context) {
        super(context);
        jsonParser = new JSONParser();
    }

    public MessageDao(Context context, String message, boolean isUserInput) {
        super(context);
        jsonParser = new JSONParser();
        this.message = message;
        this.isUserInput = isUserInput;
    }

    @Override
    public void parse(JSONParser jsonParser, JSONObject jsonObject) {
        if (jsonObject.containsKey("message")) {
            setMessage(jsonObject.get("message").toString());
            Log.i(AppConstants.APP_TAG, message);
        }
        if (jsonObject.containsKey("url")) {
            try {
                setUrl(jsonObject.get("url").toString());
            } catch (Exception e) {

            }
        }
        if (jsonObject.containsKey("quiz")) {

        }
        if (jsonObject.containsKey("hasSimulation")) {
            setHasSimulation((Boolean) jsonObject.get("hasSimulation"));
        }
        setUserInput(false);
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isHasSimulation() {
        return hasSimulation;
    }

    public void setHasSimulation(boolean hasSimulation) {
        this.hasSimulation = hasSimulation;
    }

    private String getEncodedImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

    public JSONObject getJSONPostRequest() {
        JSONObject request = new JSONObject();
        try {
            request.put("message", this.message);
            if(image != null) {
                request.put("image", getEncodedImage());
            }
            if(question != null) {
                request.put("question", this.question);
            }
        } catch (Exception e) {
            Log.e(AppConstants.APP_TAG, e.getMessage());
        }
        return request;
    }
}
