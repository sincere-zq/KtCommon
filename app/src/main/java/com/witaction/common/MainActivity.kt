package com.witaction.common

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.permissionx.guolindev.PermissionX
import com.vincent.videocompressor.VideoCompress
import com.witaction.common.base.BVMActivity
import com.witaction.common.databinding.ActivityMainBinding
import com.witaction.common.utils.UriTofilePath
import com.witaction.common.utils.e
import com.witaction.common.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : BVMActivity<ActivityMainBinding, MViewModel>() {


    override fun vmbinding(): Class<MViewModel> = MViewModel::class.java

    override fun viewbinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView() {
        vb.btnSelect.setOnClickListener {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        val i =
                            Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(i, 66)
                    } else {
                        toast("拒绝了权限")
                    }
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 66 && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data;
            e(uri.toString())
            if (uri != null) {
                press(uri)
            }
        }
    }

    fun press(uri: Uri) {
        VideoCompress.compressVideoLow(
            UriTofilePath.getPath(this, uri),
            getExternalStorageDirectory().absolutePath + "/test.mp4",
            object : VideoCompress.CompressListener {
                override fun onSuccess() {
                }

                override fun onFail() {
                }

                override fun onProgress(percent: Float) {
                    e(percent.toString())
                    toast(percent.toString())
                }

                override fun onStart() {
                }

            }
        )
    }

    override fun initData() {
        vm.cityListResult.observe(this, Observer {
            if (it.code == 0)
                vb.loadingview.hide()
            else
                vb.loadingview.error(it.msg, R.mipmap.ic_net_error)

        })
        vm.getCityList()
    }

    fun aync() {
        lifecycleScope.launch(Dispatchers.Main) {
            e("time:${System.currentTimeMillis()}");
            val data1 = async { getData() }
            val data2 = async { getData2() }
            upDate(data1.await(), data2.await())
        }
    }

    fun upDate(data1: String, data2: String) {
        e("data1:$data1 data2:$data2 ${System.currentTimeMillis()}")
        vb.loadingview.error("网络连接已断开", R.mipmap.ic_net_error)
    }

    fun common() {
        lifecycleScope.launch(Dispatchers.Main) {
            e("开始：${System.currentTimeMillis()}")
            val result = getData()
            e("结果:" + result)
            vb.loadingview.error("网络连接已断开", R.mipmap.ic_net_error)
            e("结束：${System.currentTimeMillis()}")
        }
    }

    suspend fun getData() = withContext(Dispatchers.IO) {
        e("线程名字:${Thread.currentThread().name}")
        Thread.sleep(3000)
        "data1"
    }

    suspend fun getData2() = withContext(Dispatchers.IO) {
        Thread.sleep(2000)
        "data2"
    }

}
