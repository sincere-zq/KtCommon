package com.zhixing.entity.bean;

import java.io.Serializable;

/**
 * 响应结果
 */
public class ComResultBean implements Serializable {
    private String errMsg;
    private String data;
    private boolean success;

    public ComResultBean(String errMsg, String data, boolean success) {
        this.errMsg = errMsg;
        this.data = data;
        this.success = success;
    }

    public ComResultBean() {
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ComResultBean{" +
                "errMsg='" + errMsg + '\'' +
                ", data='" + data + '\'' +
                ", success=" + success +
                '}';
    }
}
