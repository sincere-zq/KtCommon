package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 本地识别记录
 */
@Entity
public class RecogeRecordBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    //识别方式类型 1、人脸 2、卡 3、指纹
    private int identifyType;
    //识别时间
    private Date recogTime;
    //是否开门 0 非白名单未开门 1 白名单开门
    private int recogeType;
    //人员编号
    private String personNo;
    //员工编号
    private String code;
    //公司、部门
    private String org;
    //人员名称
    private String personName;
    //是否是部门卡
    private int personClass;
    @NotNull
    @Unique
    private String identifyId;

    public int getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(int identifyType) {
        this.identifyType = identifyType;
    }

    public Date getRecogTime() {
        return recogTime;
    }

    public void setRecogTime(Date recogTime) {
        this.recogTime = recogTime;
    }

    public int getRecogeType() {
        return recogeType;
    }

    public void setRecogeType(int recogeType) {
        this.recogeType = recogeType;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonClass() {
        return personClass;
    }

    public void setPersonClass(int personClass) {
        this.personClass = personClass;
    }

    public Long getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public String getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.primaryId);
        dest.writeInt(this.identifyType);
        dest.writeLong(this.recogTime != null ? this.recogTime.getTime() : -1);
        dest.writeInt(this.recogeType);
        dest.writeString(this.personNo);
        dest.writeString(this.code);
        dest.writeString(this.org);
        dest.writeString(this.personName);
        dest.writeInt(this.personClass);
        dest.writeString(this.identifyId);
    }

    public RecogeRecordBean() {
    }

    protected RecogeRecordBean(Parcel in) {
        this.primaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.identifyType = in.readInt();
        long tmpRecogTime = in.readLong();
        this.recogTime = tmpRecogTime == -1 ? null : new Date(tmpRecogTime);
        this.recogeType = in.readInt();
        this.personNo = in.readString();
        this.code = in.readString();
        this.org = in.readString();
        this.personName = in.readString();
        this.personClass = in.readInt();
        this.identifyId = in.readString();
    }

    @Generated(hash = 625086924)
    public RecogeRecordBean(Long primaryId, int identifyType, Date recogTime, int recogeType, String personNo, String code,
            String org, String personName, int personClass, @NotNull String identifyId) {
        this.primaryId = primaryId;
        this.identifyType = identifyType;
        this.recogTime = recogTime;
        this.recogeType = recogeType;
        this.personNo = personNo;
        this.code = code;
        this.org = org;
        this.personName = personName;
        this.personClass = personClass;
        this.identifyId = identifyId;
    }

    public static final Parcelable.Creator<RecogeRecordBean> CREATOR = new Parcelable.Creator<RecogeRecordBean>() {
        @Override
        public RecogeRecordBean createFromParcel(Parcel source) {
            return new RecogeRecordBean(source);
        }

        @Override
        public RecogeRecordBean[] newArray(int size) {
            return new RecogeRecordBean[size];
        }
    };
}
