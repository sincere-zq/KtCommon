package com.witaction.campusdefender.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.ui.*
import com.witaction.common.base.BApp
import com.witaction.common.extension.launch
import com.witaction.common.utils.DateUtils
import com.witaction.common.utils.NetworkUtils
import kotlinx.coroutines.async

class HomeViewModel : ViewModel() {
    val homeDataResult = MutableLiveData<MutableList<MultiItemEntity>>()
    val loadingState = MutableLiveData<Int>(AppConfig.LoadingConfig.LOADING)

    /**
     * 加载首页底部事务提醒等信息  每次页面可见时都会加载
     * 显示规则
     * 家长端显示事务，没有事务不显示
     * 老师端显示日程，没有日程显示事务提醒，没有事务提醒不显示
     */
    fun getHomeData() {
        launch {
            loadingState.value = AppConfig.LoadingConfig.LOADING
            val user = ServiceLocalReponsitory.getUser()
            when (user?.selectType) {
                AppConfig.PersonConfig.STUDENT -> {
                }
                AppConfig.PersonConfig.TEACHER -> {
                    val bannerResult = async { getTopBanner() }

                    val menuResp = async { getTeacherMenu() }

                    val scheduleResp = async { getTodaySchedule() }

                    val transResp = async { getUserMsg() }

                    val realResult = mutableListOf<MultiItemEntity>()

                    handleBanner(bannerResult.await(), realResult)

                    handleTeacherMenu(menuResp.await(), realResult)

                    handleSchedule(scheduleResp.await(), realResult)

                    handleTransAlert(transResp.await(), realResult)

                    if (realResult.size == 0) {
                        if (!NetworkUtils.isConnected(BApp.context)) {
                            loadingState.value = AppConfig.LoadingConfig.NET_ERROR
                        } else {
                            loadingState.value = AppConfig.LoadingConfig.EMPTY_DATA
                        }
                    } else {
                        loadingState.value = AppConfig.LoadingConfig.COMPLETE
                    }

                    homeDataResult.value = realResult
                }
                AppConfig.PersonConfig.PARENT -> {
                    val bannerResult = async { getTopBanner() }

                    val menuResp = async { getParentMenu() }

                    val transResp = async { getUserMsg() }

                    val realResult = mutableListOf<MultiItemEntity>()

                    handleBanner(bannerResult.await(), realResult)

                    handleParentMenu(menuResp.await(), realResult)

                    handleTransAlert(transResp.await(), realResult)

                    if (realResult.size == 0) {
                        if (!NetworkUtils.isConnected(BApp.context)) {
                            loadingState.value = AppConfig.LoadingConfig.NET_ERROR
                        } else {
                            loadingState.value = AppConfig.LoadingConfig.EMPTY_DATA
                        }
                    } else {
                        loadingState.value = AppConfig.LoadingConfig.COMPLETE
                    }

                    homeDataResult.value = realResult
                }
            }
        }
    }

    private suspend fun getTopBanner() = ServiceReponsitory.getHomeTopBanner(BRequest())

    private suspend fun getTeacherMenu() = ServiceReponsitory.getTeacherMenu(BRequest())

    private suspend fun getParentMenu() = ServiceReponsitory.getParentMenu(BRequest())

    private suspend fun getUserMsg(): BResp<TransAlert> {
        val transReq = BRequest()
        transReq.addParams("IsRead", 0)
        transReq.addParams("PageSize", AppConfig.HOME_TRANS_NUM)
        return ServiceReponsitory.getUserMsg(transReq)
    }

    private suspend fun getTodaySchedule(): BResp<BusPlan> {
        val schduleReq = BRequest()
        schduleReq.addParams("PlanDate", DateUtils.getCurSystemDate())
        return ServiceReponsitory.getTeacherTodaySchedule(schduleReq)
    }

    /**
     * 处理banner数据
     */
    private fun handleBanner(
        realBannerResult: BResp<HomeBannerResult>,
        realResult: MutableList<MultiItemEntity>
    ) {
        realBannerResult.getSimpleData()?.let {
            it.itemType = HomeAdapter.TYPE_BANNER
            if (realBannerResult.isSuccess() && realBannerResult.data.size > 0 && it.adList.size > 0) {
                realResult.add(it)
            } else {
                it.adList.add(
                    HomeBanner(
                        "",
                        0,
                        ""
                    )
                )
                realResult.add(it)
            }
        }
    }

    /**
     * 处理老师菜单数据
     */
    private fun handleTeacherMenu(
        realMenuResult: BResp<TeacherMenuResult>,
        realResult: MutableList<MultiItemEntity>
    ) {
        if (realMenuResult.isSuccess()) {
            realMenuResult.getSimpleData()?.let {
                ServiceLocalReponsitory.saveMenuInfo(it)
                if (it.menuList.customList.size > 0) {
                    realResult.add(
                        TeacherMenuMultiItem(
                            HomeAdapter.TYPE_TEACHER_MENU,
                            it.menuList.getCustomMenus()
                        )
                    )
                }
                if (it.menuList.usuallyList.size > 0) {
                    realResult.add(
                        TeacherMenuMultiItem(
                            HomeAdapter.TYPE_TEACHER_MENU,
                            it.menuList.getUsuallyMenus()
                        )
                    )
                }
                ServiceLocalReponsitory.saveMenu(it.menuList.getAllTwoMenu())
            }
        }
    }

    /**
     * 处理家长菜单数据
     */
    private fun handleParentMenu(
        realMenuResult: BResp<ParentMenuResult>,
        realResult: MutableList<MultiItemEntity>
    ) {
        val sectionList = mutableListOf<ParentMenuSection>()
        if (realMenuResult.isSuccess()) {
            for (i in realMenuResult.data.indices) {
                val groupData = realMenuResult.data[i]
                val header =
                    ParentMenuSection(groupData.name)
                sectionList.add(header)
                for (j in groupData.menuList.indices) {
                    val child =
                        ParentMenuSection(
                            groupData.menuList[j]
                        )
                    sectionList.add(child)
                }
            }
        }
        if (sectionList.size > 0) {
            realResult.add(
                ParentMenuMultiItem(
                    HomeAdapter.TYPE_PARENT_MENU,
                    sectionList
                )
            )
        }
    }

    /**
     * 处理行程数据
     */
    private fun handleSchedule(
        schduleResp: BResp<BusPlan>,
        realResult: MutableList<MultiItemEntity>
    ) {
        if (schduleResp.isSuccess() && schduleResp.data.size > 0) {
            realResult.add(
                ScheduleMultiItem(
                    HomeAdapter.TYPE_SCHEDULE,
                    schduleResp.data
                )
            )
        }
    }

    /**
     * 处理事务提醒数据
     */
    private fun handleTransAlert(
        transAlert: BResp<TransAlert>,
        realResult: MutableList<MultiItemEntity>
    ) {
        if (transAlert.isSuccess() && transAlert.data.size > 0) {
            realResult.add(
                TransAlertMultiItem(
                    HomeAdapter.TYPE_TRANS,
                    transAlert.data
                )
            )
        }
    }
}