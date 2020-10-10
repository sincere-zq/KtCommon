package cn.cloudwalk.libproject.callback;

public interface OnCaptureCallback {

    public void onCapture(byte[] jpgdata, int preWidth, int preHeight);
}