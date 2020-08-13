package com.zhixing.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * create by 曾强 on 2020/3/26
 * 授权请求体
 */
public class AuthReqBean implements Serializable {
    @SerializedName("MachineCode")
    private String machineCode;
    @SerializedName("Sn")
    private String sn;
    @SerializedName("CompanyType")
    private int companyType = 1;
    @SerializedName("Mobile")
    private String mobile = "11100000000";
    @SerializedName("ClientType")
    private int clientType = 1;
    @SerializedName("UseMonthCount")
    private int useMonthCount = 240;

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getUseMonthCount() {
        return useMonthCount;
    }

    public void setUseMonthCount(int useMonthCount) {
        this.useMonthCount = useMonthCount;
    }
}
