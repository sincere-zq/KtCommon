package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */

public class DesktopSharePacket extends Packet<DesktopSharePacket> {
    public String roomNum;
    public String userNum;
    public boolean isOpen;

    public void writeData() {
        ByteUtil.write256String(this.data, this.roomNum);
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.writeBoolean(this.data, this.isOpen);
    }

    public void readData() {
        this.roomNum = ByteUtil.read256String(this.data);
        this.userNum = ByteUtil.read256String(this.data);
        this.isOpen = ByteUtil.readBoolean(this.data);
    }

    public DesktopSharePacket() {
        this.type = 1281;
    }

    public DesktopSharePacket(String roomNum) {
        this.roomNum = roomNum;
        this.type = 1281;
    }

    @Override
    public String toString() {
        return "DesktopSharePacket{" +
                "roomNum='" + roomNum + '\'' +
                ", userNum='" + userNum + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}