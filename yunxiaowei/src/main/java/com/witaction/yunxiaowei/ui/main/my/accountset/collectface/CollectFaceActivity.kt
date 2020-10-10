package com.witaction.yunxiaowei.ui.main.my.accountset.collectface

import androidx.lifecycle.observe
import cn.cloudwalk.FaceInterface
import cn.cloudwalk.libproject.Bulider
import cn.cloudwalk.libproject.LiveStartActivity
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.load
import com.witaction.common.utils.BitmapUtils
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.ConfirmDialog
import com.witaction.yunxiaowei.PersonFace
import com.witaction.yunxiaowei.R
import com.witaction.yunxiaowei.databinding.ActivityCollectFaceBinding
import com.witaction.yunxiaowei.framwork.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * 人脸采集
 */
class CollectFaceActivity : BVMActivity<ActivityCollectFaceBinding, CollectFaceViewModel>() {
    companion object {
        const val PERSON_ID = "PERSON_ID"
    }

    override fun vmbinding(): Class<CollectFaceViewModel> = CollectFaceViewModel::class.java

    override fun viewbinding(): ActivityCollectFaceBinding =
        ActivityCollectFaceBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        GlobalUtil.setOnClickListener(vb.imgFirstFace, vb.imgSecondFace, vb.imgThirdFace) {
            when (this) {
                vb.imgFirstFace -> {
                    handleFace(0)
                }
                vb.imgSecondFace -> {
                    handleFace(1)
                }
                vb.imgThirdFace -> {
                    handleFace(2)
                }
            }
        }
    }

    override fun initData() {
        val personId = intent.getStringExtra(PERSON_ID)
        if (personId.isNullOrEmpty()) {
            vm.personId = LocalRepository.getUserInfo()?.personId.toString()
        } else {
            vm.personId = personId
        }
        vm.personFaceList.observe(this) {
            if (it.isSuccess()) {
                setFaceList(it.data)
            }
        }
        vm.delFaceResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.delete_success)
                vm.getPersonFaceList()
            } else {
                toast(it.msg)
            }
        }
        vm.uploadFaceResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                toast(R.string.upload_success)
                vm.getPersonFaceList()
            } else {
                toast(it.msg)
            }
        }
        vm.getPersonFaceList()
    }

    private fun setFaceList(faceList: MutableList<PersonFace>) {
        for (index in faceList.indices) {
            when (index) {
                0 -> {
                    if (faceList[index].imageFilePath.isNotEmpty()) {
                        vb.imgFirstFace.load(faceList[index].imageFilePath) {
                            error(R.mipmap.ico_im_no_photo_default_image)
                        }
                    } else {
                        vb.imgFirstFace.setImageResource(R.drawable.layer_list_collect_face)
                    }
                }
                1 -> {
                    if (faceList[index].imageFilePath.isNotEmpty()) {
                        vb.imgSecondFace.load(faceList[index].imageFilePath) {
                            error(R.mipmap.ico_im_no_photo_default_image)
                        }
                    } else {
                        vb.imgSecondFace.setImageResource(R.drawable.layer_list_collect_face)
                    }
                }
                2 -> {
                    if (faceList[index].imageFilePath.isNotEmpty()) {
                        vb.imgThirdFace.load(faceList[index].imageFilePath) {
                            error(R.mipmap.ico_im_no_photo_default_image)
                        }
                    } else {
                        vb.imgThirdFace.setImageResource(R.drawable.layer_list_collect_face)
                    }
                }
            }
        }
    }

    private fun handleFace(index: Int) {
        vm.personFaceList.value?.run {
            if (data[index].imageFilePath.isEmpty()) {
                getFace(data[index].sortCode)
            } else {
                var faceNum = 0
                for (face in data) {
                    if (face.imageFilePath.isNotEmpty()) {
                        faceNum++
                    }
                }
                if (faceNum > 1) {
                    deleteFace(data[index].faceId)
                } else {
                    tipOnlyOneFace()
                }
            }
        }
    }

    private fun getFace(sortCode: String) {
        val liveList = mutableListOf(
            FaceInterface.LivessType.LIVESS_MOUTH,
            FaceInterface.LivessType.LIVESS_HEAD_LEFT,
            FaceInterface.LivessType.LIVESS_HEAD_RIGHT,
            FaceInterface.LivessType.LIVESS_EYE
        )
        Bulider().apply {
            setFrontLiveFace { bestface, bestInfo, _, _, _, isFrontLiveSuccess ->
                if (bestface.isNotEmpty() && bestInfo.isNotEmpty() && isFrontLiveSuccess) {
                    setFaceResult(this@CollectFaceActivity, Bulider.FACE_LIVE_PASS, 0.0, "", "")
                    val content = BitmapUtils.byteToBase64(bestface)
                    GlobalScope.launch(Dispatchers.Main) {
                        showLoading()
                        vm.uploadFace(content, sortCode)
                    }
                } else {
                    setFaceResult(this@CollectFaceActivity, Bulider.FACE_LIVE_FAIL, 0.0, "", "")
                    toast(R.string.not_check_face)
                }
            }
            isFrontHack(true)
            isResultPage(false)
            isServerLive(false)
            setLives(liveList as ArrayList<Int>, 2, true, false, Bulider.liveLevel)
            startActivity(this@CollectFaceActivity, LiveStartActivity::class.java)
        }
    }

    private fun deleteFace(faceId: String) {
        ConfirmDialog.build(this) {
            message { GlobalUtil.getString(R.string.confirm_delete_this_face) }
            cancelListener { { it.dismiss() } }
            confirmListener {
                {
                    it.dismiss()
                    showLoading()
                    vm.deleteFace(faceId)
                }
            }
        }.show()
    }

    private fun tipOnlyOneFace() {
        ConfirmDialog.build(this) {
            message { GlobalUtil.getString(R.string.only_one_face_tips) }
            cancelListener { { it.dismiss() } }
            confirmListener { { it.dismiss() } }
        }.show()
    }
}
