package cn.cloudwalk.usbdriver;

import android.graphics.Bitmap;

/**
 * create by 曾强 on 2020/3/25
 */
public interface OnTempDetectedCallBack {

    void detected(Bitmap bitmap);

    void currentTempValue(float value);
}
