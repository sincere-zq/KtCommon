package edu.dbke.socket.cp.packet;

import edu.dbke.socket.cp.Packet;

/**
 * Created by Zhangs on 2017/3/20.
 */

public class EmptyPacket extends Packet<EmptyPacket> {
    public EmptyPacket() {
    }

    @Override
    protected void writeData() {

    }

    @Override
    protected void readData() {

    }

    public EmptyPacket(short type) {
        this.type = type;
    }

}
