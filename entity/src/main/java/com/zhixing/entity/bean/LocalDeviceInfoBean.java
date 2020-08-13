package com.zhixing.entity.bean;


import android.content.Context;


import com.zhixing.entity.utils.IPUtils;
import com.zhixing.entity.utils.ObjectWriter;
import com.zhixing.entity.utils.VersionUtils;

import java.io.Serializable;

/**
 * 设备信息
 */
public class LocalDeviceInfoBean implements Serializable {
    //软件版本
    private String version;
    //机器IP
    private String ip;
    //机器序列号
    private String sn;
    //本地人员数
    private long persons;
    //本地白名单数
    private long whitePersons;
    //本地人脸数
    private long faceNum;
    //本地指纹数
    private long fingerNum;
    //本地IC卡数量
    private long icNum;
    //识别记录条数
    private long recogeRecordNumber;


    private static LocalDeviceInfoBean localDeviceInfo = null;

    public long getPersons() {
        return persons;
    }

    public void setPersons(long persons) {
        this.persons = persons;
    }

    private LocalDeviceInfoBean(Context context) {
        version = VersionUtils.getVerName(context.getApplicationContext());
        ip = IPUtils.getHostIP();
        DeviceTypeBean deviceType = (DeviceTypeBean) ObjectWriter.read(context.getApplicationContext(), "DeviceType");
        sn = deviceType.uuid;
    }

    public synchronized static LocalDeviceInfoBean getInstance(Context context) {
        if (localDeviceInfo == null) {
            localDeviceInfo = new LocalDeviceInfoBean(context);
        }
        return localDeviceInfo;
    }

    public void clear() {
        recogeRecordNumber = 0;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getFaceNum() {
        return faceNum;
    }

    public void setFaceNum(long faceNum) {
        this.faceNum = faceNum;
    }

    public long getFingerNum() {
        return fingerNum;
    }

    public void setFingerNum(long fingerNum) {
        this.fingerNum = fingerNum;
    }

    public long getIcNum() {
        return icNum;
    }

    public void setIcNum(long icNum) {
        this.icNum = icNum;
    }

    public long getRecogeRecordNumber() {
        return recogeRecordNumber;
    }

    public void setRecogeRecordNumber(long recogeRecordNumber) {
        this.recogeRecordNumber = recogeRecordNumber;
    }

    public long getWhitePersons() {
        return whitePersons;
    }

    public void setWhitePersons(long whitePersons) {
        this.whitePersons = whitePersons;
    }
}
