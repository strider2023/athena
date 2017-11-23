package com.touchmenotapps.athena.main.threads;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.touchmenotapps.athena.common.constants.AppConstants;
import com.touchmenotapps.athena.common.constants.URLConstants;
import com.touchmenotapps.athena.common.enums.ServerEvents;
import com.touchmenotapps.athena.common.interfaces.ServerResponseListener;
import com.touchmenotapps.athena.framework.AppTask;
import com.touchmenotapps.athena.main.dao.MessageDao;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by i7 on 22-11-2017.
 */

public class SendUserMessageTask extends AppTask {

    private String decodedString;
    private String errorMessage;
    private MessageDao message;

    public SendUserMessageTask(int id, Context context, ServerResponseListener serverResponseListener) {
        super(id, context, serverResponseListener);
    }

    @Override
    protected ServerEvents doInBackground(Object... objects) {
        if(getNetworkUtils().isNetworkAvailable()) {
            try {
                JSONObject dato = (JSONObject) objects[0];
                Log.i(AppConstants.APP_TAG, dato.toJSONString());
                return getServerResponse(dato);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(AppConstants.APP_TAG, e.getMessage());
                errorMessage = "Oops something went wrong!";
                return ServerEvents.FAILURE;
            }
        } else {
            errorMessage = "Oops! Unable to connect to the internet.";
            return ServerEvents.NO_NETWORK;
        }
    }

    @Override
    protected void onPostExecute(ServerEvents serverEvents) {
        super.onPostExecute(serverEvents);
        switch (serverEvents) {
            case SUCCESS:
                getServerResponseListener().onSuccess(getId(), message);
                break;
            case FAILURE:
                getServerResponseListener().onFaliure(ServerEvents.FAILURE, errorMessage);
                break;
            case NO_NETWORK:
                getServerResponseListener().onFaliure(ServerEvents.NO_NETWORK, message);
                break;
        }
    }

    private ServerEvents getServerResponse(JSONObject object) throws Exception{
        HttpURLConnection httppost = getNetworkUtils().getHttpURLConInstance(
                URLConstants.AUTH_URL, false);
        DataOutputStream out = new DataOutputStream(httppost.getOutputStream());
        out.writeBytes(object.toString());
        out.flush();
        out.close();
        StringBuilder sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                httppost.getInputStream()));
        while ((decodedString = in.readLine()) != null)
            sb.append(decodedString);
        in.close();
        JSONObject response = (JSONObject) getParser().parse(sb.toString());
        Log.i(AppConstants.APP_TAG, sb.toString());
        message = new MessageDao(getContext());
        message.parse(getParser(), response);
        return ServerEvents.SUCCESS;
    }
}
