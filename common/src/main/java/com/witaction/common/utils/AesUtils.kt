package com.witaction.common.utils

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec


private const val charset = "UTF-8"

@Throws(
    InvalidKeyException::class,
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidAlgorithmParameterException::class,
    IllegalBlockSizeException::class,
    BadPaddingException::class,
    UnsupportedEncodingException::class
)
fun aesEncryptString(content: String, key: String): String? {
    val contentBytes = content.toByteArray(charset(charset))
    val keyBytes = key.toByteArray(charset(charset))
    val encryptedBytes = aesEncryptBytes(contentBytes, keyBytes)
    return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
}


@Throws(
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidKeyException::class,
    InvalidAlgorithmParameterException::class,
    IllegalBlockSizeException::class,
    BadPaddingException::class,
    UnsupportedEncodingException::class
)
fun aesEncryptBytes(
    contentBytes: ByteArray,
    keyBytes: ByteArray
): ByteArray {
    return cipherOperation(contentBytes, keyBytes, Cipher.ENCRYPT_MODE)
}

@Throws(
    UnsupportedEncodingException::class,
    NoSuchAlgorithmException::class,
    NoSuchPaddingException::class,
    InvalidKeyException::class,
    InvalidAlgorithmParameterException::class,
    IllegalBlockSizeException::class,
    BadPaddingException::class
)
fun cipherOperation(
    contentBytes: ByteArray,
    keyBytes: ByteArray,
    mode: Int
): ByteArray {
    val secretKey =
        SecretKeySpec(keyBytes, "AES")
    val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
    cipher.init(mode, secretKey)
    return cipher.doFinal(contentBytes)
}