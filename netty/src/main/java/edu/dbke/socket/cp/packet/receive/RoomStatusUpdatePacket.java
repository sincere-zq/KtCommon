package edu.dbke.socket.cp.packet.receive;

import android.text.TextUtils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */

public class RoomStatusUpdatePacket extends Packet<RoomStatusUpdatePacket> {
    public String roomNumber;
    public String roomName;
    public short onlineSize;
    public short userMaxSize;
    public short onlineOnlookerSize;
    public short onlookerMaxSize;
    public boolean isPin;
    public boolean isLock;
    public boolean isClose;
    public byte isOnlooker;
    public Date beginTime;
    public Date endTime;
    public byte mtype;
    public String userPreList;
    public String onlookerPreList;
    public String presider;

    public void writeData() {
        ByteUtil.write256String(this.data, this.roomNumber);
        ByteUtil.write256String(this.data, this.roomName);
        this.data.putShort(this.onlineSize);
        this.data.putShort(this.userMaxSize);
        this.data.putShort(this.onlineOnlookerSize);
        this.data.putShort(this.onlookerMaxSize);
        ByteUtil.writeBoolean(this.data, this.isPin);
        ByteUtil.writeBoolean(this.data, this.isLock);
        ByteUtil.writeBoolean(this.data, this.isClose);
        this.data.put(this.isOnlooker);
        ByteUtil.writeDate(this.data, this.beginTime);
        ByteUtil.writeDate(this.data, this.endTime);
        this.data.put(this.mtype);
        ByteUtil.writeShortString(this.data, this.userPreList);
        ByteUtil.writeShortString(this.data, this.onlookerPreList);
        ByteUtil.write256String(this.data, this.presider);
    }

    public void readData() {
        this.roomNumber = ByteUtil.read256String(this.data);
        this.roomName = ByteUtil.read256String(this.data);
        this.onlineSize = this.data.getShort();
        this.userMaxSize = this.data.getShort();
        this.onlineOnlookerSize = this.data.getShort();
        this.onlookerMaxSize = this.data.getShort();
        this.isPin = ByteUtil.readBoolean(this.data);
        this.isLock = ByteUtil.readBoolean(this.data);
        this.isClose = ByteUtil.readBoolean(this.data);
        this.isOnlooker = this.data.get();
        this.beginTime = ByteUtil.readDate(this.data);
        this.endTime = ByteUtil.readDate(this.data);
        this.mtype = this.data.get();
        this.userPreList = ByteUtil.readShortString(this.data);
        this.onlookerPreList = ByteUtil.readShortString(this.data);
        this.presider = ByteUtil.read256String(this.data);
    }

    public RoomStatusUpdatePacket() {
        this.type = 1210;
    }

    public RoomStatusUpdatePacket(String roomNumber, String roomName, short onlineSize, short userMaxSize, short onlineOnlookerSize, short onlookerMaxSize, boolean isPin, boolean isLock, boolean isClose, byte isOnlooker) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.onlineSize = onlineSize;
        this.userMaxSize = userMaxSize;
        this.onlineOnlookerSize = onlineOnlookerSize;
        this.onlookerMaxSize = onlookerMaxSize;
        this.isPin = isPin;
        this.isLock = isLock;
        this.isClose = isClose;
        this.isOnlooker = isOnlooker;
        this.type = 1210;
    }

    @Override
    public String toString() {
        return "RoomStatusUpdatePacket{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomName='" + roomName + '\'' +
                ", onlineSize=" + onlineSize +
                ", userMaxSize=" + userMaxSize +
                ", onlineOnlookerSize=" + onlineOnlookerSize +
                ", onlookerMaxSize=" + onlookerMaxSize +
                ", isPin=" + isPin +
                ", isLock=" + isLock +
                ", isClose=" + isClose +
                ", isOnlooker=" + isOnlooker +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", mtype=" + mtype +
                ", userPreList='" + userPreList + '\'' +
                ", onlookerPreList='" + onlookerPreList + '\'' +
                ", presider='" + presider + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RoomStatusUpdatePacket)) {
            return false;
        }
        RoomStatusUpdatePacket packet = (RoomStatusUpdatePacket) obj;
        return TextUtils.equals(roomNumber, packet.roomNumber);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(roomNumber).append(type).toHashCode();
    }
}
