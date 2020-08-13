package cn.cloudwalk.usbdriver;

import android.hardware.usb.UsbDevice;


import java.util.List;

/**
 * create by 曾强 on 2020/3/23
 */
public interface UsbSerialDriver {

    /**
     * Returns the raw {@link UsbDevice} backing this port.
     *
     * @return the device
     */
    public UsbDevice getDevice();

    /**
     * Returns all available ports for this device. This list must have at least
     * one entry.
     *
     * @return the ports
     */
    public List<UsbSerialPort> getPorts();
}
