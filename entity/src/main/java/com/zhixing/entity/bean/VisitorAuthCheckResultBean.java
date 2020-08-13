package com.zhixing.entity.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * create by 曾强 on 2020/3/9
 * 操作结果
 * <p>
 * OpenChannel：是否打开通道
 * <p>
 * 注意：只有IsSuccess和OpenChannel同时为1
 * <p>
 * 且访客终端类型（ClientType）为2（闸机头）时才打开通道.
 */
public class VisitorAuthCheckResultBean implements Serializable {
    @SerializedName("OpenChannel")
    private int openChannel;

    public int getOpenChannel() {
        return openChannel;
    }

    public void setOpenChannel(int openChannel) {
        this.openChannel = openChannel;
    }
}
