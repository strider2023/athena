package com.touchmenotapps.athena.common.interfaces;


import com.touchmenotapps.athena.common.enums.ServerEvents;

public interface ServerResponseListener {

    void onSuccess(int threadId, Object object);

    void onFaliure(ServerEvents serverEvents, Object object);
}
