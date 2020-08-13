package edu.dbke.socket.cp.privatepacket;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/4/28.
 */

public class TransferAckPacket extends PrivatePacket {
    public String dutyCallNum;
    public String customerCallNum;
    public String dutyOrderId;
    public String expertCallNum;
    boolean flag;

    public TransferAckPacket() {
        type = PrivateProtocolType.TRANSFER_CALL_ACK;
    }

    public TransferAckPacket(String dutyCallNum, String customerCallNum, String dutyOrderId, String expertCallNum, boolean flag) {
        this.dutyCallNum = dutyCallNum;
        this.customerCallNum = customerCallNum;
        this.dutyOrderId = dutyOrderId;
        this.expertCallNum = expertCallNum;
        this.flag = flag;
    }

    @Override
    protected void writeData() {
        ByteUtil.write256String(data, dutyCallNum);
        ByteUtil.write256String(data, customerCallNum);
        ByteUtil.write256String(data, dutyOrderId);
        ByteUtil.write256String(data, expertCallNum);
        ByteUtil.writeBoolean(data, flag);
    }

    @Override
    protected void readData() {
        dutyCallNum = ByteUtil.read256String(data);
        customerCallNum = ByteUtil.read256String(data);
        dutyOrderId = ByteUtil.read256String(data);
        expertCallNum = ByteUtil.read256String(data);
        flag = ByteUtil.readBoolean(data);
    }

    @Override
    public String toString() {
        return "TransferAckPacket{" +
                "dutyCallNum='" + dutyCallNum + '\'' +
                ", customCallNum='" + customerCallNum + '\'' +
                ", dutyOrderId='" + dutyOrderId + '\'' +
                ", expertCallNum='" + expertCallNum + '\'' +
                ", flag=" + flag +
                '}';
    }
}
