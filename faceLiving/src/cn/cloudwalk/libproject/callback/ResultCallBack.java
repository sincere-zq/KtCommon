/**
 * Project Name:sdkproject
 * File Name:ResultCallBack.java
 * Package Name:cn.cloudwalk.libproject.callback
 * Date:2016年6月16日下午1:44:34
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 *
*/

package cn.cloudwalk.libproject.callback;

import java.util.HashMap;

/**
 * ClassName:ResultCallBack <br/>
 * Description: <br/>
 * Date: 2016年6月16日 下午1:44:34 <br/>
 * 
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public interface ResultCallBack {
	/**
	 * 
	 * result:活体,人脸比对结果回掉 <br/>
	 * 
	 * @author:284891377 Date: 2016年6月16日 下午1:50:27
	 * @param isLivePass
	 * @param isVerfyPass
	 * @param faceSessionId
	 * 
	 * @param face_score
	 * @param resultType
	 * @param bestFaceImgData
	 * @param liveImgDatas
	 * @since JDK 1.7
	 */
	public void result(boolean isLivePass, boolean isVerfyPass, String faceSessionId, double face_score, int resultType,
			byte[] bestFaceImgData,byte[] clipedBestFaceImgData, HashMap<Integer, byte[]> liveImgDatas);
}
