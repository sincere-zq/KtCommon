package edu.dbke.socket.cp.privatepacket;

import java.util.Arrays;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by zhuying on 2017/5/27.
 * 64, 1, 0, 108, 21, 0, -56, 0, 50, 48, 49, 55, 45, 53, 45, 50, 56, 95, 49, 56, 45, 50, 52, 45, 49,
 * 序列号+协议类型+文件id长度（byte）+ 本次数据长度（short）+跳过一个字节+文件id+具体数据内容；
 */

public class TransferFileDataPacket extends PrivatePacket {
    public String transferId;
    public short fileLen;//本次传输的文件长度
    public byte[] fileData;

    public TransferFileDataPacket() {
        type = PrivateProtocolType.TRANSFER_FILE_SEND_DATA;
    }

    @Override
    protected void writeData() {
        byte id_len = (byte) transferId.length();
        data.put(id_len);
        data.putShort(fileLen);
        data.put((byte) 0);
        ByteUtil.writeNoLenString(data, transferId);
        ByteUtil.writeBytes(data, fileData);
    }

    @Override
    protected void readData() {
        byte id_len = data.get();
        fileLen = data.getShort();
        data.get(); //跳过一个
        transferId = ByteUtil.readString(data, id_len);
        fileData = new byte[fileLen];
        data.get(fileData);
    }

    @Override
    public String toString() {
        return "TransferFileDataPacket{" +
                "transferId='" + transferId + '\'' +
                ", fileLen=" + fileLen +
                ", fileData=" + Arrays.toString(fileData) +
                '}';
    }
}
