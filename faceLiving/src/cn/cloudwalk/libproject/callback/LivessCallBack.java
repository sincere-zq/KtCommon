package cn.cloudwalk.libproject.callback;

/**
 * Created by Mr.Rain on 2016/12/8.
 * 后端防Hack攻击所需接口
 */
public interface LivessCallBack {
    /**
     *
     * @param bestface 最佳人脸数据帧(原始尺寸480x640)
     * @param bestInfo 最佳人脸关键点等信息
     * @param nextface 最佳人脸下一帧数据帧(原始尺寸480x640)
     * @param nextInfo 最佳人脸下一帧关键点等信息
     * @param clipedBestFaceImgData 裁剪后的最佳人脸(从最佳人脸原图中扣出人脸区域)
     */
    public void OnLivessSerResult(byte[] bestface, String bestInfo, byte[] nextface, String
            nextInfo,byte[] clipedBestFaceImgData,boolean isUpload);
}
