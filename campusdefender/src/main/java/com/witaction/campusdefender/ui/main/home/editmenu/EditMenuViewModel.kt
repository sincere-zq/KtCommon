package com.witaction.campusdefender.ui.main.home.editmenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.campusdefender.ui.*
import com.witaction.common.extension.launch

class EditMenuViewModel : ViewModel() {
    //是否是在编辑模式
    val isEdit by lazy { MutableLiveData<Boolean>(false) }
    //所有菜单
    val allMenuList by lazy { MutableLiveData<MutableList<TeacherMenuSection>>(ServiceLocalReponsitory.getMenu()) }
    //自定义菜单
    val customList by lazy { MutableLiveData<MutableList<TeacherMenu>>(ServiceLocalReponsitory.getMenuInfo()?.menuList?.customList) }
    //菜单信息
    val menuInfo by lazy { ServiceLocalReponsitory.getMenuInfo() }
    //保存自定义菜单结果
    val saveResult = MutableLiveData<BResp<Any>>()

    fun saveCustomMenus(menuInfo: String, ssosToken: String, ssosUserId: String) {
        launch {
            val request = BRequest()
            request.addParams("ModInfos", menuInfo)
            request.addParams("SsosToken", ssosToken)
            request.addParams("SsosUserId", ssosUserId)
            saveResult.value = ServiceReponsitory.saveCustomMenu(request)
        }
    }
}