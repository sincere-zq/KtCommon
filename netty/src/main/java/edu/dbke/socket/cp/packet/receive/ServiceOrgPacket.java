package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/13.
 */


public class ServiceOrgPacket extends Packet<ServiceOrgPacket> {
    public String uid;
    public String name;
    public String authCodes;
    public String privateServerIP;
    public String privateServerPort;
    public String publicServerIP;
    public String publicServerPort;
    public String OAServerIP;
    public String pontactAddr;
    public String contactTel;
    public String sipNum;
    public String sipPin;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.uid);
        ByteUtil.write256String(this.data, this.name);
        ByteUtil.write256String(this.data, this.authCodes);
        ByteUtil.write256String(this.data, this.privateServerIP);
        ByteUtil.write256String(this.data, this.privateServerPort);
        ByteUtil.write256String(this.data, this.publicServerIP);
        ByteUtil.write256String(this.data, this.publicServerPort);
        ByteUtil.write256String(this.data, this.OAServerIP);
        ByteUtil.write256String(this.data, this.pontactAddr);
        ByteUtil.write256String(this.data, this.contactTel);
        ByteUtil.write256String(this.data, this.sipNum);
        ByteUtil.write256String(this.data, this.sipPin);
    }

    protected void readData() {
        this.uid = ByteUtil.read256String(this.data);
        this.name = ByteUtil.read256String(this.data);
        this.authCodes = ByteUtil.read256String(this.data);
        this.privateServerIP = ByteUtil.read256String(this.data);
        this.privateServerPort = ByteUtil.read256String(this.data);
        this.publicServerIP = ByteUtil.read256String(this.data);
        this.publicServerPort = ByteUtil.read256String(this.data);
        this.OAServerIP = ByteUtil.read256String(this.data);
        this.pontactAddr = ByteUtil.read256String(this.data);
        this.contactTel = ByteUtil.read256String(this.data);
        this.sipNum = ByteUtil.read256String(this.data);
        this.sipPin = ByteUtil.read256String(this.data);
    }

    public ServiceOrgPacket() {
        this.type = 1298;
    }

    public ServiceOrgPacket(String uid, String name, String authCodes, String privateServerIP, String privateServerPort, String publicServerIP, String publicServerPort, String oAServerIP, String pontactAddr, String contactTel) {
        this.uid = uid;
        this.name = name;
        this.authCodes = authCodes;
        this.privateServerIP = privateServerIP;
        this.privateServerPort = privateServerPort;
        this.publicServerIP = publicServerIP;
        this.publicServerPort = publicServerPort;
        this.OAServerIP = oAServerIP;
        this.pontactAddr = pontactAddr;
        this.contactTel = contactTel;
        this.type = 1298;
    }

    @Override
    public String toString() {
        return "ServiceOrgPacket{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", authCodes='" + authCodes + '\'' +
                ", privateServerIP='" + privateServerIP + '\'' +
                ", privateServerPort='" + privateServerPort + '\'' +
                ", publicServerIP='" + publicServerIP + '\'' +
                ", publicServerPort='" + publicServerPort + '\'' +
                ", OAServerIP='" + OAServerIP + '\'' +
                ", pontactAddr='" + pontactAddr + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", sipNum='" + sipNum + '\'' +
                ", sipPin='" + sipPin + '\'' +
                '}';
    }
}