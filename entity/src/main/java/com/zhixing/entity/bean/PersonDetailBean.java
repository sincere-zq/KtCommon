package com.zhixing.entity.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 人员详细信息
 */
public class PersonDetailBean implements Serializable {
    //人员编号
    private String personNo;
    //人员姓名
    private String personName;
    //员工编号
    private String number;
    //员工部门或者公司
    private String depart;
    //人脸
    private Bitmap faceBitmap;
    //指纹1
    private boolean firstFinger;
    //指纹2
    private boolean sendFinger;
    //卡号
    private String cardNum;

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

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public Bitmap getFaceBitmap() {
        return faceBitmap;
    }

    public void setFaceBitmap(Bitmap faceBitmap) {
        this.faceBitmap = faceBitmap;
    }

    public boolean isFirstFinger() {
        return firstFinger;
    }

    public void setFirstFinger(boolean firstFinger) {
        this.firstFinger = firstFinger;
    }

    public boolean isSendFinger() {
        return sendFinger;
    }

    public void setSendFinger(boolean sendFinger) {
        this.sendFinger = sendFinger;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
