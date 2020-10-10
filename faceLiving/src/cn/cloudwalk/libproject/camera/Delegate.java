package cn.cloudwalk.libproject.camera;

/**
 * Create User:yusr Date:2016/6/2015:46 Copyright @ 2010-2016 Cloudwalk
 * Information Technology Co.Ltd All Rights Reserved.
 */
public interface Delegate {

	/**
	 * 处理打开相机出错
	 */
	void onOpenCameraError();

	void onFocus(float x, float y);

	void onFocused();
}
