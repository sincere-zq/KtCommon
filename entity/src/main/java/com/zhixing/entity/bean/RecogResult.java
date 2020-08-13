package com.zhixing.entity.bean;

import android.graphics.Bitmap;

import com.zhixing.entity.PersonBean;

import java.io.Serializable;

/**
 * 识别结果
 */
public class RecogResult implements Serializable {
    //识别
    public static final int TYPE_RECOG = 0;
    //采集
    public static final int TYPE_COLLECT = 1;
    //识别方式
    private int type;
    //工作方式（采集、识别）
    private int workType;
    //人脸图片
    private Bitmap bitmap;
    //除人脸识别外的数据
    private String data;
    //人员信息
    private PersonBean person;
    //人员照片
    private String dataString;
    //人员体温
    private float currentTemp;
    //人员体温热力图
    private String currentTempImage;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public boolean isSucess() {
        return person != null;
    }

    public String getMsg() {
        return person == null ? "陌生人" : person.getPersonName();
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCurrentTempImage() {
        return currentTempImage;
    }

    public void setCurrentTempImage(String currentTempImage) {
        this.currentTempImage = currentTempImage;
    }
}
