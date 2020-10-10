package cn.cloudwalk.libproject.callback;

import android.graphics.Bitmap;

/**
 * 自定义比对接口
 * 
 * @author ysyh55
 * 
 */
public interface DefineRecognizeCallBack {
	/**
	 * 用户自定义图片验证
	 * 
	 * @param bestface
	 *            最佳人脸(jpg二进制数组)
	 */
	public void OnDefineFaceVerifyResult(byte[] bestface);

}
