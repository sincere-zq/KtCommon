package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.packet.AbstractPacket;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/31.
 */


public class IpInfoPacket extends AbstractPacket<IpInfoPacket> {
    public String userNum;
    public String ip;
    public String code;
    public String country;
    public String city;
    public String isp;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.write256String(this.data, this.ip);
        ByteUtil.write256String(this.data, this.code);
        ByteUtil.write256String(this.data, this.country);
        ByteUtil.write256String(this.data, this.city);
        ByteUtil.write256String(this.data, this.isp);
    }

    protected void readData() {
        this.userNum = ByteUtil.read256String(this.data);
        this.ip = ByteUtil.read256String(this.data);
        this.code = ByteUtil.read256String(this.data);
        this.country = ByteUtil.read256String(this.data);
        this.city = ByteUtil.read256String(this.data);
        this.isp = ByteUtil.read256String(this.data);
    }

    public IpInfoPacket() {
        this.type = 1374;
    }

    public IpInfoPacket(String userNum, String ip, String code, String country, String city, String isp) {
        this.userNum = userNum;
        this.ip = ip;
        this.code = code;
        this.country = country;
        this.city = city;
        this.isp = isp;
        this.type = 1374;
    }
}