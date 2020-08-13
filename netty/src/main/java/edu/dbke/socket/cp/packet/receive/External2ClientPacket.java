package edu.dbke.socket.cp.packet.receive;

import java.util.Arrays;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/4/26.
 */


public class External2ClientPacket extends Packet<External2ClientPacket> {
    public String target;
    public byte[] bytesData;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.target);
        this.data.put(this.bytesData);
    }

    protected void readData() {
        this.target = ByteUtil.read256String(this.data);
        this.bytesData = new byte[this.data.limit() - this.data.position()];
        this.data.get(this.bytesData);
    }

    public External2ClientPacket() {
        this.type = 22;
    }

    public External2ClientPacket(String target, byte[] bytesData) {
        this.target = target;
        this.bytesData = bytesData;
        this.type = 22;
    }

    @Override
    public String toString() {
        return "External2ClientPacket{" +
                "target='" + target + '\'' +
                ", bytesData=" + Arrays.toString(bytesData) +
                '}';
    }

    public int getPrivateType() {
        return data.getShort();
    }
}
