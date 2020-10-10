package cn.cloudwalk.libproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.HashMap;

import cn.cloudwalk.libproject.callback.DefineRecognizeCallBack;
import cn.cloudwalk.libproject.callback.FrontLiveCallback;
import cn.cloudwalk.libproject.callback.LivessCallBack;
import cn.cloudwalk.libproject.callback.ResultCallBack;

public class Bulider {
    public static String LICENCE = "MDM1MzEwbm9kZXZpY2Vjd2F1dGhvcml6ZZfn5OXl5+Tq3+bg5efm5ef65OXl4Obg5Yjm5uvl5ubrkeXm5uvl5uai6+Xm5uvl5qTm6+Xm5ufk++bn5uQ=";
    public static final int FACE_VERFY_PASS = 5, FACE_VERFY_FAIL = 6, FACE_VERFY_NETFAIL = 7,
            BESTFACE_FAIL = 8, FACE_LIVE_FAIL = 9, FACE_LIVE_PASS = 10;
    public static ArrayList<Integer> totalLiveList = new ArrayList<Integer>();
    public static ArrayList<Integer> actionList = new ArrayList<Integer>();
    public static int execLiveCount;
    public static boolean isLivesRandom;
    public static int liveLevel;
    public static boolean isResultPage;

    public static DefineRecognizeCallBack dfvCallBack;

    public static int timerCount = 8;//活体检测超时时间
    public static boolean isServerLive;//后端防攻击开关
    public static boolean isFrontHack;//前端防攻击开关
    public static ResultCallBack mResultCallBack;
    @Deprecated
    public static LivessCallBack livessCallBack;
    //前端活体完成回调(替换LivessCallBack)
    public static FrontLiveCallback mFrontLiveCallback;
    // 设置信息
    public static String licence = LICENCE;

    // activity之间传递数据过大,传递不了问题解决
    public static byte[] clipedBestFaceData;//裁剪后的最佳人脸
    public static byte[] bestFaceData;
    public static byte[] nextFaceData;//最佳人脸的下一帧
    public static String bestInfo;
    public static String nextInfo;
    public static HashMap<Integer, byte[]> liveDatas;
    public static boolean isLivesPicReturn;
    public static Class startCls;
    public static boolean isServerDet;
    // 最佳人脸
    public static long mBestFacedelayTime = 3000;

    public static String publicFilePath;

    /**
     * initFaceVerify:设置是否需要人脸比对回调. <br/>
     *
     * @param dfvCallBack
     * @return
     * @author:284891377 Date: 2016-5-19 下午4:02:15
     * @since JDK 1.7
     */
    @Deprecated
    public Bulider setFaceVerify(DefineRecognizeCallBack dfvCallBack) {

        Bulider.dfvCallBack = dfvCallBack;

        return this;
    }

    public Boolean isServerDet(boolean isServerDet) {

        return isServerDet;
    }

    /**
     * 设置活体
     *
     * @param liveList         总活体值
     * @param execLiveCount    执行活体数
     * @param isLivesRandom    活体是否随机
     * @param isLivesPicReturn 活体图片是否返回
     * @param liveLevel        活体等级
     * @return
     */
    public Bulider setLives(ArrayList<Integer> liveList, int execLiveCount, boolean isLivesRandom,
                            boolean isLivesPicReturn, int liveLevel) {
        Bulider.totalLiveList.clear();
        Bulider.totalLiveList.addAll(liveList);

        Bulider.execLiveCount = execLiveCount;
        Bulider.isLivesRandom = isLivesRandom;
        Bulider.isLivesPicReturn = isLivesPicReturn;
        if (isLivesPicReturn)
            liveDatas = new HashMap<Integer, byte[]>();
        Bulider.liveLevel = liveLevel;
        return this;
    }

    ;

    /**
     * 是否显示结果页面
     *
     * @param isResultPage
     * @return
     */
    public Bulider isResultPage(boolean isResultPage) {
        Bulider.isResultPage = isResultPage;
        return this;
    }

    /**
     * setLicence:设置授权码. <br/>
     *
     * @param licence
     * @return
     * @author:284891377 Date: 2016年6月28日 下午5:41:36
     * @since JDK 1.7
     */
    public Bulider setLicence(String licence) {
        Bulider.licence = licence;
        return this;
    }


    /**
     * 活体前端完成回调
     *
     * @return
     */
    public Bulider setFrontLiveFace(FrontLiveCallback frontLiveCallback) {
        Bulider.mFrontLiveCallback = frontLiveCallback;
        return this;
    }

    /**
     * 人脸后端验证
     *
     * @param livessCallBack
     * @return
     */
    @Deprecated /*参见setFrontLiveFace(FrontLiveCallback frontLiveCallback)*/
    public Bulider setLivessFace(LivessCallBack livessCallBack) {
        Bulider.livessCallBack = livessCallBack;
        return this;
    }

    /**
     * 后端活体的开关
     *
     * @param isServerLive
     * @return
     */
    public Bulider isServerLive(boolean isServerLive) {
        Bulider.isServerLive = isServerLive;
        return this;
    }

    /**
     * 前端防攻击开关
     *
     * @param isFrontHack
     * @return
     */
    public Bulider isFrontHack(boolean isFrontHack) {
        Bulider.isFrontHack = isFrontHack;
        return this;
    }

    /**
     * 设置结果回掉
     *
     * @param mResultCallBack
     * @return
     */
    public Bulider setResultCallBack(ResultCallBack mResultCallBack) {
        Bulider.mResultCallBack = mResultCallBack;
        return this;
    }

    /**
     * 返回比对结果
     *
     * @param ctx
     * @param result        FACE_VERFY_PASS:比对通过
     *                      FACE_VERFY_FAIL:比对不通过FACE_VERFY_NETFAIL:比对网络超时
     * @param faceScore     比对分数
     * @param faceSessionId sessionId 比对流水记录id
     * @param tipMsg        自定义提示信息
     */
    public void setFaceResult(Context ctx, int result, double faceScore, String faceSessionId,
                              String tipMsg) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(ctx);
        Intent intent = new Intent(Contants.ACTION_BROADCAST_LIVE);
        intent.putExtra("isFaceComparePass", result);
        intent.putExtra("faceCompareScore", faceScore);
        intent.putExtra("faceCompareSessionId", faceSessionId);
        intent.putExtra("faceCompareTipMsg", tipMsg);

        localBroadcastManager.sendBroadcast(intent);

    }

    public void startActivity(Activity act, Class cls) {
        startCls = cls;
        Intent it = new Intent(act, cls);
        act.startActivity(it);
    }

    /**
     * 后端活体
     *
     * @param ctx
     * @param result
     * @param extInfo
     */
    public void setFaceLiveResult(Context ctx, int result, int extInfo) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(ctx);
        Intent mIntent = new Intent(Contants.ACTION_BROADCAST_SERVER_LIVE);
        mIntent.putExtra("result", result);
        mIntent.putExtra("extInfo", extInfo);

        localBroadcastManager.sendBroadcast(mIntent);
    }

    /**
     * 设置最佳人脸延迟多少毫秒回调
     *
     * @param delayTimeMillis 默认3秒(语音播放时间),建议大于3秒
     * @return
     */
    @Deprecated
    public Bulider setBestFaceDelay(long delayTimeMillis) {

        Bulider.mBestFacedelayTime = delayTimeMillis;
        return this;
    }

    public Bulider setPublicFilePath(String publicFilePath) {
        Bulider.publicFilePath = publicFilePath;
        return this;
    }

    public Bulider setLiveTime(int timerCount) {
        Bulider.timerCount = timerCount;
        return this;
    }


}
