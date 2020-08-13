package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 * 拨号进入会议室后收到的会议室数据。
 */


public class ConfBridgeRoomPacket extends Packet<ConfBridgeRoomPacket> {
    public String name;
    public String roomNumber;
    public String presider;
    public short userSize;
    public short onlineSize;
    public int startTime;
    public boolean isPin;
    public boolean isLock;
    public boolean isClose;
    public boolean isRecordVoice;
    public boolean isRecordVideo;
    public boolean isSingleVideo;
    public byte isOnlooker;
    public String description;
    public byte usersMax;

    public void writeData() {
        ByteUtil.write256String(this.data, this.name);
        ByteUtil.write256String(this.data, this.roomNumber);
        ByteUtil.write256String(this.data, this.presider);
        this.data.putShort(this.userSize);
        this.data.putShort(this.onlineSize);
        this.data.putInt(this.startTime);
        ByteUtil.writeBoolean(this.data, this.isPin);
        ByteUtil.writeBoolean(this.data, this.isLock);
        ByteUtil.writeBoolean(this.data, this.isClose);
        ByteUtil.writeBoolean(this.data, this.isRecordVoice);
        ByteUtil.writeBoolean(this.data, this.isRecordVideo);
        ByteUtil.writeBoolean(this.data, this.isSingleVideo);
        this.data.put(this.isOnlooker);
        ByteUtil.writeShortString(this.data, this.description);
        this.data.put(this.usersMax);
    }

    public void readData() {
        this.name = ByteUtil.read256String(this.data);
        this.roomNumber = ByteUtil.read256String(this.data);
        this.presider = ByteUtil.read256String(this.data);
        this.userSize = this.data.getShort();
        this.onlineSize = this.data.getShort();
        this.startTime = this.data.getInt();
        this.isPin = ByteUtil.readBoolean(this.data);
        this.isLock = ByteUtil.readBoolean(this.data);
        this.isClose = ByteUtil.readBoolean(this.data);
        this.isRecordVoice = ByteUtil.readBoolean(this.data);
        this.isRecordVideo = ByteUtil.readBoolean(this.data);
        this.isSingleVideo = ByteUtil.readBoolean(this.data);
        this.isOnlooker = this.data.get();
        this.description = ByteUtil.readShortString(this.data);
        this.usersMax = this.data.get();
    }

    public ConfBridgeRoomPacket() {
        this.type = 1110;
    }

    public ConfBridgeRoomPacket(String roomNumber, boolean isClose) {
        this.roomNumber = roomNumber;
        this.isClose = isClose;
        this.type = 1110;
    }

    @Override
    public String toString() {
        return "ConfBridgeRoomPacket{" +
                "name='" + name + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", presider='" + presider + '\'' +
                ", userSize=" + userSize +
                ", onlineSize=" + onlineSize +
                ", startTime=" + startTime +
                ", isPin=" + isPin +
                ", isLock=" + isLock +
                ", isClose=" + isClose +
                ", isRecordVoice=" + isRecordVoice +
                ", isRecordVideo=" + isRecordVideo +
                ", isSingleVideo=" + isSingleVideo +
                ", isOnlooker=" + isOnlooker +
                ", description='" + description + '\'' +
                ", usersMax=" + usersMax +
                '}';
    }
}
