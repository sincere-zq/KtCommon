package com.witaction.common.utils;

/**
 * Created by WangYQ on 2017/7/12.
 */


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil {
    /**
     * DES加密
     *
     * @param encryptKey    key
     * @param iv            向量
     * @param encryptString 加密内容
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptKey, String iv, String encryptString) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes("utf-8"));
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("utf-8"), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
        byte[] byteArr = Base64.encode(encryptedData, Base64.NO_WRAP);
        return new String(byteArr, "utf-8");
    }

    /**
     * DES解密
     *
     * @param decryptKey    key
     * @param iv            向量
     * @param decryptString 解密内容
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptKey, String iv, String decryptString) throws Exception {
        byte[] byteMi = Base64.decode(decryptString.getBytes("utf-8"), Base64.DEFAULT);
        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes("utf-8"));
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("utf-8"), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData, "utf-8");
    }
}