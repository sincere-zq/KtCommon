package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/5/10.
 */

public class UserStatusExitPacket extends Packet<UserStatusExitPacket> {
    public String userNum;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userNum);
    }

    protected void readData() {
        this.userNum = ByteUtil.read256String(this.data);
    }

    public UserStatusExitPacket() {
        this.type = 1142;
    }

    public UserStatusExitPacket(String userNum) {
        this.userNum = userNum;
        this.type = 1142;
    }
}
