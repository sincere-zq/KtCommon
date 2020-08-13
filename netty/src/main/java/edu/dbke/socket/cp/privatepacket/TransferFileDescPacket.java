package edu.dbke.socket.cp.privatepacket;

import edu.dbke.socket.cp.util.ByteUtil;

/**
 * 文件传输描述协议（发送和收到文件传输请求）
 * 20, 3, 9, 11, 0, 0, 1, -108, 50, 48, 49, 55, 45, 53, 45, 50, 54, 95, 49, 54, 45, 50, 53, 45, 51, 95, 53, 49, 54, 48, 53, -23, -69, -124, -27, -93, -82, -26, -106, -121, 100, 101, 115, 107, 116, 111, 112, 46, 105, 110, 105
 * 数据格式：tranferIdLength(byte) + senderSipNumber(byte)
 * + senderNameLength(byte) + fileNameLength(byte) +fileSize(int)
 * + 根据前面解析的长度解析具体数据内容
 */

public class TransferFileDescPacket extends PrivatePacket {
    public int fileLength;
    public String transferId;
    public String senderSip;
    public String senderName;
    public String fileName;

    public boolean isSender;

    public TransferFileDescPacket() {
        type = PrivateProtocolType.TRANSFER_FILE_REQUEST;
    }

    @Override
    protected void writeData() {
        byte id_len = (byte) transferId.length();
        byte sip_num_len = (byte) senderSip.length();
        byte sip_name_len = (byte) senderName.length();
        byte file_name_len = (byte) fileName.length();
        data.put(id_len);
        data.put(sip_num_len);
        data.put(sip_name_len);
        data.put(file_name_len);
        data.putInt(fileLength);
        ByteUtil.writeNoLenString(data, transferId);
        ByteUtil.writeNoLenString(data, senderSip);
        ByteUtil.writeNoLenString(data, senderName);
        ByteUtil.writeNoLenString(data, fileName);
    }

    @Override
    protected void readData() {
        byte id_len = data.get();
        byte sip_num_len = data.get();
        byte sip_name_len = data.get();
        byte file_name_len = data.get();
        fileLength = data.getInt();
        transferId = ByteUtil.readString(data, id_len);
        senderSip = ByteUtil.readString(data, sip_num_len);
        senderName = ByteUtil.readString(data, sip_name_len);
        fileName = ByteUtil.readString(data, file_name_len);
    }

    @Override
    public String toString() {
        return "TransferFileDescPacket{" +
                "fileLength=" + fileLength +
                ", transferId='" + transferId + '\'' +
                ", senderSip='" + senderSip + '\'' +
                ", senderName='" + senderName + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
