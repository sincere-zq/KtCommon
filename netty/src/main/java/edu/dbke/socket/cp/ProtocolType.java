package edu.dbke.socket.cp;


import com.witaction.netty.utils.Desc;

import edu.dbke.socket.cp.util.PacketCheckUitl;

public class ProtocolType {
    public static final String VERSION = "1.2.73";
    @Desc(
            key = -1,
            desc = "通讯协议版本查询"
    )
    public static final short SERVER_PROTOCOL_VERSION = -1;
    @Desc(
            key = 0,
            desc = "接受一个连接"
    )
    public static final short SERVER_ACCEPT = 0;
    @Desc(
            key = 1,
            desc = "包发送结束"
    )
    public static final short SERVER_PACKET_END = 1;
    @Desc(
            key = 2,
            desc = "成功返回"
    )
    public static final short SERVER_STATUS_OK = 2;
    @Desc(
            key = 3,
            desc = "终端断线"
    )
    public static final short SERVER_SOCKET_CLOSED = 3;
    @Desc(
            key = 4,
            desc = "请求的服务找不到"
    )
    public static final short SERVER_STATUS_NOT_FOUND = 4;
    @Desc(
            key = 5,
            desc = "服务器内部错误"
    )
    public static final short SERVER_STATUS_SERVER_ERROR = 5;
    @Desc(
            key = 6,
            desc = "服务器关闭"
    )
    public static final short SERVER_STATUS_SHUTDOWN = 6;
    @Desc(
            key = 7,
            desc = "服务器安全关闭"
    )
    public static final short SERVER_STATUS_SHUTDOWN_GRACE = 7;
    @Desc(
            key = 8,
            desc = "大数据开始"
    )
    public static final short SERVER_BIGDATA_START = 8;
    @Desc(
            key = 9,
            desc = "大数据数据包"
    )
    public static final short SERVER_BIGDATA_DATA = 9;
    @Desc(
            key = 10,
            desc = "大数据数据包接收完成"
    )
    public static final short SERVER_BIGDATA_END = 10;
    @Desc(
            key = 11,
            desc = "大数据处理测试"
    )
    public static final short BIGDATA_HANDLE_TEST = 11;
    @Desc(
            key = 12,
            desc = "服务器并发测试加入"
    )
    public static final short SERVER_CONCURRENCY_TEST_JOIN = 12;
    @Desc(
            key = 14,
            desc = "获取服务器收到记录条数"
    )
    public static final short SERVER_CONCURRENCY_TEST_COUNT = 14;
    @Desc(
            key = 15,
            desc = "socket有效性检查，心跳协议"
    )
    public static final short SERVER_SOCKET_CHECK = 15;
    @Desc(
            key = 16,
            desc = "服务器状态查询"
    )
    public static final short SERVER_STATUS_QUERY = 16;
    @Desc(
            key = 17,
            desc = "服务器监控"
    )
    public static final short SERVER_STATUS_MONITOR = 17;
    @Desc(
            key = 18,
            desc = "注册服务器"
    )
    public static final short SYSTEM_REGISTER = 18;
    @Desc(
            key = 19,
            desc = "服务器回音测试，使用Empty可做为有回执的心跳协议"
    )
    public static final short SERVER_STATUS_ECHO = 19;
    @Desc(
            key = -19,
            desc = "获取服务器当前时间，发送Empty; 返回StringPacket(时间缀)"
    )
    public static final short SERVER_STATUS_SYN_TIME = -19;
    @Desc(
            key = 20,
            desc = "数据传输"
    )
    public static final short SYSTEM_DATA_TRANS = 20;

    @Desc(
            key = 21,
            desc = "数据展示"
    )
    public static final short SYSTEM_DATA_DISPLAY = 21;
    @Desc(
            key = 22,
            desc = "功能操作包"
    )
    public static final short SYSTEM_FUN_OPERATE = 22;
    @Desc(
            key = 23,
            desc = "白名单数据包"
    )
    public static final short SYSTEM_WHITE_LIST = 23;
    @Desc(
            key = 24,
            desc = "会议签到数据包"
    )
    public static final short SYSTEM_MEETING_DISP = 24;
    @Desc(
            key = 25,
            desc = "消费内容包"
    )
    public static final short SYSTEM_CONSUME = 25;

    @Desc(
            key = 26,
            desc = "消费信息确认包"
    )
    public static final short SYSTEM_SURE_CONSUME = 26;
    @Desc(
            key = 27,
            desc = "人员注册特征包 "
    )
    public static final short SYSTEM_FEATURE_REGISTER_INFO = 27;
    @Desc(
            key = 28,
            desc = "人员注册包 "
    )
    public static final short SYSTEM_FEATURE_REGISTER = 28;
    @Desc(
            key = 29,
            desc = "报到结果包  "
    )
    public static final short SYSTEM_STUDENT_REGISTER = 29;

    @Desc(
            key = 31,
            desc = "人脸底库注册 "
    )
    public static final short SYSTEM_FRONT_FACE_REG = 31;

    @Desc(
            key = 30,
            desc = "识别记录协议包 "
    )
    public static final short SYSTEM_FRONT_IDENTIFY = 30;
    @Desc(
            key = 34,
            desc = "系统配置包"
    )
    public static final short SYSTEM_CONFIG = 34;
    @Desc(
            key = 35,
            desc = "人员姓名注册包"
    )
    public static final short SYSTEM_PERSON_REG = 35;
    @Desc(
            key = 36,
            desc = "人员姓名删除包"
    )
    public static final short SYSTEM_PERSON_DEL = 36;
    @Desc(
            key = 37,
            desc = "现场预约协议"
    )
    public static final short SYSTEM_INSTANT_BOOKING = 37;
    @Desc(
            key = 38,
            desc = "现场预约确认包"
    )
    public static final short SYSTEM_INSTANT_BOOKING_VERIFY = 38;
    @Desc(
            key = 39,
            desc = "查询特征存在情况"
    )
    public static final short SYSTEM_FEATURE_REGISTER_OTHERSEARCH_INFO = 39;
    @Desc(
            key = 40,
            desc = "离线消费记录"
    )
    public static final short SYSTEM_OFFLINE_CONSUME_UPLOAD = 40;
    @Desc(
            key = 41,
            desc = "请求消费时段"
    )
    public static final short SYSTEM_REQ_CONSUME_TIME = 41;
    @Desc(
            key = 42,
            desc = "同步服务器时间"
    )
    public static final short SYSTEM_SYC_SERVER_TIME = 42;
    @Desc(
            key = 43,
            desc = "对通道进行开门"
    )
    public static final short SYSTEM_CHANNEL_OPEN = 43;
    @Desc(
            key = 44,
            desc = "对通道配置进行更改"
    )
    public static final short SYSTEM_CHANNEL_CFG = 44;
    @Desc(
            key = 45,
            desc = "前端识别陌生人上报"
    )
    public static final short SYSTEM_FRONT_STRANGERS = 45;
    @Desc(
            key = 46,
            desc = "前端离线识别记录上传"
    )
    public static final short SYSTEM_OFFLINE_IDENTIFY_UPLOAD = 46;
    @Desc(
            key = 57,
            desc = "终端数据初始化任务包"
    )
    public static final short SYSTEM_CLIENT_INIT = 57;

    @Desc(
            key = 1000,
            desc = "手持终端位置更新"
    )
    public static final short HANDSET_LOCATION_UPDATE = 1000;
    @Desc(
            key = 1001,
            desc = "手持终端位置监控"
    )
    public static final short HANDSET_LOCATION_MONITOR = 1001;
    @Desc(
            key = 1002,
            desc = "手持终端上线"
    )
    public static final short HANDSET_ONLINE = 1002;
    @Desc(
            key = 1003,
            desc = "手持终端下线"
    )
    public static final short HANDSET_OFFLINE = 1003;
    @Desc(
            key = 1004,
            desc = "在线终端列表"
    )
    public static final short HANDSET_ONLINE_LIST = 1004;
    @Desc(
            key = 1005,
            desc = "终端监控"
    )
    public static final short HANDSET_MONITOR = 1005;

    @Desc(
            key = 1109,
            desc = "获取系统配置的会议室列表"
    )
    public static final short CONF_LIST_ROOM = 1109;
    @Desc(
            key = 1110,
            desc = "会议室实例"
    )
    public static final short CONF_ROOM_DATA = 1110;
    @Desc(
            key = 1210,
            desc = "会议室状态变更"
    )
    public static final short ROOM_STATUS_UPDATE = 1210;

    @Desc(
            key = 1112,
            desc = "会议用户实例"
    )
    public static final short CONF_ROOM_USER_DATA = 1112;

    @Desc(
            key = 1129,
            desc = "会议视频监控状态，接入或移出"
    )
    public static final short CONF_CAMERA_SHARE_STATUS = 1129;

    @Desc(
            key = 1131,
            desc = "端到端通话通道查询响应，不存在响应没空字符串"
    )
    public static final short AST_CHANNLE_QUERY_RES = 1131;

    @Desc(
            key = 1140,
            desc = "用户状态上报加入"
    )
    public static final short AST_USER_STATE_JOIN = 1140;
    @Desc(
            key = 1141,
            desc = "用户状态信息"
    )
    public static final short AST_USER_STATE_INFO = 1141;

    @Desc(
            key = 1298,
            desc = "服务机构查询结果"
    )
    public static final short UUC_SERVICEORG = 1298;
    @Desc(
            key = 1297,
            desc = "服务机构查询，返回JSON。发送AccountCheckPacket;返回StringPacket(String为JSON，格式：{succeed:是否成功,errorMsg:失败时的错误描述," +
                    "object:服务机构列表})"
    )
    public static final short UUC_SERVICEORG_QUERY_JSON = 1297;

    @Desc(
            key = 1281,
            desc = "共享桌面状态打开或关闭"
    )
    public static final short UUC_DESKTOP_SHARE_STATUS = 1281;


    @Desc(
            key = -1194,
            desc = "融合通讯服务账号验证"
    )
    public static final short UUC_ACCOUNT_CHECK_RESULT = -1194;

    @Desc(
            key = 1283,
            desc = "查询SIP帐号当前登录的socketID，StringPacket[发送到服务器sipnum，返回socketid]"
    )
    public static final short UUC_SIP_QUERY_SOCKET_ID = 1283;

    @Desc(
            key = 1197,
            desc = "融合通讯服务个人通讯录查询"
    )
    public static final short UUC_CONTACTLIST_QUERY = 1197;

    @Desc(
            key = 1196,
            desc = "融合通讯服务个人通讯录同步操作结果"
    )
    public static final short UUC_CONTACTLIST_OPT_RESULT = 1196;

    @Desc(
            key = 1340,
            desc = "用户加入队列,QueueUserPacket"
    )
    public static final short UUC_QUEUE_USER_JOIN = 1340;
    @Desc(
            key = 1341,
            desc = "用户移动位置,服务器收到移动用户位置，终端收到更新用户位置,QueueUserPacket"
    )
    public static final short UUC_QUEUE_USER_MOVE = 1341;

    @Desc(
            key = 1344,
            desc = "队列状态数据,QueueStatusPacket"
    )
    public static final short UUC_QUEUE_STATUS_DATA = 1344;

    @Desc(
            key = 1348,
            desc = "服务用户完成,OptStringInfoPacket"
    )
    public static final short UUC_QUEUE_SERVICE_FINISH = 1348;

    @Desc(
            key = 1474,
            desc = "QueueSocketkService，值班单StringPacket(dutyid&token)"
    )
    public static final short UUC_DUTY = 1474;

    @Desc(
            key = 1202,
            desc = "即时聊天消息"
    )
    public static final short MESSAGE = 1202;

    @Desc(
            key = 1204,
            desc = "群消息"
    )
    public static final short MESSAGE_GROUP = 1204;
    @Desc(
            key = 1205,
            desc = "加入一个群"
    )
    public static final short MESSAGE_GROUP_JOIN = 1205;

    @Desc(
            key = 1208,
            desc = "消息字体设置"
    )
    public static final short MESSAGE_FONT = 1208;


    public ProtocolType() {
    }

    public static void main(String[] args) throws Exception {
        PacketCheckUitl.main(null);
    }
}