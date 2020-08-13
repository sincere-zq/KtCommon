package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 旷视人脸信息
 */
@Entity
public class PassFaceBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    //人员编号
    private String personNo;
    //服务器传的faceId
    @NotNull
    @Unique
    private String faceId;
    @NotNull
    @Unique
    //人脸特征路径
    private String faceToken;

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.primaryId);
        dest.writeString(this.personNo);
        dest.writeString(this.faceId);
        dest.writeString(this.faceToken);
    }

    public Long getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public PassFaceBean() {
    }

    protected PassFaceBean(Parcel in) {
        this.primaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.personNo = in.readString();
        this.faceId = in.readString();
        this.faceToken = in.readString();
    }

    @Generated(hash = 1059394136)
    public PassFaceBean(Long primaryId, @NotNull String personNo, @NotNull String faceId,
            @NotNull String faceToken) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.faceId = faceId;
        this.faceToken = faceToken;
    }

    public static final Creator<PassFaceBean> CREATOR = new Creator<PassFaceBean>() {
        @Override
        public PassFaceBean createFromParcel(Parcel source) {
            return new PassFaceBean(source);
        }

        @Override
        public PassFaceBean[] newArray(int size) {
            return new PassFaceBean[size];
        }
    };
}
