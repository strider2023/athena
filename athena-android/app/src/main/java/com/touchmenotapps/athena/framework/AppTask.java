package com.touchmenotapps.athena.framework;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.touchmenotapps.athena.common.enums.ServerEvents;
import com.touchmenotapps.athena.common.interfaces.ServerResponseListener;

import org.json.simple.parser.JSONParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by arindamnath on 10/02/16.
 */
public abstract class AppTask extends AsyncTask<Object, Void, ServerEvents> {

    private int id;
    private ServerResponseListener serverResponseListener;
    private NetworkUtils networkUtils;
    private AppPreferences appPreferences;
    private JSONParser parser;
    private ProgressDialog mProgress;
    private Context context;

    public AppTask(int id, Context context, ServerResponseListener serverResponseListener){
        this.id = id;
        this.context = context;
        this.serverResponseListener = serverResponseListener;
        this.networkUtils = new NetworkUtils(context);
        this.appPreferences = new AppPreferences(context);
        this.mProgress = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
        this.mProgress.setMessage("Loading...");
        this.mProgress.setCancelable(false);
        this.parser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress.show();
    }

    @Override
    protected void onPostExecute(ServerEvents serverEvents) {
        super.onPostExecute(serverEvents);
        mProgress.dismiss();
    }

    public int getId() {
        return id;
    }

    public ServerResponseListener getServerResponseListener() {
        return serverResponseListener;
    }

    public NetworkUtils getNetworkUtils() {
        return networkUtils;
    }

    public AppPreferences getAppPreferences() {
        return appPreferences;
    }

    public JSONParser getParser() {
        return parser;
    }

    public ProgressDialog getmProgress() {
        return mProgress;
    }

    public Context getContext() {
        return context;
    }

    private final String md5(final String s) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
