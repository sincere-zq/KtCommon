package edu.dbke.socket.cp.packet.receive;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/31.
 */


public class UserStatusInfoPacket extends Packet<UserStatusInfoPacket> {
    public String userNum;
    public byte status;
    public String statusDesc;
    private String userName;
    private SocketChannel socket;
    private Map<String, SocketChannel> friendSockets = new ConcurrentHashMap();
    private List<String> friendList;
    private IpInfoPacket ipInfo;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userNum);
        this.data.put(this.status);
        ByteUtil.write256String(this.data, this.statusDesc);
    }

    protected void readData() {
        this.userNum = ByteUtil.read256String(this.data);
        this.status = this.data.get();
        this.statusDesc = ByteUtil.read256String(this.data);
    }

    public UserStatusInfoPacket() {
        this.type = 1141;
    }

    public UserStatusInfoPacket(String userNum, byte status, String statusDesc) {
        this.userNum = userNum;
        this.status = status;
        this.statusDesc = statusDesc;
        this.type = 1141;
    }

    public UserStatusInfoPacket(String userNum, byte status, String statusDesc, SocketChannel socket) {
        this.userNum = userNum;
        this.status = status;
        this.statusDesc = statusDesc;
        this.socket = socket;
        this.type = 1141;
    }

    public SocketChannel getSocket() {
        return this.socket;
    }

    public void setSocket(SocketChannel socket) {
        this.socket = socket;
    }

    public Map<String, SocketChannel> getFriendSockets() {
        return this.friendSockets;
    }

    public List<String> getFriendList() {
        return this.friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public IpInfoPacket getIpInfo() {
        return this.ipInfo;
    }

    public void setIpInfo(IpInfoPacket ipInfo) {
        this.ipInfo = ipInfo;
    }
}
