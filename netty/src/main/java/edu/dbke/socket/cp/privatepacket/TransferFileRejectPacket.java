package edu.dbke.socket.cp.privatepacket;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by zhuying on 2017/5/27.
 * 7, 0, 0, 106, 19, 0, 0, 0, 50, 48, 49, 55, 45, 48, 53, 45, 50, 55, 95, 49, 54, 45, 5....
 * 序列号+协议类型+文件id长度（byte）+跳过三个字节+具体数据
 */

public class TransferFileRejectPacket extends PrivatePacket {
    public String transferId;

    public TransferFileRejectPacket() {
        type = PrivateProtocolType.TRANSFER_FILE_REJECT;
    }

    @Override
    protected void writeData() {
        byte id_len = (byte) transferId.length();
        data.put(id_len);
        data.put((byte) 0);
        data.put((byte) 0);
        data.put((byte) 0);
        ByteUtil.writeNoLenString(data, transferId);
    }

    @Override
    protected void readData() {
        byte id_len = data.get();
        data.get();//跳过三个冗余段
        data.get();
        data.get();
        transferId = ByteUtil.readString(data, id_len);
    }

    @Override
    public String toString() {
        return "TransferFileReceivePacket{" +
                "transferId='" + transferId + '\'' +
                '}';
    }
}
