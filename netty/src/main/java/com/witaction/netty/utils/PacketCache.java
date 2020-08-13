package com.witaction.netty.utils;

import java.util.Collections;
import java.util.HashSet;

import edu.dbke.socket.cp.Packet;

/**
 * 缓存未发送成功的Packet
 */

public class PacketCache {
    public static HashSet<Packet<?>> sPacketCache;

    public synchronized static void init() {
        if (sPacketCache == null) {
            sPacketCache = new HashSet<>();
            sPacketCache = (HashSet<Packet<?>>) Collections.synchronizedSet(sPacketCache);
        }
    }

    public synchronized static void putPacket(Packet packet) {
        init();
        sPacketCache.add(packet);
    }

    public static HashSet<Packet<?>> getCache() {
        init();
        return sPacketCache;
    }

    public static void clear() {
        if (!sPacketCache.isEmpty()) {
            sPacketCache.clear();
        }
    }

}
