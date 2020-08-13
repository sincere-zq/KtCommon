package edu.dbke.socket.cp.privatepacket;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by zhuying on 2017/5/27.
 * -74, 0, 0, 105, 19, 0, 0, 0, 0, 0, 0, 0, 50, 48, 49, 55, 45, 48, 53, 45, 50, 55, 95, 49, 54, 45,
 * 序列号+协议类型+id长度(byte)+跳过3个字节+ 偏移（int）+具体数据
 */

public class TransferFileReceivePacket extends PrivatePacket {
    public String transferId;
    public int recvOffset;//接收文件时的偏移

    public TransferFileReceivePacket() {
        type = PrivateProtocolType.TRANSFER_FILE_RECEIVE;
    }

    @Override
    protected void writeData() {
        byte id_len = (byte) transferId.length();
        data.put(id_len);
        data.put((byte) 0);
        data.put((byte) 0);
        data.put((byte) 0);
        data.putInt(recvOffset);
        ByteUtil.writeNoLenString(data, transferId);
    }

    @Override
    protected void readData() {
        byte id_len = data.get();
        data.get();//跳过三个冗余段
        data.get();
        data.get();
        recvOffset = data.getInt();
        transferId = ByteUtil.readString(data, id_len);
    }

    @Override
    public String toString() {
        return "TransferFileReceivePacket{" +
                "transferId='" + transferId + '\'' +
                ", recvOffset=" + recvOffset +
                '}';
    }
}
