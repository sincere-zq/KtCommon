package edu.dbke.socket.cp.privatepacket;

import java.nio.ByteBuffer;

import edu.dbke.socket.cp.packet.receive.External2ClientPacket;

/**
 * 隶属于External2ClientPacket下的私有协议。字段结构
 * 第1，2字节：seqNum,自定义使用
 * 第3,4字节：私有协议号，参考{@link PrivateProtocolType}
 * 第5字节开始：自定义数据；
 */

public abstract class PrivatePacket<T> {

    public short seq;//消息顺序号
    public short type;//消息类型
    public ByteBuffer data;//数据

    /**
     * 发送一个对象
     *
     * @return
     */
    public ByteBuffer writeObject() {
        data = ByteBuffer.allocate(1024 * 128 + 14);//数据
        data.putShort((short) 1);
        data.putShort(type);
        writeData();
        data.flip();
        byte[] dataCopy = new byte[data.limit()];
        System.arraycopy(data.array(), 0, dataCopy, 0, data.limit());
        return ByteBuffer.wrap(dataCopy);
    }

    protected abstract void writeData();

    public static int getType(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.getShort();//跳过第一个
        return byteBuffer.getShort();
    }

    /**
     * 读取一个对象
     */
    @SuppressWarnings("unchecked")
    public T readObject(ByteBuffer data) {
        try {
            data.rewind();
            this.data = data;
            this.seq = data.getShort();
            this.type = data.getShort();
            readData();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return (T) this;
    }

    protected abstract void readData();

    /**
     * 发送一个对象
     *
     * @return
     */
    final public byte[] writeByteObject() {
        return writeObject().array();
    }

    /**
     * 读取一个对象
     */
    final public T readByteObject(byte[] data) {
        return readObject(ByteBuffer.wrap(data));
    }

    @Override
    public String toString() {
        return "PrivatePacket{" +
                "seq=" + seq +
                ", type=" + type +
                '}';
    }

    public static PrivatePacket convertToPrivatePacket(byte[] bytes) {
        PrivatePacket packet = null;
        int type = getType(bytes);
        switch (type) {
            case PrivateProtocolType.TRANSFER_CALL_REQUEST:
                packet = new TransferRequestPacket();
                break;
            case PrivateProtocolType.TRANSFER_CALL_ANSWER:
                packet = new TransferAnswerPacket();
                break;
            case PrivateProtocolType.TRANSFER_CALL_ACK:
                packet = new TransferAckPacket();
                break;
            case PrivateProtocolType.TRANSFER_FILE_REQUEST:
                packet = new TransferFileDescPacket();
                break;
            case PrivateProtocolType.TRANSFER_FILE_RECEIVE:
                packet = new TransferFileReceivePacket();
                break;
            case PrivateProtocolType.TRANSFER_FILE_SEND_DATA:
                packet = new TransferFileDataPacket();
            default:

        }
        if (packet != null) {
            packet.readByteObject(bytes);
        }
        return packet;
    }

    public static PrivatePacket convertToPrivatePacket(External2ClientPacket clientPacket) {
        return convertToPrivatePacket(clientPacket.bytesData);
    }


    public static void main(String args[]) {
//        0, 5, 0, 100, -128, 3, 54, 48, 53
        //0, 4, 0, 112, 3, 54, 48, 53, 3, 54, 48, 53, 9, -23, -69, -124, -27, -93, -82, -26, -106, -121, 19, -26, -103, -70, -24, -95, -116, -25, -108, -75, -27, -83, -112, 45, -27, -92, -106, -23, -125, -88
        byte[] bytes = new byte[]{0, 37, 0, 101, 3, 54, 48, 53, 3, 50, 48, 51, 32, 102, 102, 56, 48, 56, 48, 56, 49, 53, 98, 49, 51, 97, 53, 51, 57, 48, 49, 53, 98, 98, 56, 101, 48, 99, 101, 53, 57, 48, 48, 56, 99, 10, -28, -67, -107, -23, -100, -98, 45, 50, 48, 51, 0, 0, 107, -23, -69, -124, -27, -93, -82, -26, -106, -121, 32, -26, -83, -93, -27, -100, -88, -27, -112, -111, -26, -126, -88, -27, -113, -111, -27, -121, -70, -27, -128, -68, -25, -113, -83, -27, -110, -88, -24, -81, -94, -24, -67, -84, -26, -114, -91, -24, -81, -73, -26, -79, -126, -17, -68, -116, -27, -110, -88, -24, -81, -94, -27, -82, -94, -26, -120, -73, -17, -68, -102, -28, -67, -107, -23, -100, -98, 45, 50, 48, 51, -17, -68, -120, 50, 48, 51, -17, -68, -119, -17, -68, -116, -26, -104, -81, -27, -112, -90, -27, -112, -116, -26, -124, -113, -17, -68, -97};
//        byte[] bytes = new byte[]{0, 2, 0, 102, 3, 54, 48, 49, 3, 50, 48, 51, 32, 102, 102, 56, 48, 56, 48, 56, 49, 53, 98, 49, 51, 97, 53, 51, 57, 48, 49, 53, 98, 98, 55, 97, 53, 57, 99, 51, 48, 48, 48, 56, 56, 3, 54, 48, 49, 1};
//        byte[] bytes = new byte[]{0, 1, 0, 102, 3, 54, 48, 53, 3, 50, 48, 51, 32, 102, 102, 56, 48, 56, 48, 56, 49, 53, 98, 49, 51, 97, 53, 51, 57, 48, 49, 53, 98, 98, 56, 100, 54, 52, 48, 56, 56, 48, 48, 56, 97, 3, 50, 48, 49, 1};
//        byte[] bytes = new byte[]{0, 10, 0, 103, 3, 54, 48, 53, 3, 50, 48, 51, 32, 102, 102, 56, 48, 56, 48, 56, 49, 53, 98, 49, 51, 97, 53, 51, 57, 48, 49, 53, 98, 98, 55, 97, 53, 57, 99, 51, 48, 48, 48, 56, 56, 3, 54, 48, 49, 1};
//        TransferAnswerPacket packet = new TransferAnswerPacket();
//        TransferAckPacket packet = new TransferAckPacket();
        PrivatePacket packet = convertToPrivatePacket(bytes);
        System.out.print(packet.toString());
    }
}
