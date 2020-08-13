package edu.dbke.socket.cp.packet.send;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 * 加入排队，加入后会实时收到排队位置
 */


public class QueueUserPacket extends Packet<QueueUserPacket> {
    public String queueNum;
    public String userNum;
    public String userName;
    public byte userType;
    public int queueingTime;
    public int position;
    private long joinTime;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.queueNum);
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.write256String(this.data, this.userName);
        this.data.put(this.userType);
        this.data.putInt(this.queueingTime);
        this.data.putInt(this.position);
    }

    protected void readData() {
        this.queueNum = ByteUtil.read256String(this.data);
        this.userNum = ByteUtil.read256String(this.data);
        this.userName = ByteUtil.read256String(this.data);
        this.userType = this.data.get();
        this.queueingTime = this.data.getInt();
        this.position = this.data.getInt();
    }


    public QueueUserPacket() {
        this.type = 1340;
    }

    public QueueUserPacket(String queueNum, String userNum) {
        this.queueNum = queueNum;
        this.userNum = userNum;
        this.type = 1340;
    }

    public QueueUserPacket(String queueNum, String userNum, String userName, byte userType, int queueingTime, int position) {
        this.queueNum = queueNum;
        this.userNum = userNum;
        this.userName = userName;
        this.userType = userType;
        this.queueingTime = queueingTime;
        this.position = position;
        this.type = 1340;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(new Object[]{this.queueNum, this.userNum});
    }

    public boolean equals(Object obj) {
        if (obj instanceof QueueUserPacket) {
            QueueUserPacket cast = (QueueUserPacket) obj;
            if (null != this.queueNum && null != this.userNum && this.queueNum.equals(cast.queueNum) && this.userNum.equals(cast.userNum)) {
                return true;
            }
        }

        return false;
    }

    public long getJoinTime() {
        return this.joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    @Override
    public String toString() {
        return "QueueUserPacket{" +
                "queueNum='" + queueNum + '\'' +
                ", userNum='" + userNum + '\'' +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", queueingTime=" + queueingTime +
                ", position=" + position +
                ", joinTime=" + joinTime +
                '}';
    }

    public QueueUserPacket copy() {
        QueueUserPacket packet = new QueueUserPacket();
        packet.size = size;
        packet.type = type;
        packet.queueNum = this.queueNum;
        packet.userNum = this.userNum;
        packet.userName = this.userName;
        packet.userType = this.userType;
        packet.queueingTime = this.queueingTime;
        packet.position = this.position;
        packet.joinTime = this.joinTime;
        return packet;
    }
}
