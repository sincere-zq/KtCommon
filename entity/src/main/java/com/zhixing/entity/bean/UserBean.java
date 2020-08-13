package com.zhixing.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 登录结果
 */
public class UserBean implements Serializable {
    /**
     * "Id": "c93c1d0c02bf4908bce7e0530fa2182a",
     * "Name": "德外正门一号访客机",
     * "Number": "VC001",
     * "Logo": ".jpg",
     * "SchoolId": "b10ef22d-3d22-426f-a7cd-4ab0d5dbe23d",
     * "SchoolName": "德阳外国语小学",
     * "Token": "+TdONRAwrywpcO9Q9e4ZOmtXAJUfkNyG+U=",
     * "UseSiteId": "a52a82830bf540bb896393cafda42ab0",
     * "UseSiteName": "德阳外国语小学正大门",
     * <p>
     * "ClientType": "",
     * "IoType": ""
     */
    @SerializedName("Id")
    private String id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Number")
    private String number;
    @SerializedName("Logo")
    private String logo;
    @SerializedName("SchoolId")
    private String schoolId;
    @SerializedName("SchoolName")
    private String schoolName;
    @SerializedName("Token")
    private String token;
    @SerializedName("UseSiteId")
    private String useSiteId;
    @SerializedName("UseSiteName")
    private String useSiteName;
    @SerializedName("ClientType")
    private int clientType;
    @SerializedName("IoType")
    private int ioType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUseSiteId() {
        return useSiteId;
    }

    public void setUseSiteId(String useSiteId) {
        this.useSiteId = useSiteId;
    }

    public String getUseSiteName() {
        return useSiteName;
    }

    public void setUseSiteName(String useSiteName) {
        this.useSiteName = useSiteName;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getIoType() {
        return ioType;
    }

    public void setIoType(int ioType) {
        this.ioType = ioType;
    }
}
