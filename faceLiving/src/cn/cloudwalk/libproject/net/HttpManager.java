/**
 * Project Name:cwFaceForDev3
 * File Name:HttpManager.java
 * Package Name:cn.cloudwalk.dev.mobilebank.util
 * Date:2016-5-10 11:55:20
 * Copyright @ 2010-2016 Cloudwalk Information Technology Co.Ltd All Rights Reserved.
 */

package cn.cloudwalk.libproject.net;

import android.os.AsyncTask;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.cloudwalk.libproject.util.LogUtils;

/**
 * ClassName: OkHttpManager <br/>
 * Description:<br/>
 * date: 2016-5-10 11:55:20 <br/>
 *
 * @author 284891377
 * @version
 * @since JDK 1.7
 */
public class HttpManager {
    private static final String TAG = LogUtils.makeLogTag("HttpManager");

    public static void postAsync(final String url, final List<BasicNameValuePair> pairs,
                                 final DataCallBack dataCallBack) {
        new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object... param) {
                String result = null;
                try {
                    result = ApacheHttpUtil.post(url, pairs);
                    pairs.clear();
                } catch (OutOfMemoryError e) {

                    e.printStackTrace();
                }
                return result;
            }

            protected void onPostExecute(String result) {

                try {
                    JSONObject jb = new JSONObject(result);
                    if (jb.optInt("result") == 0) {
                        dataCallBack.requestSucess(jb);
                    } else {
                        String errorMsg = jb.optString("info");
                        dataCallBack.requestFailure("错误码:" + jb.optInt("result") + " 错误信息:" +
                                errorMsg);
                    }
                } catch (Exception e) {

                    dataCallBack.requestFailure("网络异常,请检查网络!");
                    e.printStackTrace();

                }

            }

            ;
        }.execute("");
    }

    /****************** 数据回掉接口 ****************************/
    public interface DataCallBack {
        void requestFailure(String errorMsg);

        void requestSucess(JSONObject jb);
    }

    /****************** api ****************************/
    //后端活体POC
    public static void cwFaceSerLivess(String ipStr,String app_id, String app_secret,String faceInfo,DataCallBack dataCallBack){
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        pairs.add(new BasicNameValuePair("app_id", app_id));
        pairs.add(new BasicNameValuePair("app_secret", app_secret));
        pairs.add(new BasicNameValuePair("param", faceInfo));
        postAsync(ipStr + "/faceliveness", pairs, dataCallBack);
    }

}
