package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class OptStringPacket extends Packet<OptStringPacket> {
    public String optId;
    public String optData;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.optId);
        ByteUtil.writeShortString(this.data, this.optData);
    }

    protected void readData() {
        this.optId = ByteUtil.read256String(this.data);
        this.optData = ByteUtil.readShortString(this.data);
    }

    public OptStringPacket() {
    }

    public OptStringPacket(short type) {
        this.type = type;
    }

    public OptStringPacket(short type, String optId) {
        this.type = type;
        this.optId = optId;
    }

    public OptStringPacket(short type, String optId, String optData) {
        this.type = type;
        this.optId = optId;
        this.optData = optData;
    }

    @Override
    public String toString() {
        return "OptStringPacket{" +
                "optId='" + optId + '\'' +
                ", optData='" + optData + '\'' +
                '}';
    }
}
