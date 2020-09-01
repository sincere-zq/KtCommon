package com.witaction.common.utils

import android.text.TextUtils
import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * 根据文件获取base64 utf-8编码字符串
 *
 * @param filePath
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
fun encoderFileToUtf8(filePath: String?): String? {
    val bytes = encodeBase64File(filePath)
    return bytes?.let { encoderByteToUtf8(it) } ?: ""
}

/**
 * @return 把byte[]数组转码为utf-8的字符串
 * @throws UnsupportedEncodingException
 */
@Throws(UnsupportedEncodingException::class)
fun encoderByteToUtf8(bytes: ByteArray?): String? {
    return String(bytes!!, StandardCharsets.UTF_8)
}

/**
 * 根据文件获取base64 utf-8编码字符串  URL编码
 */
@Throws(Exception::class)
fun URLEncoderFileToUtf8(filePath: String?): String? {
    val bytes = encodeBase64File(filePath)
    return bytes?.let { URLEncoderByteToUtf8(it) } ?: ""
}

/**
 * @return 把byte[]数组转码为utf-8的字符串 URL编码
 * @throws UnsupportedEncodingException
 */
@Throws(UnsupportedEncodingException::class)
fun URLEncoderByteToUtf8(bytes: ByteArray?): String? {
    return URLEncoder.encode(String(bytes!!, StandardCharsets.UTF_8), "utf-8")
}

/**
 * 根据文件获取base64 byte数组
 *
 * @param path
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
fun encodeBase64File(path: String?): ByteArray? {
    if (TextUtils.isEmpty(path)) {
        return null
    }
    val file = File(path)
    val inputFile = FileInputStream(file)
    val buffer = ByteArray(file.length().toInt())
    inputFile.read(buffer)
    inputFile.close()
    return Base64.encode(buffer, Base64.NO_WRAP)
}

@Throws(Exception::class)
fun encodeString(string: String): String? {
    return Base64.encodeToString(string.toByteArray(), Base64.NO_WRAP)
}

/**
 * 将base64字符保存文本文件
 *
 * @param base64Code
 * @param targetPath
 * @throws Exception
 */
@Throws(Exception::class)
fun toFile(base64Code: String, targetPath: String?) {
    val buffer = base64Code.toByteArray()
    val out = FileOutputStream(targetPath)
    out.write(buffer)
    out.close()
}