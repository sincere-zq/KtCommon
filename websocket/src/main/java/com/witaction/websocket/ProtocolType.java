package com.witaction.websocket;

/**
 * WebSocket协议号
 */

public class ProtocolType {
    @Desc(
            key = 11001,
            desc = "人员识别统计"
    )
    public static final short PROTOCOL_PERSON_IDENTIFY = 11001;
    @Desc(
            key = 11002,
            desc = "人员出入"
    )
    public static final short PROTOCOL_PERSON_ACCESS = 11002;
}