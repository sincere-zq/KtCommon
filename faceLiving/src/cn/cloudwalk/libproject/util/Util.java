package cn.cloudwalk.libproject.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static final String AppName = "FaceRecog";

    public static final String FACE_THRESHOLD = "0";

    private static final String TAG = "Util";

    public static boolean DEBUG = true;

    /**
     * 判断当前语言环境是否为中文
     *
     * @return
     */
    public static boolean isChineseLanguage() {
        boolean isChineseLanguage;
        Locale l = Locale.getDefault();
        String language = l.getLanguage();
        String country = l.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            isChineseLanguage = true;
        } else {
            isChineseLanguage = false;
        }
        return isChineseLanguage;
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getFilePath(String suffix) {
        String filePath = FileUtil.parentPath + File.separator + FileUtil.DST_FOLDER_NAME + File.separator
                + Util.getTimeStamp() + "" + suffix;
        Log.e(TAG, filePath);
        return filePath;
    }

    public static String getBeBetweenTime(long start_time) {
        long video_time = (System.currentTimeMillis() - start_time);
        video_time /= 1000;
        int secs = (int) (video_time % 60);
        video_time /= 60;
        int mins = (int) (video_time % 60);
        video_time /= 60;
        long hours = video_time;

        return  hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
    }

    /**
     * 从输入流中读取数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();// 网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }

    public final static String MD5(String pwd) {
        // 用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = pwd.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { // i = 0
                byte byte0 = md[i]; // 95
                str[k++] = md5String[byte0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[byte0 & 0xf]; // F
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取缓存地址
     *
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        }
        if (cachePath == null)
            cachePath = context.getCacheDir().getPath();

        return cachePath;
    }

    public static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;

        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }

    /**
     * 判断屏幕
     *
     * @param ctx
     * @return
     */
    public static boolean isScreenPortrait(Context ctx) {

        Configuration mConfiguration = ctx.getResources().getConfiguration(); // 获取设置的配置信息
        int ori = mConfiguration.orientation; // 获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            // 横屏
            return false;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            // 竖屏
            return true;
        }
        return true;
    }

    /**
     * 获取状态栏高度
     *
     * @param ctx
     * @return
     */
    public static int getStatusBarHeight(Context ctx) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 40;//40状态栏高度默认40
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = ctx.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }
}
