package edu.dbke.socket.cp.packet;

import edu.dbke.socket.cp.Packet;

/**
 * Created by Zhangs on 2017/3/13.
 */


public abstract class AbstractPacket<T> extends Packet<T> {
    public AbstractPacket() {
    }

    protected void writeData() {
    }

    protected void readData() {
    }
}
