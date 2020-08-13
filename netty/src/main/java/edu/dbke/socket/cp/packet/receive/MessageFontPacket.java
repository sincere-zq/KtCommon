package edu.dbke.socket.cp.packet.receive;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.util.ByteUtil;

/**
 * Created by Zhangs on 2017/3/31.
 */


public class MessageFontPacket extends Packet<MessageFontPacket> {
    public String userNum;
    public String font;
    public byte fontSize;
    public boolean fontBold;
    public boolean fontItalic;
    public boolean fontUnderline;
    public int fontColor;

    protected void writeData() {
        ByteUtil.write256String(this.data, this.userNum);
        ByteUtil.write256String(this.data, this.font);
        this.data.put(this.fontSize);
        ByteUtil.writeBoolean(this.data, this.fontBold);
        ByteUtil.writeBoolean(this.data, this.fontItalic);
        ByteUtil.writeBoolean(this.data, this.fontUnderline);
        this.data.putInt(this.fontColor);
    }

    protected void readData() {
        this.userNum = ByteUtil.read256String(this.data);
        this.font = ByteUtil.read256String(this.data);
        this.fontSize = this.data.get();
        this.fontBold = ByteUtil.readBoolean(this.data);
        this.fontItalic = ByteUtil.readBoolean(this.data);
        this.fontUnderline = ByteUtil.readBoolean(this.data);
        this.fontColor = this.data.getInt();
    }

    public static int setRGB(int r, int g, int b) {
        r <<= 16;
        g <<= 8;
        int rgb = r | g | b;
        return rgb;
    }

    public static String getRGB(int rgb) {
        String str = "";
        str = str + ((rgb & 16711680) >> 16);
        str = str + (rgb & 255);
        str = str + (rgb & 255);
        return str;
    }

    public MessageFontPacket() {
        this.type = 1208;
    }

    public MessageFontPacket(String userNum, byte status, String statusDesc) {
        this.userNum = userNum;
        this.type = 1208;
    }

    public static void main(String[] args) {
        System.out.println(setRGB(15, 15, 15));
        System.out.println(getRGB(986895));
    }
}
