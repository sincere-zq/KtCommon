package com.witaction.common.widget.camera;

public interface OnCaptureCallback {

    public void onCapture(byte[] jpgdata, int preWidth, int preHeight);
}