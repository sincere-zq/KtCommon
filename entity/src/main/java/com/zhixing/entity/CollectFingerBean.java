package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * 采集人员指纹
 */
@Entity
public class CollectFingerBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    //人员编号
    private String personNo;
    @NotNull
    //人员指纹特征路径
    private String fingerPath;

    private String msg;

    private boolean upload;

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getFingerPath() {
        return fingerPath;
    }

    public void setFingerPath(String fingerPath) {
        this.fingerPath = fingerPath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.primaryId);
        dest.writeString(this.personNo);
        dest.writeString(this.fingerPath);
        dest.writeString(this.msg);
        dest.writeByte(this.upload ? (byte) 1 : (byte) 0);
    }

    public Long getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public boolean getUpload() {
        return this.upload;
    }

    public CollectFingerBean() {
    }

    protected CollectFingerBean(Parcel in) {
        this.primaryId = in.readLong();
        this.personNo = in.readString();
        this.fingerPath = in.readString();
        this.msg = in.readString();
        this.upload = in.readByte() != 0;
    }

    @Generated(hash = 653608952)
    public CollectFingerBean(Long primaryId, @NotNull String personNo, @NotNull String fingerPath, String msg,
            boolean upload) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.fingerPath = fingerPath;
        this.msg = msg;
        this.upload = upload;
    }

    public static final Creator<CollectFingerBean> CREATOR = new Creator<CollectFingerBean>() {
        @Override
        public CollectFingerBean createFromParcel(Parcel source) {
            return new CollectFingerBean(source);
        }

        @Override
        public CollectFingerBean[] newArray(int size) {
            return new CollectFingerBean[size];
        }
    };
}
