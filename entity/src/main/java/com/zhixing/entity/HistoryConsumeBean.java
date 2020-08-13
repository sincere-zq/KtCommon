package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * 消费记录
 */
@Entity
public class HistoryConsumeBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    private String consumeId;
    @NotNull
    //人员编号
    private String personNo;
    @NotNull
    private String code;
    @NotNull
    private String org;
    @NotNull
    private String personName;
    @NotNull
    //消费类型
    private int consumeType;
    @NotNull
    //金额
    private int monetary;
    //识别方式  1人脸 2卡 3指纹
    private int identifyType;
    @NotNull
    //消费时间
    private Date date;

    private int personClass;

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public int getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(int consumeType) {
        this.consumeType = consumeType;
    }

    public int getMonetary() {
        return monetary;
    }

    public void setMonetary(int monetary) {
        this.monetary = monetary;
    }

    public int getIdentifyType() {
        return identifyType;
    }

    public void setIdentifyType(int identifyType) {
        this.identifyType = identifyType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPersonClass() {
        return personClass;
    }

    public void setPersonClass(int personClass) {
        this.personClass = personClass;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.primaryId);
        dest.writeString(this.consumeId);
        dest.writeString(this.personNo);
        dest.writeString(this.code);
        dest.writeString(this.org);
        dest.writeString(this.personName);
        dest.writeInt(this.consumeType);
        dest.writeInt(this.monetary);
        dest.writeInt(this.identifyType);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.personClass);
    }

    public HistoryConsumeBean() {
    }

    protected HistoryConsumeBean(Parcel in) {
        this.primaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.consumeId = in.readString();
        this.personNo = in.readString();
        this.code = in.readString();
        this.org = in.readString();
        this.personName = in.readString();
        this.consumeType = in.readInt();
        this.monetary = in.readInt();
        this.identifyType = in.readInt();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.personClass = in.readInt();
    }

    @Generated(hash = 1449512779)
    public HistoryConsumeBean(Long primaryId, @NotNull String consumeId, @NotNull String personNo, @NotNull String code,
            @NotNull String org, @NotNull String personName, int consumeType, int monetary, int identifyType,
            @NotNull Date date, int personClass) {
        this.primaryId = primaryId;
        this.consumeId = consumeId;
        this.personNo = personNo;
        this.code = code;
        this.org = org;
        this.personName = personName;
        this.consumeType = consumeType;
        this.monetary = monetary;
        this.identifyType = identifyType;
        this.date = date;
        this.personClass = personClass;
    }

    public static final Creator<HistoryConsumeBean> CREATOR = new Creator<HistoryConsumeBean>() {
        @Override
        public HistoryConsumeBean createFromParcel(Parcel source) {
            return new HistoryConsumeBean(source);
        }

        @Override
        public HistoryConsumeBean[] newArray(int size) {
            return new HistoryConsumeBean[size];
        }
    };
}
