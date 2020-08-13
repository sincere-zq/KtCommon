package edu.dbke.socket.cp.util;

import android.text.TextUtils;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Vector;

/**
 * 字节数据转换工具
 *
 * @author huitang
 */
public class ByteUtil {
    public static void main(String[] args) {
        //		System.out.println(byteToShort(shortToByte((short) (Short.MAX_VALUE))));
        //		System.out.println(byteToInt(intToByte(Integer.MAX_VALUE)));
//        System.out.println(byteToLong(longToByte(Long.MIN_VALUE)));
//        System.out.println(longToByte(Long.MAX_VALUE));
//        //		System.out.println(byteToShort(shortToByte((short) (Short.MIN_VALUE))));
//        //		System.out.println(byteToInt(intToByte(Integer.MIN_VALUE)));
//        //		System.out.println(byteToLong(longToByte(Long.MIN_VALUE)));
//
//        System.out.println(unSignIntToLong(LongToUnSignInt(3816622080l)));


    }

    public static void printByteBuffer(ByteBuffer byteBuffer) {
        byte[] bytes = byteBuffer.array();
        printBytes(bytes);
    }

    public static void printBytes(byte[] bytes) {
        String str = "";
        for (byte b : bytes) {
            str += (Byte.toString(b));
            str += ",";
        }
        LogUtils.e(str);
        System.out.print(str);
    }

    public static byte[] shortToByte(short data) {
        byte[] buffer = new byte[2];
        buffer[0] = (byte) ((data & 0xff00) >> 8);
        buffer[1] = (byte) (data & 0x00ff);
        return buffer;
    }

    public static short byteToShort(byte[] b) {
        short s0 = (short) (b[0] << 8 & 0xff00);
        short s1 = (short) (b[1] & 0x00ff);
        return (short) (s0 | s1);
    }

    public static byte[] intToByte(int data) {
        byte[] buffer = new byte[4];
        buffer[0] = (byte) ((data & 0xff000000) >> 24);
        buffer[1] = (byte) ((data & 0x00ff0000) >> 16);
        buffer[2] = (byte) ((data & 0x0000ff00) >> 8);
        buffer[3] = (byte) (data & 0x000000ff);
        return buffer;
    }

    public static int byteToInt(byte[] b) {
        int s0 = b[0] << 24 & 0xff000000;
        int s1 = b[1] << 16 & 0x00ff0000;
        int s2 = b[2] << 8 & 0x0000ff00;
        int s3 = b[3] & 0x000000ff;
        return s0 | s1 | s2 | s3;
    }

    public static byte[] longToByte(long data) {
        byte[] buffer = new byte[8];
        buffer[0] = (byte) ((data & 0xff00000000000000l) >> 56);
        buffer[1] = (byte) ((data & 0x00ff000000000000l) >> 48);
        buffer[2] = (byte) ((data & 0x0000ff0000000000l) >> 40);
        buffer[3] = (byte) ((data & 0x000000ff00000000l) >> 32);
        buffer[4] = (byte) ((data & 0x00000000ff000000l) >> 24);
        buffer[5] = (byte) ((data & 0x0000000000ff0000l) >> 16);
        buffer[6] = (byte) ((data & 0x000000000000ff00l) >> 8);
        buffer[7] = (byte) (data & 0x00000000000000ffl);
        return buffer;
    }

    public static long byteToLong(byte[] b) {
        long s0 = (long) b[0] << 56 & 0xff00000000000000l;//不知道为什么，前面加个long转换就对了
        long s1 = b[1] << 48 & 0x00ff000000000000l;
        long s2 = b[2] << 40 & 0x0000ff0000000000l;
        long s3 = b[3] << 32 & 0x000000ff00000000l;
        long s4 = b[4] << 24 & 0x00000000ff000000l;
        long s5 = b[5] << 16 & 0x0000000000ff0000l;
        long s6 = b[6] << 8 & 0x000000000000ff00l;
        long s7 = b[7] & 0x00000000000000ffl;
        return s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
    }

    /**
     * 用负数表示无符号int
     *
     * @param data
     * @return
     */
    public static int LongToUnSignInt(long data) {
        if (data >= 0 && data <= 2147483647) {
            return (int) data;
        } else if (data > 2147483647 && data < 4294967296l) {
            return (int) -(data - 2147483647);
        } else {
            throw new RuntimeException("数据超出表示范围");
        }
    }

    /**
     * 用long表示无符号int真实值
     *
     * @param data
     * @return
     */
    public static long unSignIntToLong(int data) {
        if (data >= 0) {
            return data;
        } else {
            return (2147483647l - data);
        }
    }

    /**
     * @param length
     * @return
     */
    public static byte writeIntToUnSignByte(int length) {
        if (length > 127 && length < 256) {
            return (byte) (-(length - 127));
        } else if (length >= 0 && length <= 127) {
            return (byte) length;
        } else {
            throw new RuntimeException("数据超出范围");
        }
    }

    /**
     * @param length
     * @return
     */
    public static short readUnSignByte(byte length) {
        if (length < 0) {
            return (byte) (127 - length);
        } else {
            return length;
        }
    }

    /**
     * 发送长度不超过256的字符字节
     *
     * @param data 数据包buffer
     * @param str  要发送的字符
     */
    public static void write256String(ByteBuffer data, String str) {
        if (null != str) {
            byte[] bytStr = null;
            try {
                bytStr = str.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            data.put(ByteUtil.writeIntToUnSignByte(bytStr.length));
            data.put(bytStr);
        } else {
            data.put((byte) 0);
        }
    }

    /**
     * 将Str直接填充到data里，不添加数据长度
     *
     * @param data
     * @param str
     */
    public static void writeNoLenString(ByteBuffer data, String str) {
        if (!TextUtils.isEmpty(str)) {
            byte[] bytStr = null;
            try {
                bytStr = str.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            data.put(bytStr);
        }
    }

    /**
     * 将byte[]直接写入bytebuffer;
     */
    public static void writeBytes(ByteBuffer byteBuffer, byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            byteBuffer.put(bytes);

        }
    }

    /**
     * 读取长度不超过256的字符
     *
     * @param data 数据包buffer
     * @return
     */
    public static String read256String(ByteBuffer data) {
        short length = ByteUtil.readUnSignByte(data.get());
        if (length == 0) {
            return null;
        } else {
            byte[] byteData = new byte[length];//读取数据长度
            data.get(byteData);
            try {
                return new String(byteData, "utf-8");
            } catch (UnsupportedEncodingException e) {
                return new String("");
            }
        }
    }

    /**
     * 读取指定长度
     *
     * @param data 数据包buffer
     * @return
     */
    public static String readString(ByteBuffer data, int length) {
        if (length == 0) {
            return null;
        } else {
            byte[] byteData = new byte[length];//读取数据长度
            data.get(byteData);
            try {
                return new String(byteData, "utf-8");
            } catch (UnsupportedEncodingException e) {
                LogUtils.e("解码失败..." + data.toString());
                return new String("");
            }
        }
    }

    /**
     * @param data 数据包buffer
     */
    public static void writeDateToInt(ByteBuffer data, Date date) {
        if (null != date) {
            data.putInt((int) (date.getTime() / 1000));
        } else {
            data.putInt(0);
        }
    }

    /**
     * @param data 数据包buffer
     * @return
     */
    public static Date readDateFromInt(ByteBuffer data) {
        int time = data.getInt();
        if (time == 0) {
            return null;
        } else {
            Date date = new Date();
            date.setTime(((long) time) * 1000);
            return date;
        }
    }

    /**
     * @param data 数据包buffer
     */
    public static void writeDateToLong(ByteBuffer data, Date date) {
        if (null != date) {
            data.putLong(date.getTime() / 1000);
        } else {
            data.putLong(0);
        }
    }

    /**
     * @param data 数据包buffer
     * @return
     */
    public static Date readDateFromLong(ByteBuffer data) {
        long time = data.getLong();
        if (time == 0) {
            return null;
        } else {
            Date date = new Date();
            date.setTime(time * 1000);
            return date;
        }
    }

    /**
     * @param data 数据包buffer
     */
    public static void writeDate(ByteBuffer data, Date date) {
        if (null != date) {
            data.putDouble(date.getTime());
        } else {
            data.putDouble(0);
        }
    }

    /**
     * @param data 数据包buffer
     * @return
     */
    public static Date readDate(ByteBuffer data) {
        long time = data.getLong();
        if (time == 0) {
            return null;
        } else {
            Date date = new Date();
            date.setTime(time / 1000);
            return date;
        }
    }

    /**
     * 写boolean
     *
     * @param b
     * @return
     */
    public static void writeBoolean(ByteBuffer data, boolean b) {
        if (b) {
            data.put((byte) 1);
        } else {
            data.put((byte) 0);
        }
    }

    /**
     * 读boolean
     *
     * @return
     */
    public static boolean readBoolean(ByteBuffer data) {
        return data.get() == 1;
    }

    public static String readString(ByteBuffer data) {
        byte[] dst = new byte[data.limit() - data.position() - 4];
        data.get(dst);
        try {
            return new String(dst, "UTF8");//116 114 117 101
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new String(dst);
        }

    }

    /**
     * 读取一个数据包
     *
     * @return
     * @throws IOException
     */
    public static ByteBuffer readPacket(DataInputStream dis) throws IOException {
        short size = dis.readShort();
        byte[] buf = new byte[size - 2];
        dis.read(buf);
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        byteBuffer.putShort(size);
        byteBuffer.put(buf);
        byteBuffer.flip();
        return byteBuffer;
    }

    /**
     * 读取一个数据包
     *
     * @return
     * @throws IOException
     */
    public static ByteBuffer readPacket(InputStream is) throws IOException {
        return readPacket(new DataInputStream(is));
    }

    /**
     * 读取指定类型的数据包，其它数据包丢弃
     *
     * @return
     * @throws IOException
     */
    public static ByteBuffer readPacket(InputStream is, byte protocolType) throws IOException {
        ByteBuffer buf;
        do {
            buf = ByteUtil.readPacket(is);
        } while (buf.get(2) != protocolType);
        return buf;
    }

    /**
     * 写一个最大长度为32767的string
     *
     * @param data
     * @param str
     */
    public static void writeShortString(ByteBuffer data, String str) {
        if (null != str) {
            byte[] bytStr = null;
            try {
                bytStr = str.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            data.put(ByteUtil.writeIntToUnSignByte(bytStr.length));
            data.putShort((short) bytStr.length);
            data.put(bytStr);
        } else {
            data.putShort((short) 0);
        }
    }

    /**
     * @param data
     * @return
     */
    public static String readShortString(ByteBuffer data) {
        short length = data.getShort();
        if (length == 0) {
            return null;
        } else {
            byte[] byteData = new byte[length];//读取数据长度
            data.get(byteData);
            try {
                return new String(byteData, "utf-8");
            } catch (UnsupportedEncodingException e) {
                return new String("");
            }
        }
    }

    /**
     * @param data
     * @return
     */
    public static Vector<String> readVecStrs(ByteBuffer data) {
        Vector<String> vecStrs = new Vector<String>();
        int nCount = data.getInt();
        for (int i = 0; i < nCount; i++) {
            //			vecStrs.push_back(ByteUtil.read256String(data));
            vecStrs.add(ByteUtil.read256String(data));
        }
        return vecStrs;
    }

    public static void writeVecStrs(ByteBuffer data, Vector<String> vecStrs) {
        data.putInt(vecStrs.size());
        for (int i = 0; i < vecStrs.size(); i++) {
            //			ByteUtil.write256String(data,vecStrs[i]);
            ByteUtil.write256String(data, vecStrs.get(i));
        }
    }

}
