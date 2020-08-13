package edu.dbke.socket.cp.packet.receive;

import java.util.Date;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class ConfBridgeUserPacket extends Packet<ConfBridgeUserPacket> {
    public String userName;
    public String userNumber;
    public String roomNumber;
    public Date dateJoined;
    public Date dateLeft;
    public boolean muted;
    public boolean talking;
    public boolean videoDisable;
    public boolean isAdmin;
    public boolean isRecordVoice;
    public boolean isRecordVideo;
    public boolean isBarrierVoice;
    public boolean isDesktopShare;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userName);
        ByteUtil.write256String(this.data, this.userNumber);
        ByteUtil.write256String(this.data, this.roomNumber);
        ByteUtil.writeDate(this.data, this.dateJoined);
        ByteUtil.writeDate(this.data, this.dateLeft);
        ByteUtil.writeBoolean(this.data, this.muted);
        ByteUtil.writeBoolean(this.data, this.talking);
        ByteUtil.writeBoolean(this.data, this.videoDisable);
        ByteUtil.writeBoolean(this.data, this.isAdmin);
        ByteUtil.writeBoolean(this.data, this.isRecordVoice);
        ByteUtil.writeBoolean(this.data, this.isRecordVideo);
        ByteUtil.writeBoolean(this.data, this.isBarrierVoice);
        ByteUtil.writeBoolean(this.data, this.isDesktopShare);
    }

    protected void readData() {
        this.userName = ByteUtil.read256String(this.data);
        this.userNumber = ByteUtil.read256String(this.data);
        this.roomNumber = ByteUtil.read256String(this.data);
        this.dateJoined = ByteUtil.readDate(this.data);
        this.dateLeft = ByteUtil.readDate(this.data);
        this.muted = ByteUtil.readBoolean(this.data);
        this.talking = ByteUtil.readBoolean(this.data);
        this.videoDisable = ByteUtil.readBoolean(this.data);
        this.isAdmin = ByteUtil.readBoolean(this.data);
        this.isRecordVoice = ByteUtil.readBoolean(this.data);
        this.isRecordVideo = ByteUtil.readBoolean(this.data);
        this.isBarrierVoice = ByteUtil.readBoolean(this.data);
        this.isDesktopShare = ByteUtil.readBoolean(this.data);
    }

    public ConfBridgeUserPacket() {
        this.type = 1112;
    }

    @Override
    public String toString() {
        return "ConfBridgeUserPacket{" +
                "userName='" + userName + '\'' +
                ", userNumber='" + userNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", dateJoined=" + dateJoined +
                ", dateLeft=" + dateLeft +
                ", muted=" + muted +
                ", talking=" + talking +
                ", videoDisable=" + videoDisable +
                ", isAdmin=" + isAdmin +
                ", isRecordVoice=" + isRecordVoice +
                ", isRecordVideo=" + isRecordVideo +
                ", isBarrierVoice=" + isBarrierVoice +
                ", isDesktopShare=" + isDesktopShare +
                '}';
    }
}
