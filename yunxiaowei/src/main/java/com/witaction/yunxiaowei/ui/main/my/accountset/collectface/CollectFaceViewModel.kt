package com.witaction.yunxiaowei.ui.main.my.accountset.collectface

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.PersonFace
import com.witaction.yunxiaowei.framwork.ServerRepository

class CollectFaceViewModel : ViewModel() {
    lateinit var personId: String

    val personFaceList by lazy { MutableLiveData<BResp<PersonFace>>() }

    val delFaceResult by lazy { MutableLiveData<BResp<Any>>() }

    val uploadFaceResult by lazy { MutableLiveData<BResp<PersonFace>>() }

    fun getPersonFaceList() {
        launch {
            personFaceList.value =
                ServerRepository.getPersonFaceList(mutableMapOf("PersonId" to personId))
        }
    }

    fun deleteFace(faceId: String) {
        launch {
            delFaceResult.value = ServerRepository.deleteFace(
                mutableMapOf(
                    "PersonId" to personId,
                    "FaceId" to faceId
                )
            )
        }
    }

    fun uploadFace(content: String, sortCode: String) {
        launch {
            uploadFaceResult.value = ServerRepository.uploadFace(
                mutableMapOf(
                    "PersonId" to personId,
                    "Content" to content,
                    "SortCode" to sortCode
                )
            )
        }
    }
}