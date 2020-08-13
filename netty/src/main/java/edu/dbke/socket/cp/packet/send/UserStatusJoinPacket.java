package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class UserStatusJoinPacket extends Packet<UserStatusJoinPacket> {
    public String userNum;
    public String userName;
    public byte status;
    public String statusDesc;
    public String serverName;
    public String streamServerIps;
    public static byte JOIN_ERROR = 0;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.write256String(this.data, this.userName);
        this.data.put(this.status);
        ByteUtil.write256String(this.data, this.statusDesc);
        ByteUtil.write256String(this.data, this.serverName);
        ByteUtil.writeShortString(this.data, this.streamServerIps);
    }

    protected void readData() {
        this.userNum = ByteUtil.read256String(this.data);
        this.userName = ByteUtil.read256String(this.data);
        this.status = this.data.get();
        this.statusDesc = ByteUtil.read256String(this.data);
        this.serverName = ByteUtil.read256String(this.data);
        this.streamServerIps = ByteUtil.readShortString(this.data);
    }

    public UserStatusJoinPacket() {
        this.type = 1140;
    }

    public UserStatusJoinPacket(String userNum, byte status, String statusDesc) {
        this.userNum = userNum;
        this.status = status;
        this.statusDesc = statusDesc;
        this.type = 1140;
    }

    public UserStatusJoinPacket(String userNum, String userName, byte status, String statusDesc, String serverName, String streamServerIps) {
        this.userNum = userNum;
        this.userName = userName;
        this.status = status;
        this.statusDesc = statusDesc;
        this.serverName = serverName;
        this.streamServerIps = streamServerIps;
    }

    @Override
    public String toString() {
        return "UserStatusJoinPacket{" +
                "userNum='" + userNum + '\'' +
                ", userName='" + userName + '\'' +
                ", status=" + status +
                ", statusDesc='" + statusDesc + '\'' +
                ", serverName='" + serverName + '\'' +
                ", streamServerIps='" + streamServerIps + '\'' +
                '}';
    }
}