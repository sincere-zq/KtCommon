package com.witaction.common.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.annotation.IntRange
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.witaction.common.utils.UriTofilePath.getPath
import java.io.File
import kotlin.random.Random

/**
 * 系统相册、相机
 */
class PhotoUtils {

    companion object {
        @Throws(Exception::class)
        fun getBitmapBase64FromUri(uri: Uri, context: Activity): String? {
            val bitmap = BitmapUtils.getBitmapFormUri(context, uri)
            val finalBitmap = BitmapUtils.rotateBitmapByDegree(
                bitmap, BitmapUtils.getBitmapDegree(
                    getPath(context, uri)
                )
            )
            return FileEncoder.encodeBase64Bitmap(finalBitmap)
        }

        private val authorities = "${GlobalUtil.appPackage}.provider"
        /**
         * 调用裁剪功能
         */
        @JvmStatic
        fun crop(manager: FragmentManager): Crop = Crop(manager)

        /**
         * 调用裁剪功能
         */
        @JvmStatic
        fun crop(activity: FragmentActivity): Crop = Crop(activity.supportFragmentManager)

        /**
         * 调用裁剪功能
         */
        @JvmStatic
        fun crop(fragment: Fragment): Crop = Crop(fragment.childFragmentManager)


        class Crop(private val manager: FragmentManager) {

            private var aspectX = 0
            private var aspectY = 0
            private var outputX = 0
            private var outputY = 0

            /**
             * 设置比例
             */
            fun setAspect(@IntRange(from = 0) aspectX: Int, @IntRange(from = 0) aspectY: Int): Crop {
                this.aspectX = aspectX
                this.aspectY = aspectY
                return this
            }

            /**
             * 设置输出图片的宽高
             */
            fun setOutput(@IntRange(from = 0) width: Int, @IntRange(from = 0) height: Int): Crop {
                this.outputX = width
                this.outputY = height
                return this
            }

            fun build(
                uri: Uri,
                cropCallBack: (cropUri: Uri?, success: Boolean, msg: String) -> Unit
            ) {
                crop(manager, uri, aspectX, aspectY, outputX, outputY, cropCallBack)
            }
        }

        /**
         * 打开相册
         */
        @JvmStatic
        fun select(
            manager: FragmentManager,
            photoCallBack: (uri: Uri?, success: Boolean, msg: String) -> Unit
        ) = getPhotoFragment(manager).select(photoCallBack)

        /**
         * 打开相册
         */
        @JvmStatic
        fun select(
            activity: FragmentActivity,
            photoCallBack: (uri: Uri?, success: Boolean, msg: String) -> Unit
        ) = getPhotoFragment(activity.supportFragmentManager).select(photoCallBack)

        /**
         * 打开相册
         */
        @JvmStatic
        fun select(
            fragment: Fragment,
            photoCallBack: (uri: Uri?, success: Boolean, msg: String) -> Unit
        ) = getPhotoFragment(fragment.childFragmentManager).select(photoCallBack)

        /**
         * 调用相机拍照
         */
        @JvmStatic
        fun camera(
            manager: FragmentManager,
            photoCallBack: (uri: Uri?, success: Boolean, msg: String) -> Unit
        ) = getPhotoFragment(manager).camera(photoCallBack)

        /**
         * 调用相机拍照
         */
        @JvmStatic
        fun camera(
            activity: FragmentActivity,
            photoCallBack: (uri: Uri?, success: Boolean, msg: String) -> Unit
        ) = getPhotoFragment(activity.supportFragmentManager).camera(photoCallBack)

        /**
         * 调用相机拍照
         */
        @JvmStatic
        fun camera(
            fragment: Fragment,
            photoCallBack: (uri: Uri?, success: Boolean, msg: String) -> Unit
        ) = getPhotoFragment(fragment.childFragmentManager).camera(photoCallBack)

        private fun crop(
            manager: FragmentManager,
            uri: Uri,
            aspectX: Int,
            aspectY: Int,
            outputX: Int,
            outputY: Int,
            cropCallBack: (Uri?, Boolean, String) -> Unit
        ) = getPhotoFragment(manager).crop(uri, aspectX, aspectY, outputX, outputY, cropCallBack)


    }

    class PhotoFragment : Fragment() {
        private val REQUEST_CODE_CROP = 601
        private val REQUEST_CODE_CAMERA = 602
        private val REQUEST_CODE_SELECT = 603
        private var cropCallback: ((Uri?, Boolean, String) -> Unit)? = null
        private var selectCallback: ((Uri?, Boolean, String) -> Unit)? = null
        private var cameraCallback: ((Uri?, Boolean, String) -> Unit)? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setRetainInstance(true)
        }


        lateinit var cameraPath: String
        //调用相机
        fun camera(photoCallBack: (Uri?, Boolean, String) -> Unit) {
            if (authorities.isNullOrBlank())
                throw RuntimeException("authority不能为空")

            this.cameraCallback = photoCallBack
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
            val file = File(
                requireContext().externalCacheDir!!.absolutePath,
                "${System.currentTimeMillis()}${Random.nextInt(9999)}.jpg"
            )
            cameraPath = file.absolutePath
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                FileProvider.getUriForFile(requireContext(), authorities, file)
            } else {
                Uri.fromFile(file)
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, REQUEST_CODE_CAMERA)
        }

        //选择图片
        fun select(photoCallBack: (Uri?, Boolean, String) -> Unit) {
            this.selectCallback = photoCallBack
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            startActivityForResult(
                intent, REQUEST_CODE_SELECT
            )
        }

        var outUri: Uri? = null
        //裁剪
        fun crop(
            uri: Uri,
            aspectX: Int,
            aspectY: Int,
            outputX: Int,
            outputY: Int,
            callBack: (Uri?, Boolean, String) -> Unit
        ) {
            if (authorities.isBlank())
                throw RuntimeException("请填写正确的authority")
            val uri1 = if (uri.scheme.equals(ContentResolver.SCHEME_FILE))
                FileProvider.getUriForFile(requireContext(), authorities, uri.toFile())
            else uri

            val cursor = requireContext().contentResolver.query(uri1, null, null, null, null, null)
            cursor?.let {
                it.moveToFirst()

                this.cropCallback = callBack
                val intent = Intent("com.android.camera.action.CROP")
                //文件名
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                outUri = Uri.fromFile(
                    File(
                        requireContext().externalCacheDir!!.absolutePath,
                        "crop${Random.nextInt(0, 9999)}$displayName"
                    )
                )
                requireContext().grantUriPermission(
                    requireContext().getPackageName(),
                    outUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.putExtra("noFaceDetection", true) //去除默认的人脸识别，否则和剪裁匡重叠
                intent.setDataAndType(uri1, requireContext().contentResolver.getType(uri))
                intent.putExtra("crop", "true") // crop=true 有这句才能出来最后的裁剪页面.
                intent.putExtra("output", outUri)
                intent.putExtra("outputFormat", "JPEG") // 返回格式
                var ax = aspectX
                var ay = aspectY
                if (ay != 0 && ay != 0) {
                    if (ax == ay && Build.MANUFACTURER == "HUAWEI") {
                        ax = 9998
                        ay = 9999
                    }
                    intent.putExtra("aspectX", ax) // 这两项为裁剪框的比例.
                    intent.putExtra("aspectY", ay) // x:y=1:2
                }
                if (outputX != 0 && outputY != 0) {
                    intent.putExtra("outputX", outputX)
                    intent.putExtra("outputY", outputY)
                }
                intent.putExtra("return-data", false)
                startActivityForResult(
                    intent, REQUEST_CODE_CROP
                )
            }
            cursor?.close()
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    REQUEST_CODE_CROP -> {
                        //裁剪
                        //把outUri转成
                        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            //把uri转成匿名uri
                            if (outUri?.scheme.equals(ContentResolver.SCHEME_FILE))
                                FileProvider.getUriForFile(
                                    requireContext(),
                                    authorities,
                                    outUri!!.toFile()
                                )
                            else outUri
                        } else outUri
                        cropCallback?.invoke(uri, true, "")
                        cropCallback = null
                    }
                    REQUEST_CODE_CAMERA -> {
                        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            FileProvider.getUriForFile(
                                requireContext(),
                                authorities, File(cameraPath)
                            )
                        } else {
                            Uri.fromFile(File(cameraPath))
                        }
                        cameraCallback?.invoke(uri, true, "")
                        cameraCallback = null
                    }
                    REQUEST_CODE_SELECT -> {
                        val uri = data?.data
                        selectCallback?.invoke(uri, true, "")
                        selectCallback = null
                    }
                }
            } else {
                when (requestCode) {
                    REQUEST_CODE_CROP -> {
                        //裁剪
                        cropCallback?.invoke(null, false, "裁剪失败")
                        cropCallback = null
                    }
                    REQUEST_CODE_CAMERA -> {

                        cameraCallback?.invoke(null, false, "拍照失败")
                        cameraCallback = null
                    }
                    REQUEST_CODE_SELECT -> {
                        selectCallback?.invoke(null, false, "选择图片失败")
                        selectCallback = null
                    }
                }
            }
        }


    }

}


private fun getPhotoFragment(manager: FragmentManager) =
    manager.findFragmentByTag("photoFragmen") as? PhotoUtils.PhotoFragment
        ?: PhotoUtils.PhotoFragment().apply {
            manager.beginTransaction()
                .add(this, "photoFragmen")
                .commitAllowingStateLoss()
            manager.executePendingTransactions()
        }