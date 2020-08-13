package com.witaction.netty.view;

/**
 * 消息处理
 */
public interface NettyRspView {
    String REGISTER_FAILED = "客户端注册失败";

    /**
     * 处理注册结果包
     */
    void handleRegistRsp(String response);

    /**
     * 处理显示结果包
     */
    void handleDataDisplayRsp(String response);

    /**
     * 处理控制引擎发来的采集人员信息包
     */
    void handleNeedFeture(String response);

    /**
     * 处理发送给控制引擎人员特征的结果包
     */
    void handleSendFetureResult(String response);

    /**
     * 特征处理
     */
    void handleFeatures(String response);

    /**
     * 注册人员
     */
    void handlePerson(String response);

    /**
     * 删除人员
     */
    void deletePerson(String response);

    /**
     * 处理消费报
     */
    void handleConsumeInfo(String response);

    /**
     * 处理消费结果
     */
    void handleConsumeResult(String response);

    /**
     * 处理白名单
     */
    void handleWhiteList(String response);

    /**
     * 处理离线消费记录上传结果包
     */
    void handleLocalConsumeRecord(String response);

    /**
     * 1.18	终端数据初始化任务包
     */
    void handleSystemInit(String response);

    /**
     * 处理消费时间段
     */
    void handleConsumeTime(String response);

    /**
     * 同步服务器时间
     */
    void handleServerTime(String response);

    /**
     * 打开门禁通道
     */
    void openChannel(String response);

    /**
     * 对通道配置进行更改
     */
    void handleChannelCfg(String response);

    /**
     * 离线识别记录上传结果
     */
    void offlineIdenRecordUploadResult(String response);
}
