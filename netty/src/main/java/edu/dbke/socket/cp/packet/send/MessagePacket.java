package edu.dbke.socket.cp.packet.send;

import android.text.TextUtils;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/5/11.
 */

public class MessagePacket extends Packet<MessagePacket> {
    public final static int TYPE_SEND = 0x101;
    public final static int TYPE_RECEIVE = 0x102;
    public final static int TYPE_UNKNOWN = 0x100;
    public String userNum;//发送消息者sip
    public String peerNum;//发送对象sip
    public String msg;
    public int time;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.write256String(this.data, this.peerNum);
        ByteUtil.writeShortString(this.data, this.msg);
        this.data.putInt(this.time);
    }

    protected void readData() {
        this.userNum = ByteUtil.read256String(this.data);
        this.peerNum = ByteUtil.read256String(this.data);
        this.msg = ByteUtil.readShortString(this.data);
        this.time = this.data.getInt();
    }

    public MessagePacket() {
        this.type = 1202;
    }

    public MessagePacket(String userNum, byte status, String statusDesc) {
        this.userNum = userNum;
        this.msg = statusDesc;
        this.type = 1202;
    }

    @Override
    public String toString() {
        return "MessagePacket{" +
                "userNum='" + userNum + '\'' +
                ", peerNum='" + peerNum + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MessagePacket) {
            MessagePacket anoPacket = (MessagePacket) obj;
            return TextUtils.equals(userNum, anoPacket.userNum) && TextUtils.equals(peerNum, anoPacket.peerNum)
                    && TextUtils.equals(msg, anoPacket.msg) && time == anoPacket.time;
        } else {
            return false;
        }
    }
}
