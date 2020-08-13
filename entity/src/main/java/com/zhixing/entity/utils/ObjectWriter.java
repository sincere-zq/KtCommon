package com.zhixing.entity.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;

/**
 * create by Xiao on 2019/5/13
 */
public class ObjectWriter {
    /**
     * 写入本地文件
     *
     * @param context
     * @param obj
     * @param fileName
     */
    public static void write(Context context, Object obj, String fileName) {
        try {
            String name = context.getPackageName().replace(".", "_");
            File rootFile = new File(Environment.getExternalStorageDirectory().getPath() + MessageFormat.format("/{0}greenDao", name));
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(obj);
            oout.flush();
            oout.close();
            bout.close();
            byte[] b = bout.toByteArray();
            File file = new File(Environment.getExternalStorageDirectory().getPath() + MessageFormat.format("/{0}greenDao/", name), fileName);
            FileOutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();
        } catch (Exception e) {
        } finally {

        }
    }

    /**
     * 从本地文件读取
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Object read(Context context, String fileName) {
        // 拿出持久化数据
        Object obj = null;
        try {
            String name = context.getPackageName().replace(".", "_");
            File file = new File(Environment.getExternalStorageDirectory().getPath() + MessageFormat.format("/{0}greenDao/", name), fileName);
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(in);
            obj = oin.readObject();
            in.close();
            oin.close();
        } catch (Exception e) {
        }
        return obj;
    }

    public static boolean clear(Context context) {
        try {
            String name = context.getPackageName().replace(".", "_");
            File file = new File(Environment.getExternalStorageDirectory().getPath() + MessageFormat.format("/{0}greenDao/", name), "Configuration");
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
