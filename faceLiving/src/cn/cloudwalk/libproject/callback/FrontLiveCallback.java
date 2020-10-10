package cn.cloudwalk.libproject.callback;

/**
 * 前端活体完成回调
 * 作为参数同LivessCallBack的替代接口,参数同LivessCallBack.OnLivessSerResult
 * 在前端活体动作完成时回调,参数可做为请求后端防攻击数据
 * 当开启后端防攻击时(Builder.isServerLive=true),isFrontLiveSuccess默认返回true
 * 当开启前端防攻击时(Builder.isFrontHack=true),isFrontLiveSuccess返回前端防攻击结果
 */

public interface FrontLiveCallback {
    /**
     * @param bestface              最佳人脸数据帧
     * @param bestInfo              最佳人脸关键点等信息
     * @param nextface              最佳人脸下一帧数据帧
     * @param nextInfo              最佳人脸下一帧关键点等信息
     * @param clipedBestFaceImgData 裁剪后的最佳人脸
     * @param isFrontLiveSuccess    前端活体是否通过
     */
    void onFrontLivessFinished(byte[] bestface, String bestInfo, byte[] nextface, String
            nextInfo, byte[] clipedBestFaceImgData, boolean isFrontLiveSuccess);
}
