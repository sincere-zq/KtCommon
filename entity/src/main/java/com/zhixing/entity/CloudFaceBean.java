package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 云从人脸信息
 */
@Entity
public class CloudFaceBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;
    @NotNull
    //人员编号
    private String personNo;
    @NotNull
    @Unique
    //人脸特征路径
    private String featurePath;

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getFeaturePath() {
        return featurePath;
    }

    public void setFeaturePath(String featurePath) {
        this.featurePath = featurePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.primaryId);
        dest.writeString(this.personNo);
        dest.writeString(this.featurePath);
    }

    public Long getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public CloudFaceBean() {
    }

    protected CloudFaceBean(Parcel in) {
        this.primaryId = (Long) in.readValue(Long.class.getClassLoader());
        this.personNo = in.readString();
        this.featurePath = in.readString();
    }

    @Generated(hash = 647591705)
    public CloudFaceBean(Long primaryId, @NotNull String personNo, @NotNull String featurePath) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.featurePath = featurePath;
    }

    public static final Creator<CloudFaceBean> CREATOR = new Creator<CloudFaceBean>() {
        @Override
        public CloudFaceBean createFromParcel(Parcel source) {
            return new CloudFaceBean(source);
        }

        @Override
        public CloudFaceBean[] newArray(int size) {
            return new CloudFaceBean[size];
        }
    };
}
