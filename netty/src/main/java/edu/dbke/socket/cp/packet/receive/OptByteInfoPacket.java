package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/5/2.
 */


public class OptByteInfoPacket extends Packet<OptByteInfoPacket> {
    public String optId;
    public byte optType;
    public byte optResult;
    public String optInfo;
    public static final byte TYPE_ADD = 1;
    public static final byte TYPE_UPDATE = 2;
    public static final byte TYPE_DELETE = 3;
    public static final byte TYPE_QUERY = 4;
    protected void writeData() {
        ByteUtil.write256String(this.data, this.optId);
        this.data.put(this.optType);
        this.data.put(this.optResult);
        ByteUtil.writeShortString(this.data, this.optInfo);
    }

    protected void readData() {
        this.optId = ByteUtil.read256String(this.data);
        this.optType = this.data.get();
        this.optResult = this.data.get();
        this.optInfo = ByteUtil.readShortString(this.data);
    }

    public OptByteInfoPacket() {
    }

    public OptByteInfoPacket(short type) {
        this.type = type;
    }

    public OptByteInfoPacket(short type, String optId) {
        this.type = type;
        this.optId = optId;
    }

    public OptByteInfoPacket(short type, String optId, byte optResult) {
        this.type = type;
        this.optId = optId;
        this.optResult = optResult;
    }

    public OptByteInfoPacket(short type, String optId, byte optResult, String optInfo) {
        this.type = type;
        this.optId = optId;
        this.optResult = optResult;
        this.optInfo = optInfo;
    }

    @Override
    public String toString() {
        return "OptByteInfoPacket{" +
                "optId='" + optId + '\'' +
                ", optType=" + optType +
                ", optResult=" + optResult +
                ", optInfo='" + optInfo + '\'' +
                '}';
    }
}
