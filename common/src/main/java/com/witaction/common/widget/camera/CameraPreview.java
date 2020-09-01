package com.witaction.common.widget.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 实时预览帧 setPreviewCallback
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback, PreviewCallback {

    private Camera mCamera;
    private CWPreviewCallback mCWPreviewCallback;
    Delegate mDelegate;
    private int orientation;
    private int mFrameCount = 0;
    private static final int DEFAULT_EXPOSURE = 0;//自动曝光默认值
    private static final int DEFAULT_EXPOSURE_STEP = 1;//自动曝光步长
    private boolean mPushFrame = true;

    /**
     * 设置屏幕方向
     *
     * @param orientation Configuration.ORIENTATION_LANDSCAPE 或者
     *                    Configuration.ORIENTATION_PORTRAIT
     */
    public void setScreenOrientation(int orientation) {
        this.orientation = orientation;

    }

    //摄像头id
    int caremaId = Camera.CameraInfo.CAMERA_FACING_FRONT;

    public int getCaremaId() {
        return caremaId;
    }

    public void setCaremaId(int caremaId) {
        this.caremaId = caremaId;
    }

    private boolean mPreviewing = true;
    private boolean mSurfaceCreated = false;
    private CameraConfigurationManager mCameraConfigurationManager;
    Context context;

    /**
     * setReqPrevWH:设置希望的预览分辨率. <br/>
     *
     * @author:284891377 Date: 2016/10/25 0025 10:50
     * @since JDK 1.7
     */
    public void setReqPrevWH(int reqPrevW, int reqPrevH) {
        this.reqPrevW = reqPrevW;
        this.reqPrevH = reqPrevH;
    }

    int reqPrevW = 640, reqPrevH = 480;

    public CameraPreview(Context context) {
        super(context);
        this.context = context;
        mFrameCount = 0;
        orientation = context.getResources().getConfiguration().orientation;
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        this.context = context;
        mFrameCount = 0;
        orientation = context.getResources().getConfiguration().orientation;
    }

    public CameraPreview(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context = context;
        mFrameCount = 0;
        orientation = context.getResources().getConfiguration().orientation;
    }

    public void setCamera(Camera camera) {
        mCamera = camera;
        if (mCamera != null) {
            mCameraConfigurationManager = new CameraConfigurationManager(getContext());

            getHolder().addCallback(this);
            if (mPreviewing) {
                requestLayout();
            } else {
                showCameraPreview();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceCreated = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        stopCameraPreview();
        showCameraPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceCreated = false;
        stopCameraPreview();
    }

    public void showCameraPreview() {
        if (mCamera != null) {
            try {
                mPreviewing = true;
                mCamera.setPreviewDisplay(getHolder());

                int degree = mCameraConfigurationManager.setCameraParametersForPreviewCallBack(mCamera,
                        caremaId, reqPrevW,
                        reqPrevH);
                mCamera.startPreview();
                mCamera.setPreviewCallback(CameraPreview.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopCameraPreview() {
        if (mCamera != null) {
            try {
                mPreviewing = false;
                mCamera.cancelAutoFocus();
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 增加相机曝光值
     */
    public void increaseExposure() {
        try {
            if (mCamera != null) {
                Camera.Parameters params = mCamera.getParameters();
                if (!(params.getMaxExposureCompensation() == 0 && params.getMinExposureCompensation() == 0)) {
                    int current = params.getExposureCompensation();
                    if (current + DEFAULT_EXPOSURE_STEP <= params.getMaxExposureCompensation()) {
                        params.setExposureCompensation(current + DEFAULT_EXPOSURE_STEP);
                        mCamera.setParameters(params);
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * 降低相机曝光值
     */
    public void decreaseExposure() {
        try {
            if (mCamera != null) {
                Camera.Parameters params = mCamera.getParameters();
                if (!(params.getMaxExposureCompensation() == 0 && params.getMinExposureCompensation() == 0)) {
                    int current = params.getExposureCompensation();
                    if (current - DEFAULT_EXPOSURE_STEP >= params.getMinExposureCompensation()) {
                        params.setExposureCompensation(current - DEFAULT_EXPOSURE_STEP);
                        mCamera.setParameters(params);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void openFlashlight() {
        if (flashLightAvaliable()) {
            mCameraConfigurationManager.openFlashlight(mCamera);
        }
    }

    public void closeFlashlight() {
        if (flashLightAvaliable()) {
            mCameraConfigurationManager.closeFlashlight(mCamera);
        }
    }

    private boolean flashLightAvaliable() {
        return mCamera != null && mPreviewing && mSurfaceCreated
                && getContext().getPackageManager().hasSystemFeature(PackageManager
                .FEATURE_CAMERA_FLASH);
    }

    /******************************************************************/
    public Size getPreviewSize() {
        Camera.Parameters parameters = mCamera.getParameters();
        return parameters.getPreviewSize();
    }

    public void setDelegate(Delegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    /**
     * 打开摄像头开始预览，但是并未开始识别
     */
    public void cwStartCamera() {
        if (mCamera != null) {
            return;
        }

        try {
            mCamera = Camera.open(caremaId);
        } catch (Exception e) {
            if (mDelegate != null) {
                mDelegate.onOpenCameraError();
            }
        }
        setCamera(mCamera);
    }

    /**
     * 关闭摄像头预览，并且隐藏扫描框
     */
    public void cwStopCamera() {
        if (mCamera != null) {
            stopCameraPreview();

            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }

    /**
     * 切换摄像头
     */
    public int switchCarema() {
        cwStopCamera();
        if (caremaId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            caremaId = Camera.CameraInfo.CAMERA_FACING_BACK;
        } else {
            caremaId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        cwStartCamera();
        return caremaId;
    }

    public void setPushFrame(boolean mPushFrame) {
        this.mPushFrame = mPushFrame;
    }

    /**
     * 设置预览回调
     *
     * @param cwPreviewCallback
     */
    public void setCWPreviewCallback(CWPreviewCallback cwPreviewCallback) {
        this.mCWPreviewCallback = cwPreviewCallback;
    }

    /**
     * 预览回调
     */
    public interface CWPreviewCallback {
        void onCWPreviewFrame(byte[] frameData, int frameW, int frameH, int frameFormat, int frameAngle, int frameMirror);
    }
}