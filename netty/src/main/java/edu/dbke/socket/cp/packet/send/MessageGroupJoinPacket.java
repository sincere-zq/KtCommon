package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

import static edu.dbke.socket.cp.ProtocolType.MESSAGE_GROUP_JOIN;

/**
 * Created by Zhangs on 2017/5/22.
 */
public class MessageGroupJoinPacket extends Packet<MessageGroupJoinPacket> {
    public String groupNum;
    public String userNum;
    @Override
    protected void writeData() {
        ByteUtil.write256String(this.data, this.groupNum);
        ByteUtil.write256String(this.data, this.userNum);
    }
    @Override
    protected void readData() {
        this.groupNum = ByteUtil.read256String(this.data);
        this.userNum = ByteUtil.read256String(this.data);
    }

    public MessageGroupJoinPacket() {
        this.type = MESSAGE_GROUP_JOIN;
    }

    public MessageGroupJoinPacket(String groupNum, String userNum) {
        this.userNum = userNum;
        this.groupNum = groupNum;
        this.type = MESSAGE_GROUP_JOIN;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    @Override
    public String toString() {
        return "MessageGroupJoinPacket{" +
                "groupNum='" + groupNum + '\'' +
                ", userNum='" + userNum + '\'' +
                '}';
    }
}