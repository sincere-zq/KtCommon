package com.aill.androidserialport;

import java.io.FileDescriptor;

/**
 * @description 串口操作类
 */
public class SerialPort {


    static {
        System.loadLibrary("android_serial_port");
    }

    /**
     * 打开串口
     *
     * @param path     设备路径
     * @param baudrate 波特率
     * @param flags
     * @return FileDescriptor
     */
    public native static FileDescriptor open(String path, int baudrate, int flags);

    /**
     * 关闭串口
     */
    public native void close();

}
