package edu.dbke.socket.cp.packet;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.UnsupportedEncodingException;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class StringPacket extends Packet<StringPacket> {
    public String dataStr;

    protected void writeData() {
        if (null != this.dataStr) {
            try {
                this.data.put(this.dataStr.getBytes("utf-8"));
            } catch (UnsupportedEncodingException var2) {
                var2.printStackTrace();
            }
        }

    }

    protected void readData() {
        this.dataStr = ByteUtil.readString(this.data);
    }

    public StringPacket() {
    }

    public StringPacket(short type) {
        this.type = type;
    }

    public StringPacket(short type, String str) {
        this.type = type;
        this.dataStr = str;
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(new Object[]{this.dataStr});
    }

    public boolean equals(Object obj) {
        if (obj instanceof StringPacket) {
            StringPacket cast = (StringPacket) obj;
            if (null != this.dataStr && this.dataStr.equals(cast.dataStr)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "StringPacket{" +
                super.toString() +
                "dataStr='" + dataStr + '\'' +
                '}';
    }
}
