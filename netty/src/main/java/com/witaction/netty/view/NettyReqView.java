package com.witaction.netty.view;


import java.io.Serializable;

/**
 * 消息发送处理
 */
public interface NettyReqView extends Serializable {
    /**
     * 取消重新登录
     */
    void cancelRegisterAgainTimer();

    /**
     * 重新登录
     */
    void registerAgain();

    /**
     * 发送注册包
     */
    void sendRegisterPacket();

    /**
     * 请求消费时段
     */
    void reqestConsumeTime();

    /**
     * 发送白名单注册结果bao
     */
    void sendWhiteInfoReq(String serNo);

    /**
     * 人员特征注册结果
     */
    void sendFeatureRegisterPacket(String dutySerNo, boolean result, String msg);

    /**
     * 发送人员删除结果包
     */
    void sendPersonDelPacket(String dutySerNo);

    /**
     * 发送人员信息注册结果包
     */
    void sendPersonRegisterPacket(String dutySerNo, boolean result, String msg);

    /**
     * 识别器本地识别包
     */
    void sendFrontIdentifyPacket(int type, String data, String personNo, int personType, float currentTemp, String currentTempImage);

    /**
     * 发送消费确认包
     */
    void sendConsumePacket(String accountInfo, int money, int identifyType, int personClass, int consumeType);

    /**
     * 发送人员特征注册结果包
     */
    void sendFaceRegisterPacket(String dutySerNo, boolean result, String msg);

    /**
     * 根据手机号或者员工编号查询特征存在情况
     */
    void sendStrCheckFeature(String searchText);
}
