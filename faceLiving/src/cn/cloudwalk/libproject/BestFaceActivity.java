/**
 * Project Name:cwFaceForDev3
 * File Name:LiveActivity.java
 * Package Name:cn.cloudwalk.dev.mobilebank
 * Date:2016-5-16上午9:17:24
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package cn.cloudwalk.libproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.cloudwalk.CloudwalkSDK;
import cn.cloudwalk.FaceInterface;
import cn.cloudwalk.FaceInterface.CW_LivenessCode;
import cn.cloudwalk.callback.FaceInfoCallback;
import cn.cloudwalk.callback.LivessCallBack;
import cn.cloudwalk.jni.FaceInfo;
import cn.cloudwalk.libproject.camera.CameraPreview;
import cn.cloudwalk.libproject.progressHUD.CwProgressHUD;
import cn.cloudwalk.libproject.util.CameraUtil;
import cn.cloudwalk.libproject.util.DisplayUtil;
import cn.cloudwalk.libproject.util.LogUtils;
import cn.cloudwalk.libproject.util.NullUtils;
import cn.cloudwalk.libproject.util.UIUtils;
import cn.cloudwalk.libproject.util.Util;

/**
 * ClassName: BestFaceActivity <br/>
 * Description: <br/>
 * date: 2016-5-16 上午9:17:24 <br/>
 *
 * @author 284891377
 * @since JDK 1.7
 */
public class BestFaceActivity extends TemplatedActivity implements FaceInfoCallback, LivessCallBack, CameraPreview.CWPreviewCallback {
    private final String TAG = LogUtils.makeLogTag("BestFaceActivity");

    final static int BESTFACE = 101, SET_RESULT = 122, FACE_TIMEOUT = 123, PLAYMAIN_END = 125,
            CLEARFACE = 126;
    boolean isStop;// 界面遮盖
    boolean isLivePass;// 活体是否通过
    int DRUATION_INTO = 500;
    long mLastInfoTime = 0;

    boolean isSetResult = false;// 跳转页面

    boolean isStartDetectFace;// 开始检测

    // 活体声音资源初始化
    public SoundPool sndPool;
    public Map<String, Integer> poolMap;
    int currentStreamID;
    boolean isLoadmain;
    boolean isPlayMain = true;

    CameraPreview mPreview;
    FrameLayout mFl_top;
    LinearLayout mLl_bottom;

    MainHandler mMainHandler;

    // 版权图片
    ImageView mIv_copyright;
    Bitmap mCopyright;

    public CloudwalkSDK cloudwalkSDK;
    public int initRet;

    int orientation;
    public CwProgressHUD processDialog;// 进度框
    // 使用广播来传递数据
    LocalBroadcastManager localBroadcastManager;
    LiveBroadcastReceiver liveBroadcastReceiver;
    LiveServerBroadcastReceiver liveServerBroadcastReceiver;

    public class LiveBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            int faceCompareType = intent.getIntExtra("isFaceComparePass", Bulider.FACE_VERFY_FAIL);
            String faceCompareSessionId = intent.getStringExtra("faceSessionId");
            String faceCompareTipMsg = intent.getStringExtra("faceCompareTipMsg");
            double faceScore = intent.getDoubleExtra("faceCompareScore", 0d);

            setFaceResult(faceCompareType, faceScore, faceCompareSessionId, faceCompareTipMsg);

        }

    }

    public class LiveServerBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int extInfo = intent.getIntExtra("extInfo", Bulider.FACE_LIVE_FAIL);
            int result = intent.getIntExtra("result", Bulider.FACE_LIVE_FAIL);

            setFaceLiveResult(extInfo, result);
        }
    }

    private void setFaceLiveResult(int extInfo, int result) {
        boolean isLivePass = false;
        if (result == Bulider.FACE_LIVE_PASS && extInfo == 1) {
            isLivePass = true;
        } else if (result == Bulider.FACE_LIVE_FAIL || extInfo != 1) {
            isLivePass = false;
        }
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }

        mMainHandler.removeCallbacksAndMessages(null);
        if (isSetResult || isStop)
            return;
        isSetResult = true;
        if (Bulider.isResultPage) {
            Intent mIntent = new Intent(BestFaceActivity.this, LiveServerActivity.class);
            mIntent.putExtra(LiveServerActivity.FACEDECT_RESULT_ISSUCCESS, isLivePass);
            startActivity(mIntent);
        }
        finish();

    }

    @Override
    protected boolean hasActionBar() {
        orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        } else {
            return true;
        }
    }


    private void initcloudwalkSDK() {
        cloudwalkSDK = new CloudwalkSDK();
        // 设置活体等级
        cloudwalkSDK.cwSetLivessLevel(Bulider.liveLevel);

        // 初始化
        initRet = cloudwalkSDK.cwInit(this, Bulider.licence);
        cloudwalkSDK.setWorkType(CloudwalkSDK.DetectType.LIVE_DETECT);
        cloudwalkSDK.cwResetLivenessTarget();
        cloudwalkSDK.setStageflag(FaceInterface.LivessFlag.LIVE_PREPARE);
        cloudwalkSDK.setPushFrame(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloudwalk_activity_bestface);
        setTitle(R.string.cloudwalk_bestface_title);
        setRightBtnIcon(R.drawable.btn_switch);
        initSoundPool(this);
        mMainHandler = new MainHandler(this);

        initView();
        // FaceRecognize单例实例化
        initcloudwalkSDK();
        initCallBack();

        processDialog = CwProgressHUD.create(this).setStyle(CwProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.cloudwalk_faceserver_live)).setCancellable(true).setAnimationSpeed(2)
                .setDimAmount(0.5f);

    }

    @Override
    public void onCWPreviewFrame(byte[] frameData, int frameW, int frameH, int frameFormat, int frameAngle, int frameMirror) {
        //将预览帧传入sdk
        cloudwalkSDK.cwPushFrame(frameData, frameW, frameH, frameFormat, frameAngle, frameMirror);
    }

    private void playMain() {
        if (isPlayMain && isLoadmain) {
            isPlayMain = false;
            currentStreamID = 1;// 第一个加载的语音为1
            sndPool.play(currentStreamID, 1.0f, 1.0f, 0, 0, 1.0f);
            mMainHandler.sendEmptyMessageDelayed(PLAYMAIN_END, 3000);

        }

    }

    public void initSoundPool(Context ctx) {

        poolMap = new HashMap<String, Integer>();
        sndPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        sndPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

                if (1 == sampleId && initRet == 0) {
                    isLoadmain = true;
                    playMain();
                }

            }
        });

        poolMap.put("main", sndPool.load(ctx, R.raw.cloudwalk_main, 1));//

    }

    public void releaseSoundPool() {
        if (sndPool != null) {
            sndPool.setOnLoadCompleteListener(null);//避免Activity销毁后加载完成播放音乐导致崩溃
            sndPool.release();
            sndPool = null;
        }

    }

    private void initCallBack() {
        cloudwalkSDK.cwFaceInfoCallback(this);
        cloudwalkSDK.cwLivessInfoCallback(this);
        mPreview.setCWPreviewCallback(this);
    }

    private void initView() {

        // 屏幕分辨率

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels - DisplayUtil.dip2px(this, 45) - Util.getStatusBarHeight(this);
        // 版权图片
        mIv_copyright = (ImageView) findViewById(R.id.copyright_iv); // 云丛logo


        // 根据预览分辨率设置Preview尺寸
        mPreview = (CameraPreview) findViewById(R.id.preview);
        mPreview.setScreenOrientation(orientation);
        if (CameraUtil.isHasCamera(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
            mPreview.setCaremaId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            mPreview.setCaremaId(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        mFl_top = (FrameLayout) findViewById(R.id.top_fl);

        mLl_bottom = (LinearLayout) findViewById(R.id.bottom_rl);

        // 屏幕方向
        int previewW = 0, previewH = 0, flTopW = 0, flTopH = 0, bottomW = 0, bottomH = 0;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height = dm.heightPixels - Util.getStatusBarHeight(this);
            previewH = height;
            previewW = height * Contants.PREVIEW_W / Contants.PREVIEW_H;
            bottomH = height;
            bottomW = width - previewW;
            // 调整布局大小
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(previewW, previewH);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mPreview.setLayoutParams(params);

            params = new RelativeLayout.LayoutParams(previewW, previewH);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            mFl_top.setLayoutParams(params);
            mFl_top.setBackgroundResource(R.drawable.cloudwalk_face_main_camera_mask);

            params = new RelativeLayout.LayoutParams(bottomW, bottomH);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mLl_bottom.setLayoutParams(params);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //屏幕高度-自定义titlebar高度-状态栏高度-NavigationBar高度
            int navigationBarnH = 0;
            if (UIUtils.checkDeviceHasNavigationBar(this)) {
                navigationBarnH = UIUtils.getNavigationBarHeight(this);
            }
            height = dm.heightPixels - DisplayUtil.dip2px(this, 45) - Util.getStatusBarHeight(this) - navigationBarnH;
            previewW = width;
            previewH = (int) (width * 1.0 * Contants.PREVIEW_W / Contants.PREVIEW_H);
            flTopW = width;
            flTopH = width;
            bottomW = width;
            if (height - width < DisplayUtil.dip2px(this, 185)) {
                bottomH = DisplayUtil.dip2px(this, 185);
            } else {
                bottomH = height - width;
            }

            // 调整布局大小
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(previewW, previewH);
            mPreview.setLayoutParams(params);

            params = new RelativeLayout.LayoutParams(flTopW, flTopH);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            mFl_top.setLayoutParams(params);
            mFl_top.setBackgroundResource(R.drawable.cloudwalk_face_main_camera_mask);

            params = new RelativeLayout.LayoutParams(bottomW, bottomH);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mLl_bottom.setLayoutParams(params);
        }

    }

    /**
     * 获取最佳人脸
     */
    private void getBestFace() {
        Bulider.clipedBestFaceData = cloudwalkSDK.cwGetClipedBestFace();
        Bulider.bestFaceData = cloudwalkSDK.cwGetOriBestFace();
        Bulider.nextFaceData = cloudwalkSDK.cwGetNextFace();
        Bulider.bestInfo = cloudwalkSDK.cwGetBestInfo();
        Bulider.nextInfo = cloudwalkSDK.cwGetNextInfo();
    }

//    private void doFaceVerify() {
//        isLivePass = true;
//
//        if (Bulider.mFrontLiveCallback == null) {
//
//            mMainHandler.obtainMessage(SET_RESULT, CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK).sendToTarget();
//
//            return;
//        } else {
//            localBroadcastManager = LocalBroadcastManager.getInstance(this);
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(Contants.ACTION_BROADCAST_SERVER_LIVE);
//            liveServerBroadcastReceiver = new LiveServerBroadcastReceiver();
//            localBroadcastManager.registerReceiver(liveServerBroadcastReceiver, filter);
//            processDialog.show();
//            Bulider.mFrontLiveCallback.onFrontLivessFinished(Bulider.bestFaceData, Bulider.bestInfo,
//                    Bulider.nextFaceData, Bulider.nextInfo, Bulider.clipedBestFaceData, true);
//        }
//
//    }

    private void doFaceSerLivess() {
        isLivePass = true;

        if (Bulider.mFrontLiveCallback == null) {
            mMainHandler.obtainMessage(SET_RESULT, CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK).sendToTarget();
        } else {

            if (Bulider.isServerLive) {
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
                IntentFilter filter = new IntentFilter();
                filter.addAction(Contants.ACTION_BROADCAST_SERVER_LIVE);
                liveServerBroadcastReceiver = new LiveServerBroadcastReceiver();
                localBroadcastManager.registerReceiver(liveServerBroadcastReceiver, filter);
                processDialog.show();
                Bulider.mFrontLiveCallback.onFrontLivessFinished(Bulider.bestFaceData, Bulider.bestInfo,
                        Bulider.nextFaceData, Bulider.nextInfo, Bulider.clipedBestFaceData, true);
            } else {
                mMainHandler.obtainMessage(SET_RESULT, CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK)
                        .sendToTarget();
            }
        }
    }

    private void doFrontFaceLivess() {
        isLivePass = cloudwalkSDK.cwVerifyBestImg() == 0 ? true : false;//前端活体是否通过
        if (Bulider.mFrontLiveCallback == null) {
            mMainHandler.obtainMessage(SET_RESULT, CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK)
                    .sendToTarget();
        } else {
            if (Bulider.isFrontHack && Bulider.mFrontLiveCallback != null) {
                IntentFilter filter = new IntentFilter();
                filter.addAction(Contants.ACTION_BROADCAST_LIVE);
                liveBroadcastReceiver = new LiveBroadcastReceiver();
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
                localBroadcastManager.registerReceiver(liveBroadcastReceiver, filter);
                Bulider.mFrontLiveCallback.onFrontLivessFinished(Bulider.bestFaceData, Bulider.bestInfo,
                        Bulider.nextFaceData, Bulider.nextInfo, Bulider.clipedBestFaceData, isLivePass);
            } else {
                mMainHandler.obtainMessage(SET_RESULT, CW_LivenessCode.CW_FACE_LIVENESS_FACEDEC_OK)
                        .sendToTarget();
            }
        }
    }

    /**
     * 返回比对结果
     *
     * @param faceCompareType 人脸比对是否通过
     * @param faceScore       比对分数
     * @param sessionId       sessionId
     * @param tipMsg          自定义提示信息
     */
    public void setFaceResult(int faceCompareType, double faceScore, String sessionId, String tipMsg) {
        int resultType;
        boolean isVerfyPass = false;
        if (Bulider.FACE_VERFY_PASS == faceCompareType) {
            resultType = Bulider.FACE_VERFY_PASS;
            isVerfyPass = true;
        } else if (Bulider.FACE_VERFY_FAIL == faceCompareType) {
            resultType = Bulider.FACE_VERFY_FAIL;
        } else {
            resultType = Bulider.FACE_VERFY_NETFAIL;
        }
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }

        setFaceResult(isVerfyPass, faceScore, sessionId, resultType, tipMsg);

    }

    private void setFaceResult(boolean isVerfyPass, double faceScore, String sessionId, int resultType, String tipMsg) {
        mMainHandler.removeCallbacksAndMessages(null);
        if (isSetResult || isStop)
            return;
        isSetResult = true;

        if (Bulider.isResultPage) {

            Intent mIntent = new Intent(this, LiveResultActivity.class);
            mIntent.putExtra(LiveResultActivity.FACEDECT_RESULT_TYPE, resultType);// 结果类型
            if (NullUtils.isNotEmpty(tipMsg))
                mIntent.putExtra(LiveResultActivity.FACEDECT_RESULT_MSG, tipMsg);// 自定义提示语句
            mIntent.putExtra(LiveResultActivity.ISLIVEPASS, isLivePass);// 活体是否通过
            mIntent.putExtra(LiveResultActivity.ISVERFYPASS, isVerfyPass);// 比对是否通过
            mIntent.putExtra(LiveResultActivity.FACESCORE, faceScore);// 比对分数
            mIntent.putExtra(LiveResultActivity.SESSIONID, sessionId);// 比对日志id

            startActivity(mIntent);
            finish();
        } else {

            if (Bulider.mResultCallBack != null)
                Bulider.mResultCallBack.result(isLivePass, isVerfyPass, sessionId, faceScore, resultType,
                        Bulider.bestFaceData, Bulider.clipedBestFaceData, null);
            finish();
        }

    }

    void reset() {
        // 重置页面状态
        Bulider.bestFaceData = null;
        Bulider.clipedBestFaceData = null;
        Bulider.liveDatas = null;//清空动作图
        isPlayMain = true;
        playMain();
        isLivePass = false;
        isStartDetectFace = false;
        isSetResult = false;
        cloudwalkSDK.cwResetLivenessTarget();
        cloudwalkSDK.setStageflag(FaceInterface.LivessFlag.LIVE_PREPARE);
        cloudwalkSDK.setPushFrame(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reset();

        isStop = false;

        if (initRet == 0) {
            mPreview.cwStartCamera();

        } else {

            mMainHandler.obtainMessage(SET_RESULT, CW_LivenessCode.CW_FACE_LIVENESS_AUTH_ERROR).sendToTarget();

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPreview.setCWPreviewCallback(null);//置空回调
        mMainHandler.removeCallbacksAndMessages(null);
        cloudwalkSDK.cwDestory();
        releaseSoundPool();
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
        if (localBroadcastManager != null)
            localBroadcastManager.unregisterReceiver(liveBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPreview.cwStopCamera();

        isStop = true;

        mMainHandler.removeCallbacksAndMessages(null);
        sndPool.stop(currentStreamID);

    }

    public class MainHandler extends Handler {
        private final WeakReference<BestFaceActivity> mActivity;

        public MainHandler(BestFaceActivity activity) {
            mActivity = new WeakReference<BestFaceActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BestFaceActivity activity = mActivity.get();
            switch (msg.what) {

                case SET_RESULT:
                    Integer resultCode = (Integer) msg.obj;
                    setFaceResult(false, 0d, "", resultCode, null);
                    break;
                case CLEARFACE:
                    cloudwalkSDK.cwClearBestFace();
                    break;
                case PLAYMAIN_END:
                    isStartDetectFace = true;//
                    cloudwalkSDK.cwResetLivenessTarget();
                    cloudwalkSDK.setStageflag(FaceInterface.LivessFlag.LIVE_PREPARE);
                    cloudwalkSDK.setPushFrame(true);
                    break;
                case BESTFACE:
                    getBestFace();
                    if (Bulider.bestFaceData == null ||
                            (Bulider.bestFaceData != null && Bulider.bestFaceData.length == 0)
                            || Bulider.clipedBestFaceData == null ||
                            (Bulider.clipedBestFaceData != null && Bulider.clipedBestFaceData.length == 0)) {
                        cloudwalkSDK.cwResetLivenessTarget();
                        cloudwalkSDK.setStageflag(FaceInterface.LivessFlag.LIVE_PREPARE);
                        cloudwalkSDK.setPushFrame(true);
                    } else {
                        mMainHandler.removeCallbacksAndMessages(null);
                        if (Bulider.isServerLive) {
                            activity.doFaceSerLivess();
                        } else if (Bulider.isFrontHack) {
                            Log.i("here","here");
                            activity.doFrontFaceLivess();//调用前端防攻击
                        }
//						doFaceVerify();
                    }

                    break;

            }

            super.handleMessage(msg);
        }

    }


    @Override
    public void detectFaceInfo(FaceInfo[] faceInfos, int faceNum) {
        if (faceNum > 0) {// 检测到脸, 画脸框

        } else {// 未检测到脸,清除脸框

        }

    }

    @Override
    public void detectInfo(final int info) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastInfoTime > DRUATION_INTO) {
            mLastInfoTime = currentTime;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv_Info = (TextView) findViewById(R.id.cloudwalk_bestface_info_txt);
                    if (tv_Info != null) {
                        switch (info) {
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_WAIT_NEXT_FRAME://too far
                                tv_Info.setText(R.string.cloudwalk_tip_not_center);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_TOO_FAR://too far
                                tv_Info.setText(R.string.cloudwalk_tip_too_far);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_TOO_CLOSE://too close
                                tv_Info.setText(R.string.cloudwalk_tip_too_close);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_NOT_FRONTAL://not frontal
                                tv_Info.setText(R.string.cloudwalk_tip_not_frontal);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_NOT_STABLE://not stable
                                tv_Info.setText(R.string.cloudwalk_tip_not_stable);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_TOO_DARK://too dark
                                tv_Info.setText(R.string.cloudwalk_tip_too_dark);

                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_TOO_BRIGHT://too bright
                                tv_Info.setText(R.string.cloudwalk_tip_too_bright);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_FACE_SHIELD://face shield
                                tv_Info.setText(R.string.cloudwalk_tip_face_shield);
                                break;
                            case FaceInterface.CW_LivenessCode.CW_FACE_INFO_NOT_CENTER://not center
                                tv_Info.setText(R.string.cloudwalk_tip_not_center);
                                break;
                            case FaceInterface.CW_FaceDETCode.CW_FACE_NO_FACE:
                                tv_Info.setText(R.string.cloudwalk_tip_no_face);
                                break;

                            //句柄提示信息
                            case FaceInterface.CW_FaceDETCode.CW_FACE_UNAUTHORIZED_ERR:
                                tv_Info.setText(R.string.facedectfail_appid);
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void detectReady() {
        cloudwalkSDK.cwResetLiving();
        cloudwalkSDK.setStageflag(FaceInterface.LivessFlag.LIVE_EYE_BLINK);
        if (!isLivePass) {
            if (mMainHandler != null) {
                //准备阶段完成,1s取最佳人脸
                mMainHandler.sendEmptyMessageDelayed(BESTFACE, 1000);
            }
        }
    }

    @Override
    public void detectFinished() {

    }

    @Override
    public void detectLivess(int livessType, byte[] imageData) {

    }

    @Override
    public void OnActionNotStandard(int notStandardType) {
        if (!isLivePass) {
            if (mMainHandler != null) {
                mMainHandler.obtainMessage(SET_RESULT, notStandardType).sendToTarget();
            }
        }
    }
}