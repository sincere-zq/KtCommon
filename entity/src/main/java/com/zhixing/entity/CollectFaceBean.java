package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 采集人脸信息
 */
@Entity
public class CollectFaceBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    @Unique
    //人员编号
    private String personNo;
    @NotNull
    @Unique
    //人脸照片路径
    private String facePath;
    @NotNull
    @Unique
    //人脸特征路径
    private String featurePath;
    //是否上传
    private boolean upload;
    //上传失败信息
    private String msg;

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getFacePath() {
        return facePath;
    }

    public void setFacePath(String facePath) {
        this.facePath = facePath;
    }

    public String getFeaturePath() {
        return featurePath;
    }

    public void setFeaturePath(String featurePath) {
        this.featurePath = featurePath;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.primaryId);
        dest.writeString(this.personNo);
        dest.writeString(this.facePath);
        dest.writeString(this.featurePath);
        dest.writeByte(this.upload ? (byte) 1 : (byte) 0);
        dest.writeString(this.msg);
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

    public CollectFaceBean() {
    }

    protected CollectFaceBean(Parcel in) {
        this.primaryId = in.readLong();
        this.personNo = in.readString();
        this.facePath = in.readString();
        this.featurePath = in.readString();
        this.upload = in.readByte() != 0;
        this.msg = in.readString();
    }

    @Generated(hash = 1444006556)
    public CollectFaceBean(Long primaryId, @NotNull String personNo, @NotNull String facePath,
                           @NotNull String featurePath, boolean upload, String msg) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.facePath = facePath;
        this.featurePath = featurePath;
        this.upload = upload;
        this.msg = msg;
    }

    public static final Creator<CollectFaceBean> CREATOR = new Creator<CollectFaceBean>() {
        @Override
        public CollectFaceBean createFromParcel(Parcel source) {
            return new CollectFaceBean(source);
        }

        @Override
        public CollectFaceBean[] newArray(int size) {
            return new CollectFaceBean[size];
        }
    };
}
