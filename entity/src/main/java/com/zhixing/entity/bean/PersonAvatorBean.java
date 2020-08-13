package com.zhixing.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * create by 曾强 on 2020/3/9
 */
public class PersonAvatorBean implements Serializable {
    /**
     * "FaceId":"",
     * "ImageFilePath":""
     */
    @SerializedName("FaceId")
    private String faceId;
    @SerializedName("ImageFilePath")
    private String imageFilePath;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }
}
