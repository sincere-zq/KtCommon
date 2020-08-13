package com.zhixing.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * create by 曾强 on 2020/3/9
 */
public class VisitorBean implements Serializable {
    /**
     * "PersonNo": "c93c1d0c02bf4908bce7e0530fa2182a",
     * "PersonName": "张三",
     * "FaceData":
     */
    @SerializedName("PersonNo")
    private String personNo;
    @SerializedName("PersonName")
    private String personName;
    @SerializedName("FaceData")
    private List<PersonAvatorBean> faceData = new ArrayList<>();

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

    public List<PersonAvatorBean> getFaceData() {
        return faceData;
    }

    public void setFaceData(List<PersonAvatorBean> faceData) {
        this.faceData = faceData;
    }
}
