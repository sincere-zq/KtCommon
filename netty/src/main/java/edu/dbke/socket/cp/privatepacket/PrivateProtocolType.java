package edu.dbke.socket.cp.privatepacket;

/**
 * 转接呼叫逻辑的私有协议
 */

public class PrivateProtocolType {
    public final static short
            TRANSFER_CALL_REQUEST = 101,    // 转接通知协议
            TRANSFER_CALL_ANSWER = 102,    // 转接确认协议
            TRANSFER_CALL_ACK = 103,    // 转接确认认可协议
            TRANSFER_FILE_REQUEST = 104,  //文件传输请求
            TRANSFER_FILE_RECEIVE = 105, //接收文件传输请求
            TRANSFER_FILE_REJECT = 106,//拒绝文件传输请求
            TRANSFER_FILE_SEND_CANCEL = 107,//取消文件发送
            TRANSFER_FILE_SEND_DATA = 108;// 文件传输，文件数据包
}
