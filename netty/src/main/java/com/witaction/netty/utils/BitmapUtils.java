package com.witaction.netty.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

import edu.dbke.socket.cp.util.LogUtils;

/**
 * Created by Zhangs on 2017/2/28.
 */

public class BitmapUtils {
    /**
     * 采样率压缩  按照图片宽高自动计算缩放比，图片质量有保障
     *
     * @param filePath  设置宽高并不是设置图片实际宽高，而是根据宽高自动计算缩放比，压缩后图片不会变形，宽高会根据计算的缩放比同时缩放，
     *                  宽高建议都设置300   设置300后图片大小为100-200KB，图片质量能接受；设置为400到500，图片大小为500-600kb，上传偏大，可自行设置
     * @param reqHeight
     * @param reqWidth
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqHeight, int reqWidth) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        //计算图片的缩放值
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)   //API 19

        {
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)  //API 12

        {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * 这个可以压缩到指定的宽，但是图片大小可能达不到预期，图片本身较小的可以使用，图片较大的建议使用上一个压缩方式
     * 根据自定义宽度设置图片大小，高度自适应  0不压缩
     *
     * @param path
     * @param width
     * @return
     */
    public static Bitmap createScaledBitemap(String path, int width) {
        Bitmap bit = BitmapFactory.decodeFile(path);
        int bitWidth = bit.getWidth();//得到图片宽
        float scaleWidth = ((float) width) / ((float) bitWidth);//计算宽度缩放比例
        if (width == 0) {
            return bit;
        } else {
            int height = (int) (bit.getHeight() * scaleWidth);//根据宽度缩放比例设置高度
            Bitmap bitmap = Bitmap.createScaledBitmap(bit, width, height, true);
            return bitmap;
        }
    }

    /**
     * 这是个保存Bitmap到sd卡中的方法，可以返回保存图片的路径
     * 保存Bitmap到sd
     *
     * @param mBitmap
     * @param bitName 图片保存的名称，返回存储图片的路径
     */
    public static String saveBitmap(Context context, Bitmap mBitmap, String bitName) {
        File f;
        //判断是否有sd卡 有就保存到sd卡，没有就保存到app缓存目录
        if (isStorage()) {
            File file = new File("/data/data/name");//保存的路径
            if (!file.exists()) {//判断目录是否存在
                file.mkdir();//不存在就创建目录
            }
            f = new File(file, bitName + ".jpg");
        } else {
            File file = new File(context.getCacheDir().toString());
            if (!file.exists()) {//判断目录是否存在
                file.mkdir();
            }
            f = new File(file, bitName + ".jpg");
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fOut != null) {
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.toString();
    }

    /**
     * 判断是否有sd卡
     *
     * @return
     */
    public static boolean isStorage() {
        boolean isstorage = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        return isstorage;
    }

    /**
     * 把Bimtmap转成Base64，用于上传图片到服务器，一般是先压缩然后转成Base64，在上传
     */
    public static String getBitmapStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//jpeg格式
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * 把Bimtmap转成jpg 模式的Base64，用于上传图片到服务器，一般是先压缩然后转成Base64，在上传
     */
    public static String getBitmapJpgStrBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }


    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 把Base64转换成Bitmap
    public static Bitmap getBitmapFromBase64(String iconBase64) {
        if (TextUtils.isEmpty(iconBase64)) {
            return null;
        }
        byte[] bitmapArray = Base64.decode(iconBase64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    /**
     * 把图片byte流转换成bitmap
     */
    public static Bitmap byteToBitmap(byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int i = 0;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                b = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                break;
            }
            i += 1;
        }
        return b;
    }

    /**
     * 转成RGB_565照片，才能进行人脸检测
     *
     * @param tmp
     * @return
     */
    public static Bitmap getRGB_565Bitmap(byte[] tmp) {
        BitmapFactory.Options bitmapOption = new BitmapFactory.Options();
        bitmapOption.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap map = BitmapFactory.decodeByteArray(tmp, 0, tmp.length, bitmapOption);
        return map;
    }


    /**
     * 保持bitmap 到SD卡
     *
     * @param bitmap
     * @param path
     * @throws IOException
     */
    public static void saveBitmap(Bitmap bitmap, String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转Bitmap
     *
     * @param bm
     * @param degree
     * @return
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            if (uri != null) {
                bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 读取资源图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 将base64字符保存成文件
     *
     * @param base64Code 照片字符
     * @param savePath   照片路径
     */
    public static void decoderBase64File(String base64Code, String savePath) {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(savePath);
            out.write(buffer);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将base64字符保存成文件
     *
     * @param buffer   照片字符
     * @param savePath 照片路径
     */
    public static void decoderBase64File(byte[] buffer, String savePath) {
        FileOutputStream out = null;
        try {
            File file = new File(savePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(savePath);
            out.write(buffer);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param @param  base64String
     * @param @return 设定文件
     * @return Bitmap    返回类型
     * @throws
     * @Title: base64ToBitmap
     * @Description: TODO(base64l转换为Bitmap)
     */
    public static Bitmap base64ToBitmap(String base64String) {

        byte[] decode = Base64.decode(base64String, Base64.DEFAULT);

        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);

        return bitmap;
    }


    public static Bitmap byteToDoBitmap(byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @param @param  base64String
     * @param @return 设定文件
     * @return Bitmap    返回类型
     * @throws
     * @Title: nv21ToBitmap
     * @Description: TODO(nv21转换为Bitmap)
     */
    public static Bitmap nv21ToBitmap(byte[] nv21, int width, int height, int degress) {
        Bitmap bitmap = null;
        try {
            YuvImage image = new YuvImage(nv21, ImageFormat.NV21, width, height, null);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compressToJpeg(new Rect(0, 0, width, height), 100, stream);
            byte[] tmp = stream.toByteArray();
//            bitmap = BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeByteArray(tmp, 0, tmp.length, options);
            stream.close();
            if (degress > 0) {
                Matrix m = new Matrix();
                m.postRotate(degress);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
