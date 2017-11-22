package com.touchmenotapps.athena.imageRecognition.threads;

import android.content.Context;

import com.touchmenotapps.athena.common.enums.ServerEvents;
import com.touchmenotapps.athena.common.interfaces.ServerResponseListener;
import com.touchmenotapps.athena.framework.AppTask;

/**
 * Created by i7 on 22-11-2017.
 */

public class ImageRecognitionTask extends AppTask {

    public ImageRecognitionTask(int id, Context context, ServerResponseListener serverResponseListener) {
        super(id, context, serverResponseListener);
    }

    @Override
    protected ServerEvents doInBackground(Object... objects) {
        return null;
    }

    @Override
    protected void onPostExecute(ServerEvents serverEvents) {
        super.onPostExecute(serverEvents);
        switch (serverEvents) {
            case SUCCESS:
                break;
            case FALIURE:
                break;
            case NO_NETWORK:
                break;
        }
    }
}
