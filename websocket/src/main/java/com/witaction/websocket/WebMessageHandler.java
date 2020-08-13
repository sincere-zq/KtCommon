package com.witaction.websocket;

/**
 * 消息处理
 */
public interface WebMessageHandler{
    void handleMessage(String message);
    void handleMessage(int protocol, String message);
    void handleIdentifed(int protocol, String message);
    void handleAccess(int protocol, String message);
}
