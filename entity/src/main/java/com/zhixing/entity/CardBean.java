package com.zhixing.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * ic卡
 */
@Entity
public class CardBean implements Parcelable {
    @Id(autoincrement = true)
    private Long primaryId;

    @NotNull
    @Unique
    private String personNo;
    
    @NotNull
    @Unique
    private String cardNumber;

    private String msg;

    private boolean upload;

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
        dest.writeString(this.cardNumber);
        dest.writeString(this.msg);
        dest.writeByte(this.upload ? (byte) 1 : (byte) 0);
    }

    public Long getPrimaryId() {
        return this.primaryId;
    }

    public boolean getUpload() {
        return this.upload;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public CardBean() {
    }

    protected CardBean(Parcel in) {
        this.primaryId = in.readLong();
        this.personNo = in.readString();
        this.cardNumber = in.readString();
        this.msg = in.readString();
        this.upload = in.readByte() != 0;
    }

    @Generated(hash = 582444902)
    public CardBean(Long primaryId, @NotNull String personNo, @NotNull String cardNumber, String msg,
            boolean upload) {
        this.primaryId = primaryId;
        this.personNo = personNo;
        this.cardNumber = cardNumber;
        this.msg = msg;
        this.upload = upload;
    }

    public static final Creator<CardBean> CREATOR = new Creator<CardBean>() {
        @Override
        public CardBean createFromParcel(Parcel source) {
            return new CardBean(source);
        }

        @Override
        public CardBean[] newArray(int size) {
            return new CardBean[size];
        }
    };
}
