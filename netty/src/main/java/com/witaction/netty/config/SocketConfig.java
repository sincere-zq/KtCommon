package com.witaction.netty.config;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhangs on 2017/3/14.
 */

public class SocketConfig implements Parcelable {
    private boolean supportReconnect;//是否支持断线重连
    private int reconnectTime;//重连次数,若重连次数为CONNECT_FOREVER,则会一直请求重连；
    private String host;
    private int port;
    private long connectDelay;//断线重连的延迟
    private boolean reSendData;//是否支持失败重发

    public SocketConfig(String host, int port, boolean reconnect, int reconnectTime, long connectDelay, boolean reSend) {
        this.supportReconnect = reconnect;
        this.reconnectTime = reconnectTime;
        this.host = host;
        this.port = port;
        this.connectDelay = connectDelay;
        this.reSendData = reSend;
    }

    public boolean isSupportReconnect() {
        return supportReconnect;
    }

    public int getReconnectTime() {
        return reconnectTime;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public long getConnectDelay() {
        return connectDelay;
    }

    public boolean isReSendData() {
        return reSendData;
    }

    public void setSupportReconnect(boolean supportReconnect) {
        this.supportReconnect = supportReconnect;
    }

    public void setReconnectTime(int reconnectTime) {
        this.reconnectTime = reconnectTime;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setConnectDelay(long connectDelay) {
        this.connectDelay = connectDelay;
    }

    public void setReSendData(boolean reSendData) {
        this.reSendData = reSendData;
    }

    public static class Builder {
        private boolean supportReConnect;
        private int reconnectTime;
        private String host;
        private int port;
        private long connectDelay;
        private boolean resendData;

        public Builder setSupportReConnect(boolean supportReConnect) {
            this.supportReConnect = supportReConnect;
            return this;
        }

        public Builder setReconnectTime(int reconnectTime) {
            this.reconnectTime = reconnectTime;
            return this;
        }

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setConnectDelay(long connectDelay) {
            this.connectDelay = connectDelay;
            return this;
        }

        @Deprecated
        public Builder setResendData(boolean resendData) {
            this.resendData = resendData;
            return this;
        }

        public SocketConfig build() {
            SocketConfig config = new SocketConfig(this.host, this.port, this.supportReConnect, this.reconnectTime, this.connectDelay, this.resendData);
            return config;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.supportReconnect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.reconnectTime);
        dest.writeString(this.host);
        dest.writeInt(this.port);
        dest.writeLong(this.connectDelay);
        dest.writeByte(this.reSendData ? (byte) 1 : (byte) 0);
    }

    protected SocketConfig(Parcel in) {
        this.supportReconnect = in.readByte() != 0;
        this.reconnectTime = in.readInt();
        this.host = in.readString();
        this.port = in.readInt();
        this.connectDelay = in.readLong();
        this.reSendData = in.readByte() != 0;
    }

    public static final Creator<SocketConfig> CREATOR = new Creator<SocketConfig>() {
        @Override
        public SocketConfig createFromParcel(Parcel source) {
            return new SocketConfig(source);
        }

        @Override
        public SocketConfig[] newArray(int size) {
            return new SocketConfig[size];
        }
    };
}
