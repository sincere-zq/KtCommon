package edu.dbke.socket.cp.packet.receive;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.packet.send.QueueUserPacket;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/10.
 * 队列状态结果
 */

public class QueueStatusPacket extends Packet<QueueStatusPacket> {
    public QueueUserPacket userPacket;//用户排队的状态；
    public String queueNum;
    public String queueName;
    public String serviceIds;
    public byte queueType;
    public int userCount;
    public int serviceCount;
    public int maxWaitingCount;
    public int totalServiceCount;
    public byte status;
    public static byte TYPE_PHONE = 0;
    public static byte TYPE_VIDEO = 1;
    public static byte STATUS_NORMAL = 0;
    public static byte STATUS_LOCKED = 1;
    public static byte STATUS_CLOSED = 2;
    public static byte STATUS_INVISIBLE = 3;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.queueNum);
        ByteUtil.write256String(this.data, this.queueName);
        ByteUtil.writeShortString(this.data, this.serviceIds);
        this.data.put(this.queueType);
        this.data.putInt(this.userCount);
        this.data.putInt(this.serviceCount);
        this.data.putInt(this.totalServiceCount);
        this.data.putInt(this.maxWaitingCount);
        this.data.put(this.status);
    }

    protected void readData() {
        this.queueNum = ByteUtil.read256String(this.data);
        this.queueName = ByteUtil.read256String(this.data);
        this.serviceIds = ByteUtil.readShortString(this.data);
        this.queueType = this.data.get();
        this.userCount = this.data.getInt();
        this.serviceCount = this.data.getInt();
        this.totalServiceCount = this.data.getInt();
        this.maxWaitingCount = this.data.getInt();
        this.status = this.data.get();
    }

    public QueueStatusPacket() {
        this.type = 1344;
    }

    public QueueStatusPacket(String queueNum, String queueName, byte queueType, int userCount, int serviceCount, int maxWaitingCount, int totalServiceCount, byte status) {
        this.queueNum = queueNum;
        this.queueName = queueName;
        this.queueType = queueType;
        this.userCount = userCount;
        this.serviceCount = serviceCount;
        this.maxWaitingCount = maxWaitingCount;
        this.totalServiceCount = totalServiceCount;
        this.status = status;
        this.type = 1344;
    }

    public int hashCode() {
        return (new HashCodeBuilder()).append(this.queueNum).hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof QueueStatusPacket) {
            QueueStatusPacket cast = (QueueStatusPacket) obj;
            if (null != this.queueNum && this.queueNum.equals(cast.queueNum)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String userPacketStr = (userPacket == null) ? "null" : userPacket.toString();
        return "QueueStatusPacket{" +
                "QueueUserPacket" + userPacketStr + '\'' +
                "queueNum='" + queueNum + '\'' +
                ", queueName='" + queueName + '\'' +
                ", serviceIds='" + serviceIds + '\'' +
                ", queueType=" + queueType +
                ", userCount=" + userCount +
                ", serviceCount=" + serviceCount +
                ", maxWaitingCount=" + maxWaitingCount +
                ", totalServiceCount=" + totalServiceCount +
                ", status=" + status +
                super.toString() +
                '}';
    }
}
