package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 人员信息
 */
@Entity
public class PersonBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    @Unique
    //人员编号
    private String personNo;
    //人员姓名
    private String personName;
    //员工编号
    private String number;
    //员工部门或者公司
    private String org;
    //是否是白名单
    private boolean white;
    //1为人，2为部门卡
    private int personClass;

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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public int getPersonClass() {
        return personClass;
    }

    public void setPersonClass(int personClass) {
        this.personClass = personClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.primaryId);
        dest.writeString(this.personNo);
        dest.writeString(this.personName);
        dest.writeString(this.number);
        dest.writeString(this.org);
        dest.writeByte(this.white ? (byte) 1 : (byte) 0);
        dest.writeInt(this.personClass);
    }

    public boolean getWhite() {
        return this.white;
    }

    public PersonBean() {
    }

    protected PersonBean(Parcel in) {
        this.primaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.personNo = in.readString();
        this.personName = in.readString();
        this.number = in.readString();
        this.org = in.readString();
        this.white = in.readByte() != 0;
        this.personClass = in.readInt();
    }

    @Generated(hash = 525076266)
    public PersonBean(Long primaryId, @NotNull String personNo, String personName, String number,
            String org, boolean white, int personClass) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.personName = personName;
        this.number = number;
        this.org = org;
        this.white = white;
        this.personClass = personClass;
    }

    public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
        @Override
        public PersonBean createFromParcel(Parcel source) {
            return new PersonBean(source);
        }

        @Override
        public PersonBean[] newArray(int size) {
            return new PersonBean[size];
        }
    };
}
