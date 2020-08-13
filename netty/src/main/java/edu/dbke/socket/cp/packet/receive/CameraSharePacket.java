package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.packet.AbstractPacket;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class CameraSharePacket extends AbstractPacket<CameraSharePacket> {
    public String roomNum;
    public String cameraId;
    public String cameraName;
    public byte mType;
    public boolean isOpen;
    private String userNum;

    public void writeData() {
        ByteUtil.write256String(this.data, this.roomNum);
        ByteUtil.write256String(this.data, this.cameraId);
        ByteUtil.write256String(this.data, this.cameraName);
        this.data.put(this.mType);
        ByteUtil.writeBoolean(this.data, this.isOpen);
    }

    public void readData() {
        this.roomNum = ByteUtil.read256String(this.data);
        this.cameraId = ByteUtil.read256String(this.data);
        this.cameraName = ByteUtil.read256String(this.data);
        this.mType = this.data.get();
        this.isOpen = ByteUtil.readBoolean(this.data);
    }

    public CameraSharePacket() {
        this.type = 1129;
    }

    public CameraSharePacket(String roomNum, String cameraId, boolean isOpen) {
        this.roomNum = roomNum;
        this.cameraId = cameraId;
        this.isOpen = isOpen;
        this.type = 1129;
    }

    public String getUserNum() {
        return this.userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Override
    public String toString() {
        return "CameraSharePacket{" +
                "roomNum='" + roomNum + '\'' +
                ", cameraId='" + cameraId + '\'' +
                ", cameraName='" + cameraName + '\'' +
                ", mType=" + mType +
                ", isOpen=" + isOpen +
                ", userNum='" + userNum + '\'' +
                '}';
    }
}
