package com.witaction.netty.callback;


import com.witaction.netty.ToastEvent;
import com.witaction.netty.view.NettyReqView;
import com.witaction.netty.view.NettyRspView;

import org.greenrobot.eventbus.EventBus;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.ProtocolType;
import edu.dbke.socket.cp.packet.StringPacket;
import edu.dbke.socket.cp.util.LogUtils;

/**
 * 服务器通信
 */

public class NettyNetCallback implements NettyCallBack {
    public static final String CONNECT_FAILED = "服务器连接失败";
    private NettyReqView reqPresenter;
    private NettyRspView rspPresenter;

    public NettyNetCallback(NettyReqView reqPresenter, NettyRspView rspPresenter) {
        this.reqPresenter = reqPresenter;
        this.rspPresenter = rspPresenter;
    }

    @Override
    public void onConnect(int comefrom) {
        LogUtils.e("onConnect:" + comefrom);
        //连接服务器成功
        EventBus.getDefault().post(new ToastEvent("服务器连接成功", false));
        //通知界面链接成功
        //发送登录包，
        reqPresenter.registerAgain();
    }

    @Override
    public void onReceiveMsg(Object data) {
        Packet packet = (Packet) data;
        int type = packet.type;
        String response = ((StringPacket) data).dataStr;
        LogUtils.e("StringPacket:" + response);
        switch (type) {
            case ProtocolType.SYSTEM_REGISTER:
                rspPresenter.handleRegistRsp(response);
                break;
            case ProtocolType.SYSTEM_DATA_DISPLAY:
                //收到数据显示包
                rspPresenter.handleDataDisplayRsp(response);
                break;
            case ProtocolType.SYSTEM_CONSUME:
                //消费协议包
                rspPresenter.handleConsumeInfo(response);
                break;
            case ProtocolType.SYSTEM_SURE_CONSUME:
                //消费成功与否
                rspPresenter.handleConsumeResult(response);
                break;
            case ProtocolType.SYSTEM_WHITE_LIST:
                //收到白名单数据包
                rspPresenter.handleWhiteList(response);
                break;
            case ProtocolType.SYSTEM_FEATURE_REGISTER_INFO:
                //收到注册信息响应包
                rspPresenter.handleNeedFeture(response);
                break;
            case ProtocolType.SYSTEM_FEATURE_REGISTER:
                //控制引擎是否收到注册成功与否包
                rspPresenter.handleSendFetureResult(response);
                break;
            case ProtocolType.SYSTEM_FRONT_FACE_REG:
                //人员本地底库注册包
                rspPresenter.handleFeatures(response);
                break;
            case ProtocolType.SYSTEM_PERSON_REG:
                //增加人员姓名
                rspPresenter.handlePerson(response);
                break;
            case ProtocolType.SYSTEM_PERSON_DEL:
                //删除人员姓名
                rspPresenter.deletePerson(response);
                break;
            case ProtocolType.SYSTEM_OFFLINE_CONSUME_UPLOAD:
                //离线消费记录
                rspPresenter.handleLocalConsumeRecord(response);
                break;
            case ProtocolType.SYSTEM_CLIENT_INIT:
                //1.18	终端数据初始化任务包
                rspPresenter.handleSystemInit(response);
                break;
            case ProtocolType.SYSTEM_REQ_CONSUME_TIME:
                //消费时间段结果
                rspPresenter.handleConsumeTime(response);
                break;
            case ProtocolType.SYSTEM_SYC_SERVER_TIME:
                //同步服务器时间
                rspPresenter.handleServerTime(response);
                break;
            case ProtocolType.SYSTEM_CHANNEL_OPEN:
                //打开门禁通道
                rspPresenter.openChannel(response);
                break;
            case ProtocolType.SYSTEM_CHANNEL_CFG:
                //对通道配置进行更改
                rspPresenter.handleChannelCfg(response);
                break;
            case ProtocolType.SYSTEM_OFFLINE_IDENTIFY_UPLOAD:
                //前端离线识别记录上传
                rspPresenter.offlineIdenRecordUploadResult(response);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDisconnected() {
        //网络断开了
        EventBus.getDefault().post(new ToastEvent(CONNECT_FAILED, false));
        LogUtils.d("网络断开了取消注册");
        reqPresenter.cancelRegisterAgainTimer();
    }

    @Override
    public void onException(Throwable e) {
        LogUtils.e("onException:" + e.toString());
        EventBus.getDefault().post(new ToastEvent(CONNECT_FAILED, false));
    }
}
