package edu.dbke.socket.cp;

import com.witaction.netty.config.NettyConstant;

import java.nio.ByteBuffer;


/**
 * 数据包，采用非javabean的方式，不参与数据收发的部分使用javabean的方式</br>
 * 必须以*Packet的方式命名，参考实现{@link edu.dbke.socket.cp.service.BigDataPacket}</br>
 * 书写顺序：1、数据成员变量，2、writeData()，3、readData()，4默认构造方法</br>
 * 要发送的值不会改变时，多次发送同一个对像不能手动生成 ByteBuffer buf = cp.writeObject();将导致只有一个人能收到数据，同一个ByteBuffer对象不能复用
 *
 * @author huitang
 */
public abstract class Packet<T> {
    private byte ver;
    public short type;//协议类型
    public int size;//数据长度
    public ByteBuffer data;//数据

    /**
     * 发送一个对象
     *
     * @return
     */
    public ByteBuffer writeObject() {
        data = ByteBuffer.allocate(NettyConstant.CHANNEL_MAX_SIZE + 14);//数据
        data.put((byte) 0xfe);
        data.put((byte) 0xfe);
        data.put((byte) 0xfe);
        data.put((byte) 0xfe);
        data.put((byte) 0x01);
        data.putShort(type);
        data.putInt(-1);
        writeData();
        data.put((byte) 0xff);
        data.put((byte) 0xff);
        data.put((byte) 0xff);
        data.put((byte) 0xff);
        data.flip();
        data.putInt(7, data.limit() - 15);
        byte[] dataCopy = new byte[data.limit()];
        System.arraycopy(data.array(), 0, dataCopy, 0, data.limit());
        return ByteBuffer.wrap(dataCopy);
    }

    /**
     * 读取一个对象
     */
    @SuppressWarnings("unchecked")
    public T readObject(ByteBuffer data) {
        try {
            data.rewind();
            this.data = data;
            data.getInt();
            this.ver = data.get();
            this.type = data.getShort();
            this.size = data.getInt();
            readData();
            data.getInt();
            if (size != data.limit() - 15) {
                throw new RuntimeException("packet data error!expect " + size + " but " + data.limit() + "received");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("read packet error!type:" + type + " size" + size);
        } finally {
        }
        return (T) this;
    }

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

    /**
     * 写数据
     *
     * @return
     */
    protected abstract void writeData();

    /**
     * 读数据
     *
     * @return
     */
    protected abstract void readData();

    @Override
    public String toString() {
        return "Packet{" +
                "size=" + size +
                ", type=" + type +
                '}';
    }
}
