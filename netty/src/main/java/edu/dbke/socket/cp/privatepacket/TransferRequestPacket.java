package edu.dbke.socket.cp.privatepacket;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/4/28.
 */

public class TransferRequestPacket extends PrivatePacket {
    public String targetNum;//值班人员sip
    public String customerNum;//客户sip
    public String dutyOrderId;
    public String customerName;//客户姓名
    public String desc;

    public TransferRequestPacket() {
        type = PrivateProtocolType.TRANSFER_CALL_REQUEST;
    }

    public TransferRequestPacket(String dutyNum, String customerNum, String dutyId, String customerName, String desc) {
        type = PrivateProtocolType.TRANSFER_CALL_REQUEST;
        this.targetNum = dutyNum;
        this.customerNum = customerNum;
        this.dutyOrderId = dutyId;
        this.customerName = customerName;
        this.desc = desc;
    }

    @Override
    protected void writeData() {
        ByteUtil.write256String(data, targetNum);
        ByteUtil.write256String(data, customerNum);
        ByteUtil.write256String(data, dutyOrderId);
        ByteUtil.write256String(data, customerName);
        ByteUtil.writeShortString(data, "0");
        ByteUtil.write256String(data, desc);
    }

    @Override
    protected void readData() {
        targetNum = ByteUtil.read256String(data);
        customerNum = ByteUtil.read256String(data);
        dutyOrderId = ByteUtil.read256String(data);
        customerName = ByteUtil.read256String(data);
        ByteUtil.readShortString(data);//从协议数据中看要跳过2个字节；
        desc = ByteUtil.readString(data);
    }

    @Override
    public String toString() {
        return "TransferRequestPacket{" +
                "targetNum='" + targetNum + '\'' +
                ", customerNum='" + customerNum + '\'' +
                ", dutyOrderId='" + dutyOrderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
