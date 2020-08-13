package edu.dbke.socket.cp.privatepacket;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/4/28.
 */

public class TransferAnswerPacket extends PrivatePacket {

    public String dutyCallNum; //值班端num
    public String customCallNum;//客户端num
    public String dutyOrderId;//单号
    public String expertCallNum;//专家端num
    public boolean accept;

    public TransferAnswerPacket(String dutyCallNum, String customCallNum, String dutyOrderId, String expertCallNum, boolean accept) {
        this.dutyCallNum = dutyCallNum;
        this.customCallNum = customCallNum;
        this.dutyOrderId = dutyOrderId;
        this.expertCallNum = expertCallNum;
        this.accept = accept;
    }

    @Override
    protected void writeData() {
        ByteUtil.write256String(data, dutyCallNum);
        ByteUtil.write256String(data, customCallNum);
        ByteUtil.write256String(data, dutyOrderId);
        ByteUtil.write256String(data, expertCallNum);
        ByteUtil.writeBoolean(data, accept);
    }

    public TransferAnswerPacket() {
        type = PrivateProtocolType.TRANSFER_CALL_ANSWER;
    }

    @Override
    protected void readData() {
        dutyCallNum = ByteUtil.read256String(data);
        customCallNum = ByteUtil.read256String(data);
        dutyOrderId = ByteUtil.read256String(data);
        expertCallNum = ByteUtil.read256String(data);
        accept = ByteUtil.readBoolean(data);
    }

    @Override
    public String toString() {
        return "TransferAnswerPacket{" +
                "dutyCallNum='" + dutyCallNum + '\'' +
                ", customCallNum='" + customCallNum + '\'' +
                ", dutyOrderId='" + dutyOrderId + '\'' +
                ", expertCallNum='" + expertCallNum + '\'' +
                ", accept=" + accept +
                '}';
    }
}
