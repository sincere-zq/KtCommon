package edu.dbke.socket.cp.packet.send;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/5/2.
 */


public class ContactListPacket extends Packet<ContactListPacket> {
    public static final byte TARGET_TYPE_ORG = 1;
    public static final byte TARGET_TYPE_USER = 3;
    public static final byte TARGET_TYPE_PERSON = 2;
    public String uid;
    public String groupName;
    public String personName;
    public String photoId;
    public String fax;
    public String sipAccount;
    public String cellphone;
    public String officePhone;
    public String telephone;
    public String defaultNumber;
    public String targetId;
    public byte targetType;
    public String optId;
    public String optFiled;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.uid);
        ByteUtil.write256String(this.data, this.groupName);
        ByteUtil.write256String(this.data, this.personName);
        ByteUtil.write256String(this.data, this.photoId);
        ByteUtil.write256String(this.data, this.fax);
        ByteUtil.write256String(this.data, this.sipAccount);
        ByteUtil.write256String(this.data, this.cellphone);
        ByteUtil.write256String(this.data, this.officePhone);
        ByteUtil.write256String(this.data, this.telephone);
        ByteUtil.write256String(this.data, this.defaultNumber);
        ByteUtil.write256String(this.data, this.targetId);
        this.data.put(this.targetType);
        ByteUtil.write256String(this.data, this.optId);
        ByteUtil.writeShortString(this.data, this.optFiled);
    }

    protected void readData() {
        this.uid = ByteUtil.read256String(this.data);
        this.groupName = ByteUtil.read256String(this.data);
        this.personName = ByteUtil.read256String(this.data);
        this.photoId = ByteUtil.read256String(this.data);
        this.fax = ByteUtil.read256String(this.data);
        this.sipAccount = ByteUtil.read256String(this.data);
        this.cellphone = ByteUtil.read256String(this.data);
        this.officePhone = ByteUtil.read256String(this.data);
        this.telephone = ByteUtil.read256String(this.data);
        this.defaultNumber = ByteUtil.read256String(this.data);
        this.targetId = ByteUtil.read256String(this.data);
        this.targetType = this.data.get();
        this.optId = ByteUtil.read256String(this.data);
        this.optFiled = ByteUtil.readShortString(this.data);
    }

    public ContactListPacket() {
        this.type = 1195;
    }

    public ContactListPacket(short type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ContactListPacket{" +
                "uid='" + uid + '\'' +
                ", groupName='" + groupName + '\'' +
                ", personName='" + personName + '\'' +
                ", photoId='" + photoId + '\'' +
                ", fax='" + fax + '\'' +
                ", sipAccount='" + sipAccount + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", officePhone='" + officePhone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", defaultNumber='" + defaultNumber + '\'' +
                ", targetId='" + targetId + '\'' +
                ", targetType=" + targetType +
                ", optId='" + optId + '\'' +
                ", optFiled='" + optFiled + '\'' +
                '}';
    }
}