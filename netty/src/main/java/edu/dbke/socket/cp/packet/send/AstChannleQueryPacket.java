package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class AstChannleQueryPacket extends Packet<AstChannleQueryPacket> {
    public String caller;
    public String called;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.caller);
        ByteUtil.write256String(this.data, this.called);
    }

    protected void readData() {
        this.caller = ByteUtil.read256String(this.data);
        this.called = ByteUtil.read256String(this.data);
    }

    public AstChannleQueryPacket() {
        this.type = 1130;
    }

    public AstChannleQueryPacket(String caller, String called) {
        this.caller = caller;
        this.called = called;
        this.type = 1130;
    }
}