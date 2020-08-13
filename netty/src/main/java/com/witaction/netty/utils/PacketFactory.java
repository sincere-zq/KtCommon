package com.witaction.netty.utils;


import java.nio.ByteBuffer;
import java.util.HashMap;

import edu.dbke.socket.cp.Packet;
import edu.dbke.socket.cp.ProtocolType;
import edu.dbke.socket.cp.packet.EmptyPacket;
import edu.dbke.socket.cp.packet.StringPacket;
import edu.dbke.socket.cp.packet.receive.CameraSharePacket;
import edu.dbke.socket.cp.packet.receive.ConfBridgeRoomPacket;
import edu.dbke.socket.cp.packet.receive.ConfBridgeUserPacket;
import edu.dbke.socket.cp.packet.receive.DesktopSharePacket;
import edu.dbke.socket.cp.packet.receive.MessageFontPacket;
import edu.dbke.socket.cp.packet.receive.OptByteInfoPacket;
import edu.dbke.socket.cp.packet.receive.QueueStatusPacket;
import edu.dbke.socket.cp.packet.receive.RoomStatusUpdatePacket;
import edu.dbke.socket.cp.packet.receive.ServiceOrgPacket;
import edu.dbke.socket.cp.packet.receive.UserStatusInfoPacket;
import edu.dbke.socket.cp.packet.send.AccountCheckPacket;
import edu.dbke.socket.cp.packet.send.ContactListPacket;
import edu.dbke.socket.cp.packet.send.MessageGroupJoinPacket;
import edu.dbke.socket.cp.packet.send.MessageGroupPacket;
import edu.dbke.socket.cp.packet.send.MessagePacket;
import edu.dbke.socket.cp.packet.send.OptStringPacket;
import edu.dbke.socket.cp.packet.send.QueueUserPacket;
import edu.dbke.socket.cp.packet.send.UserStatusJoinPacket;
import edu.dbke.socket.cp.util.LogUtils;

/**
 * Created by Zhangs on 2017/3/10.
 */
public class PacketFactory {
    private static HashMap<Short, Class> REPLY_TYPE_PACKET_MAP = new HashMap<>();

    //存储返回数据协议号对应的Packet类，用于将ByteBuffer转化成packet
    static {
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_QUEUE_STATUS_DATA, QueueStatusPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.AST_USER_STATE_JOIN, UserStatusJoinPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_QUEUE_USER_JOIN, QueueUserPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_SERVICEORG, ServiceOrgPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_SERVICEORG_QUERY_JSON, StringPacket.class);//服务机构返回StringPacket，根据String做json解析
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.ROOM_STATUS_UPDATE, RoomStatusUpdatePacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.CONF_ROOM_DATA, ConfBridgeRoomPacket.class); //会议室
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.CONF_ROOM_USER_DATA, ConfBridgeUserPacket.class);//会议人员
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.CONF_CAMERA_SHARE_STATUS, CameraSharePacket.class);//摄像头
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_DESKTOP_SHARE_STATUS, DesktopSharePacket.class);//桌面分享
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.AST_CHANNLE_QUERY_RES, StringPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_DUTY, StringPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_SIP_QUERY_SOCKET_ID, StringPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.CONF_LIST_ROOM, AccountCheckPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SERVER_STATUS_ECHO, EmptyPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_QUEUE_SERVICE_FINISH, OptStringPacket.class); //用户退出排队，或者用户挂断电话或收到的信息
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.AST_USER_STATE_INFO, UserStatusInfoPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.MESSAGE_FONT, MessageFontPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_QUEUE_USER_MOVE, QueueUserPacket.class);//位置更新；
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_CONTACTLIST_QUERY, ContactListPacket.class);//通讯录
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_CONTACTLIST_OPT_RESULT, OptByteInfoPacket.class);//通讯录
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.UUC_ACCOUNT_CHECK_RESULT, AccountCheckPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.MESSAGE, MessagePacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.MESSAGE_GROUP_JOIN, MessageGroupJoinPacket.class);
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.MESSAGE_GROUP, MessageGroupPacket.class);

        //人脸识别
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_REGISTER, StringPacket.class);//注册服务
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_DATA_TRANS, StringPacket.class);//数据传输
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_DATA_DISPLAY, StringPacket.class);//数据展示
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FUN_OPERATE, StringPacket.class);//功能操作
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_CONSUME, StringPacket.class);//消费内容
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_SURE_CONSUME, StringPacket.class);//消费确认
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_WHITE_LIST, StringPacket.class);//白名单
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FEATURE_REGISTER_INFO, StringPacket.class);//注册信息
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FEATURE_REGISTER, StringPacket.class);//人员注册包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_STUDENT_REGISTER, StringPacket.class);//报到结果包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FRONT_IDENTIFY, StringPacket.class);//本地识别成功，发送到服务器做记录协议包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FRONT_FACE_REG, StringPacket.class);//人脸注册协议
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_CONFIG, StringPacket.class);//系统配置包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_PERSON_REG, StringPacket.class);//人员姓名增加包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_PERSON_DEL, StringPacket.class);//人员姓名删除包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_INSTANT_BOOKING, StringPacket.class);//现场预约
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_INSTANT_BOOKING_VERIFY, StringPacket.class);//现场预约确认
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_MEETING_DISP, StringPacket.class);//功能操作
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FEATURE_REGISTER_OTHERSEARCH_INFO, StringPacket.class);//查询特征存在情况
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_OFFLINE_CONSUME_UPLOAD, StringPacket.class);//离线消费记录
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_CLIENT_INIT, StringPacket.class);//终端数据初始化任务包
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_REQ_CONSUME_TIME, StringPacket.class);//请求消费时段
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_SYC_SERVER_TIME, StringPacket.class);//同步服务器时间
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_CHANNEL_OPEN, StringPacket.class);//对通道进行开门
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_CHANNEL_CFG, StringPacket.class);//对通道配置进行更改
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_FRONT_STRANGERS, StringPacket.class);//前端识别陌生人上报
        REPLY_TYPE_PACKET_MAP.put(ProtocolType.SYSTEM_OFFLINE_IDENTIFY_UPLOAD, StringPacket.class);//前端离线识别记录上传
    }

    public static Packet<?> getPacket(short type, ByteBuffer byteBuffer) {
        Packet<?> packet = null;
        try {
            Class<?> clazz = REPLY_TYPE_PACKET_MAP.get(type);
            if (clazz == null) {
                LogUtils.e("Protocol Type not register,pls put in PacketFactory");
                return null;
            }
            packet = (Packet<?>) clazz.newInstance();
            packet.readObject(byteBuffer);

        } catch (InstantiationException e) {
            LogUtils.e("getPacket InstantiationException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            LogUtils.e("getPacket IllegalAccessException");
            e.printStackTrace();
        } finally {
        }
        return packet;
    }
}
