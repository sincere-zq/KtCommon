package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 采集人员表
 */
@Entity
public class CollectPersonBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    @Unique
    //人员编号
    private String personNo;
    @NotNull
    //工号
    private String code;
    @NotNull
    //人员姓名
    @SerializedName("name")
    private String personName;
    @NotNull
    //手机号
    @SerializedName("mobile")
    private String phone;
    //是否有人脸
    @SerializedName("faceFeature")
    private int hasFace;
    //是否有ic卡
    @SerializedName("icCardFeature")
    private int hasIc;
    //是否有指纹
    @SerializedName("fingerFeature")
    private int hasFinger;
    //是否有指纹2
    @SerializedName("fingerFeature2")
    private int hasFinger2;

    //1为人，2为部门卡
    private int personClass;

    private String org;

    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public int getPersonClass() {
        return personClass;
    }

    public void setPersonClass(int personClass) {
        this.personClass = personClass;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isHasFace() {
        return hasFace == 1;
    }

    public void setHasFace(boolean hasFace) {
        this.hasFace = hasFace ? 1 : 0;
    }

    public boolean isHasIc() {
        return hasIc == 1;
    }

    public void setHasIc(boolean hasIc) {
        this.hasIc = hasIc ? 1 : 0;
    }

    public int getHasFinger() {
        return hasFinger;
    }

    public boolean isHasFinger() {
        return hasFinger == 1;
    }

    public void setHasFinger(boolean hasFinger) {
        this.hasFinger = hasFinger ? 1 : 0;
    }

    public boolean isHasFinger2() {
        return hasFinger2 == 1;
    }

    public int getHasFinger2() {
        return hasFinger2;
    }

    public void setHasFinger2(boolean hasFinger2) {
        this.hasFinger2 = hasFinger2 ? 1 : 0;
    }

    public Long getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public int getHasFace() {
        return this.hasFace;
    }

    public void setHasFace(int hasFace) {
        this.hasFace = hasFace;
    }

    public int getHasIc() {
        return this.hasIc;
    }

    public void setHasIc(int hasIc) {
        this.hasIc = hasIc;
    }

    public void setHasFinger(int hasFinger) {
        this.hasFinger = hasFinger;
    }

    public void setHasFinger2(int hasFinger2) {
        this.hasFinger2 = hasFinger2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.primaryId);
        dest.writeString(this.personNo);
        dest.writeString(this.code);
        dest.writeString(this.personName);
        dest.writeString(this.phone);
        dest.writeInt(this.hasFace);
        dest.writeInt(this.hasIc);
        dest.writeInt(this.hasFinger);
        dest.writeInt(this.hasFinger2);
        dest.writeInt(this.personClass);
        dest.writeString(this.org);
    }

    public CollectPersonBean() {
    }

    protected CollectPersonBean(Parcel in) {
        this.primaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.personNo = in.readString();
        this.code = in.readString();
        this.personName = in.readString();
        this.phone = in.readString();
        this.hasFace = in.readInt();
        this.hasIc = in.readInt();
        this.hasFinger = in.readInt();
        this.hasFinger2 = in.readInt();
        this.personClass = in.readInt();
        this.org = in.readString();
    }

    @Generated(hash = 595604174)
    public CollectPersonBean(Long primaryId, @NotNull String personNo, @NotNull String code, @NotNull String personName, @NotNull String phone,
            int hasFace, int hasIc, int hasFinger, int hasFinger2, int personClass, String org, String errorMsg) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.code = code;
        this.personName = personName;
        this.phone = phone;
        this.hasFace = hasFace;
        this.hasIc = hasIc;
        this.hasFinger = hasFinger;
        this.hasFinger2 = hasFinger2;
        this.personClass = personClass;
        this.org = org;
        this.errorMsg = errorMsg;
    }

    public static final Creator<CollectPersonBean> CREATOR = new Creator<CollectPersonBean>() {
        @Override
        public CollectPersonBean createFromParcel(Parcel source) {
            return new CollectPersonBean(source);
        }

        @Override
        public CollectPersonBean[] newArray(int size) {
            return new CollectPersonBean[size];
        }
    };
}
