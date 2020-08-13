package com.zhixing.entity.bean;

import java.io.Serializable;

/**
 * 配置信息实例类
 * Created by WangYQ on 2017/6/2.
 */

public class LocalCfgInfoBean implements Serializable {
    /**
     * ip 服务器ip
     * user_id 用户id
     * user_pwd 用户密码
     * useType 功能类型：1 仅门禁  2仅考勤  3 仅消费  4 门禁+考勤  5门禁+消费  6注册（根据类型启动对应的页面）
     * mDistance 识别距离
     */
    private String ip;
    private int port;
    private String userId;
    private String userPwd;
    private int useType;
    private int mDistance = 30;
    private String visitorServerIp;
    private String visitorUserId;
    private String visitorPwd;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public int getmDistance() {
        return mDistance;
    }

    public void setmDistance(int mDistance) {
        this.mDistance = mDistance;
    }

    public String getVisitorServerIp() {
        return visitorServerIp;
    }

    public void setVisitorServerIp(String visitorServerIp) {
        this.visitorServerIp = visitorServerIp;
    }

    public String getVisitorUserId() {
        return visitorUserId;
    }

    public void setVisitorUserId(String visitorUserId) {
        this.visitorUserId = visitorUserId;
    }

    public String getVisitorPwd() {
        return visitorPwd;
    }

    public void setVisitorPwd(String visitorPwd) {
        this.visitorPwd = visitorPwd;
    }
}
