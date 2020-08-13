package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/5/16.
 */

public class MessageGroupPacket extends Packet<MessageGroupPacket> {
    public String groupNum;
    public String userNum;
    public String msg;
    public int time;

    @Override
    protected void writeData() {
        ByteUtil.write256String(this.data, this.groupNum);
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.writeShortString(this.data, this.msg);
        this.data.putInt(this.time);
    }

    @Override
    protected void readData() {
        this.groupNum = ByteUtil.read256String(this.data);
        this.userNum = ByteUtil.read256String(this.data);
        this.msg = ByteUtil.readShortString(this.data);
        this.time = this.data.getInt();
    }

    public MessageGroupPacket() {
        this.type = 1204;
    }

    public MessageGroupPacket(String groupNum, String userNum, String msg) {
        this.groupNum = groupNum;
        this.userNum = userNum;
        this.msg = msg;
        this.type = 1204;
    }

    @Override
    public String toString() {
        return "MessageGroupPacket{" +
                "groupNum='" + groupNum + '\'' +
                ", userNum='" + userNum + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }
}
