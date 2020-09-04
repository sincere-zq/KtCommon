package com.witaction.campusdefender.ui.main.my.account

import android.net.Uri
import android.view.View
import androidx.lifecycle.observe
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.ActivityAccountSetBinding
import com.witaction.campusdefender.event.EventBusMsg
import com.witaction.campusdefender.ui.ServiceLocalReponsitory
import com.witaction.campusdefender.ui.main.my.account.updatename.UpdateNameActivity
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.load
import com.witaction.common.extension.open
import com.witaction.common.utils.*
import com.witaction.common.widget.HeaderView
import com.witaction.common.widget.TakePickturePopupwindow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/**
 * 账号设置
 */
class AccountSetActivity : BVMActivity<ActivityAccountSetBinding, AccountSetViewModel>(),
    HeaderView.HeaderListener {

    override fun vmbinding(): Class<AccountSetViewModel> = AccountSetViewModel::class.java

    override fun viewbinding(): ActivityAccountSetBinding =
        ActivityAccountSetBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        GlobalUtil.setOnClickListener(
            vb.rlHeadAvator,
            vb.llPersonName,
            vb.tvChangePwd,
            vb.tvPersonFace,
            vb.tvChangePhone
        ) {
            when (this) {
                vb.rlHeadAvator -> {
                    val window = TakePickturePopupwindow(this@AccountSetActivity, {
                        takePicturesFromAlbum()
                    }, {
                        takePicturesFromCamera()
                    })
                    window.show(vb.rlHeadAvator)
                }
                vb.llPersonName -> {
                    open<UpdateNameActivity>()
                }
                vb.tvChangePwd -> {
                }
                vb.tvPersonFace -> {
                }
                vb.tvChangePhone -> {
                }
            }
        }
    }

    override fun initData() {
        vm.personInfo.observe(this) {
            it?.let { it1 ->
                vb.imgAvator.load(it1.avatarUrl) {
                    error(R.mipmap.icon_home_placeholder)
                }
                vb.tvPersonName.text = it1.name
            }
        }
        vm.uploadAvatorResp.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.upload_success)
                vm.personInfo.value?.avatarUrl = it.getSimpleData()?.avatarUrl.toString()
                vm.personInfo.value?.let { it1 ->
                    it1.avatarUrl = it.getSimpleData()?.avatarUrl.toString()
                    ServiceLocalReponsitory.saveUser(it1)
                    EventBus.getDefault().post(EventBusMsg.UserInfoMsg(true))
                }
            } else {
                toast(it.msg)
            }
        }
    }

    private fun takePicturesFromAlbum() {
        PhotoUtils.select(this) { uri, success, _ ->
            if (success) {
                uri?.let {
                    PhotoUtils.crop(this).apply {
                        setAspect(1, 1)
                        setOutput(AppConfig.HEAD_AVATOR, AppConfig.HEAD_AVATOR)
                    }.build(it) { cropUri, success, _ ->
                        cropUri?.let { it1 ->
                            if (success) {
                                vb.imgAvator.load(it1) {
                                    error(R.mipmap.icon_home_placeholder)
                                }
                                asBitmap(it1)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun takePicturesFromCamera() {
        PhotoUtils.camera(this) { uri, success, _ ->
            if (success) {
                uri?.let {
                    PhotoUtils.crop(this).apply {
                        setAspect(1, 1)
                        setOutput(AppConfig.HEAD_AVATOR, AppConfig.HEAD_AVATOR)
                    }.build(it) { cropUri, success, _ ->
                        cropUri?.let { it1 ->
                            if (success) {
                                vb.imgAvator.load(it1) {
                                    error(R.mipmap.icon_home_placeholder)
                                }
                                asBitmap(it1)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun asBitmap(uri: Uri) {
        showLoading()
        GlobalScope.launch(Dispatchers.IO) {
            val bitmap = BitmapUtils.getBitmapFormUri(this@AccountSetActivity, uri)
            val finalBitmap =
                BitmapUtils.rotateBitmapByDegree(
                    bitmap,
                    BitmapUtils.getBitmapDegree(UriTofilePath.getPath(this@AccountSetActivity, uri))
                )
            vm.uploadAvator(
                FileEncoder.encodeBase64Bitmap(finalBitmap)
            )
        }
    }

    override fun onRightSecondClick(view: View?) {
    }

    override fun onSubTitleClick(view: View?) {
    }

    override fun onLeftClick(view: View?) {
        onBackPressed()
    }

    override fun onRightClick(view: View?) {
    }

    override fun onRightTextClick(view: View?) {
    }
}
