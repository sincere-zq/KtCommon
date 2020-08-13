package com.witaction.websocket;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 消息处理
 */
public class WebMessageHandlerImpl implements WebMessageHandler {

    @Override
    public void handleMessage(String message) {
        try {
            JSONObject object = new JSONObject(message);
            Log.e("WebMessage:", message);
            handleMessage(object.getInt("Protocol"), message);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("WebMessage:EXCEPTION", "");
        }
    }

    @Override
    public void handleMessage(int protocol, String message) {
        switch (protocol) {
            //人员识别
            case ProtocolType.PROTOCOL_PERSON_IDENTIFY:
                handleIdentifed(protocol, message);
                break;
            //人员出入
            case ProtocolType.PROTOCOL_PERSON_ACCESS:
                handleAccess(protocol, message);
                break;
            default:
        }
    }

    @Override
    public void handleIdentifed(int protocol, String message) {
    }

    @Override
    public void handleAccess(int protocol, String message) {
    }

}
