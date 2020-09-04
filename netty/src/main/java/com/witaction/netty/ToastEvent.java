package com.witaction.netty;

/**
 * 创建日期：2018/1/11 on 10:00
 * 描述: toastevent事件
 * 作者:YiCH
 */
public class ToastEvent {
    private String msg;//提示信息
    private boolean isNeedRescanning;//是否需要重启识别
    private boolean offline;

    public ToastEvent(String msg, boolean isNeedRescanning, boolean offline) {
        this.msg = msg;
        this.isNeedRescanning = isNeedRescanning;
        this.offline = offline;
    }

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isNeedRescanning() {
        return isNeedRescanning;
    }

    public ToastEvent(String msg, boolean isNeedRescanning) {
        this.msg = msg;
        this.isNeedRescanning = isNeedRescanning;
    }


}
