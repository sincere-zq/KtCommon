package com.zhixing.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 更新信息
 */
public class UpDateBean implements Serializable {
    public int IsSuccess;
    public String ErrorCode;
    public int Count;
    public List<UpdateData> Data = new ArrayList<>();

    @Override
    public String toString() {
        return "UpDateBean{" +
                "IsSuccess=" + IsSuccess +
                ", ErrorCode=" + ErrorCode +
                ", Count=" + Count +
                ", Data=" + Data +
                '}';
    }

    public static class UpdateData implements Serializable {
        public String UID;
        public String Title;
        public String Content;
        public String ClientVer;
        public int ForOs;
        public String PacketPath;
        public long PacketSize;
        public String PacketName;
        public String CreateTime;
        public String Remark;

        @Override
        public String toString() {
            return "UpdateData{" +
                    "UID='" + UID + '\'' +
                    ", Title='" + Title + '\'' +
                    ", Content='" + Content + '\'' +
                    ", ClientVer='" + ClientVer + '\'' +
                    ", ForOs=" + ForOs +
                    ", PacketPath='" + PacketPath + '\'' +
                    ", PacketSize=" + PacketSize +
                    ", PacketName='" + PacketName + '\'' +
                    ", CreateTime='" + CreateTime + '\'' +
                    ", Remark='" + Remark + '\'' +
                    '}';
        }
    }
}
