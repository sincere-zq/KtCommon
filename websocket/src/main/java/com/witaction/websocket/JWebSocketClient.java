package com.witaction.websocket;


import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public abstract class JWebSocketClient extends WebSocketClient {
    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i("WebSocketClient", "websocket连接成功");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.i("WebSocketClient", "websocket连接断开");
    }

    @Override
    public void onError(Exception ex) {
        Log.i("WebSocketClient", "websocket连接异常");
    }
}
