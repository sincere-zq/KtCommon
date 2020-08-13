package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/9.
 * 账号验证
 */


public class AccountCheckPacket extends Packet<AccountCheckPacket> {
    public String username;
    public String password;
    public byte targetType;
    public byte optResult;
    public String optInfo;
    public static final byte RESULT_OK = 0;
    public static final byte RESULT_ERROR = 1;
    public static final byte TYPE_UNUSED = 0;
    public static final byte TYPE_USER = 1;
    public static final byte TYPE_SIP = 2;
    public static final byte TYPE_ORG = 3;
    public static final byte TYPE_PERSON = 4;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.username);
        ByteUtil.write256String(this.data, this.password);
        this.data.put(this.targetType);
        this.data.put(this.optResult);
        ByteUtil.writeShortString(this.data, this.optInfo);
    }

    protected void readData() {
        this.username = ByteUtil.read256String(this.data);
        this.password = ByteUtil.read256String(this.data);
        this.targetType = this.data.get();
        this.optResult = this.data.get();
        this.optInfo = ByteUtil.readShortString(this.data);
    }

    public AccountCheckPacket() {
    }

    public AccountCheckPacket(short type) {
        this.type = type;
    }

    public AccountCheckPacket(short type, String username) {
        this.type = type;
        this.username = username;
    }

    public AccountCheckPacket(short type, String username, byte optResult) {
        this.type = type;
        this.username = username;
        this.optResult = optResult;
    }

    public AccountCheckPacket(short type, String username, byte optResult, String optInfo) {
        this.type = type;
        this.username = username;
        this.optResult = optResult;
        this.optInfo = optInfo;
    }

    @Override
    public String toString() {
        return "AccountCheckPacket{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", targetType=" + targetType +
                ", optResult=" + optResult +
                ", optInfo='" + optInfo + '\'' +
                '}';
    }
}
