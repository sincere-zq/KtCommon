package com.witaction.campusdefender.ui

import android.text.TextUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.entity.SectionEntity
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.witaction.common.NetConfig
import com.witaction.plat.PlatLocalReponsitory
import java.io.Serializable


data class BResp<T>(
    @SerializedName("IsSuccess")
    val isSuccess: Int,
    @SerializedName("Message")
    val msg: String,
    @SerializedName("ErrorCode")
    val errorCode: String,
    @SerializedName("Data")
    val data: MutableList<T>
) {
    fun isSuccess() = isSuccess == 1
    fun getSimpleData(): T? = if (data.size > 0) data[0] else null
}

data class BRequest(
    @SerializedName("ApiKey")
    val apiKey: String = NetConfig.API_KEY,
    @SerializedName("User")
    val user: JsonObject = JsonObject(),
    @SerializedName("Param")
    val params: HashMap<String, Any> = HashMap()
) : Serializable {
    init {
        val userInfo = ServiceLocalReponsitory.getUser()
        if (userInfo == null) {
            user.addProperty("SchoolId", PlatLocalReponsitory.getPlat()?.sysID)
            user.addProperty("SelectType", 0)
            user.addProperty("Type", 0)
        } else {
            user.addProperty("Id", userInfo.id)
            user.addProperty("Name", userInfo.name)
            user.addProperty("Phone", userInfo.account)
            user.addProperty("SchoolId", userInfo.schoolId)
            user.addProperty("SelectType", userInfo.selectType)
            user.addProperty("Token", userInfo.token)
            user.addProperty("Type", userInfo.type)
            user.addProperty("UUCAppId", userInfo.uucAppId)
            user.addProperty("UUCAppToken", userInfo.uucAppToken)
            user.addProperty("UUCLoginUrl", userInfo.uucLoginUrl)
        }
        params["CurrentPage"] = 1
        params["PageSize"] = 20
    }

    fun addParams(key: String, value: Any) {
        params[key] = value
    }
}

/**
 * 用户信息
 */
data class User(
    @SerializedName("Id") val id: String,
    @SerializedName("Account") val account: String,
    @SerializedName("SchoolLogo") val schoolLogo: String,
    @SerializedName("Token") val token: String,
    @SerializedName("Type") val type: Int,
    @SerializedName("Name") var name: String,
    var selectType: Int,
    @SerializedName("UUCAppToken") val uucAppToken: String,
    @SerializedName("UUCAppId") val uucAppId: String,
    @SerializedName("UUCLoginUrl") val uucLoginUrl: String,
    @SerializedName("AvatarUrl") var avatarUrl: String,
    @SerializedName("EmailLoginUrl") val emailLoginUrl: String,
    @SerializedName("UUCImFileUploadUrl") val uucImFileUploadUrl: String,
    @SerializedName("HasPwd") val hasPwd: Int,
    @SerializedName("SchoolOaUrl") val schoolOaUrl: String,
    @SerializedName("UUCApiUrl") val uucApiUrl: String,
    @SerializedName("Gender") val gender: String,
    @SerializedName("Remark") val remark: String,
    var schoolId: String
) : Serializable

/**
 * 首页顶部banner结果
 */
data class HomeBannerResult(
    override var itemType: Int,
    @SerializedName("FocusStayTime")
    val focusStayTime: Long,
    @SerializedName("AdList")
    val adList: MutableList<HomeBanner>
) : MultiItemEntity, Serializable

/**
 * 首页顶部banner
 */
data class HomeBanner(
    @SerializedName("Url")
    val url: String,
    @SerializedName("PlayTime")
    val playTime: Long,
    @SerializedName("Remark")
    val remark: String
) : Serializable

/**
 * 老师菜单结果
 */
data class TeacherMenuResult(
    @SerializedName("SsosToken")
    val ssosToken: String,
    @SerializedName("SsosUserId")
    val ssosUserId: String,
    @SerializedName("SsosFunList")
    val menuList: TeacherMenuList
) : Serializable

/**
 * 老师菜单列表
 */
data class TeacherMenuList(
    @SerializedName("CustomList")
    val customList: MutableList<TeacherMenu>,
    @SerializedName("UsuallyList")
    val usuallyList: MutableList<TeacherMenu>,
    @SerializedName("AllList")
    val allList: MutableList<TeacherMenu>,
    @SerializedName("CustomMax")
    val customMax: Int,
    @SerializedName("UsuallyMin")
    val usuallyMin: Int
) : Serializable {
    /**
     * 获取自定义列表
     *
     * @return
     */
    fun getCustomMenus(): MutableList<TeacherMenuSection> {
        val child = mutableListOf<TeacherMenuSection>()
        for (j in customList.indices) {
            if (!TextUtils.isEmpty(customList[j].parentId)) { //父级菜单id不为空时，表示此菜单项为子级菜单，将此菜单项加入一级菜单数据源中的分组下
                child.add(
                    TeacherMenuSection(
                        customList[j]
                    )
                )
            }
        }
        child.add(0, TeacherMenuSection("我的应用"))
        child.add(
            TeacherMenuSection(
                TeacherMenu("更多")
            )
        )
        return child
    }

    /**
     * 获取常用列表
     *
     * @return
     */
    fun getUsuallyMenus(): MutableList<TeacherMenuSection> {
        val child = mutableListOf<TeacherMenuSection>()
        for (j in usuallyList.indices) {
            if (!TextUtils.isEmpty(usuallyList[j].parentId)) { //父级菜单id不为空时，表示此菜单项为子级菜单，将此菜单项加入一级菜单数据源中的分组下
                child.add(
                    TeacherMenuSection(
                        usuallyList[j]
                    )
                )
            }
        }
        child.add(0, TeacherMenuSection("常用"))
        child.add(
            TeacherMenuSection(
                TeacherMenu("更多")
            )
        )
        return child
    }

    /**
     * 获取适配器二级菜单
     */
    fun getAllTwoMenu(): MutableList<TeacherMenuSection> {
        val secondMenus = mutableListOf<TeacherMenuSection>()
        for (i in allList.indices) {
            val header =
                TeacherMenuSection(allList[i].name)
            if (TextUtils.isEmpty(allList[i].parentId) && !secondMenus.contains(header)) { //当前为父级分组菜单且未添加进列表，则加入列表中保存
                secondMenus.add(header)
                //设置父级分组菜单对应的子级菜单项数据集
                for (j in allList.indices) {
                    if (!TextUtils.isEmpty(allList[j].parentId) &&
                        allList[j].parentId == allList[i].id
                    ) { //父级菜单id不为空时，表示此菜单项为子级菜单，将此菜单项加入对应的父级菜单项的分组下
                        allList[j].isAdd = customList.contains(allList[j])
                        secondMenus.add(
                            TeacherMenuSection(
                                allList[j]
                            )
                        )
                    }
                }
            }
        }
        return secondMenus
    }
}

/**
 * 老师功能菜单
 */
data class TeacherMenu(
    @SerializedName("ID")
    val id: String,
    @SerializedName("ParentID")
    val parentId: String,
    @SerializedName("SN")
    val sn: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("OrderNum")
    val orderNum: String,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Url")
    val url: String,
    @SerializedName("AclUrl")
    val aclUrl: String,
    var isAdd: Boolean = false
) : Serializable {
    constructor(name: String) : this("", "", "", name, "", "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeacherMenu

        if (id != other.id) return false
        if (sn != other.sn) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + sn.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }


}

/**
 * 老师功能菜单类型
 */
data class TeacherMenuMultiItem(
    override val itemType: Int,
    val menuSectionList: MutableList<TeacherMenuSection>
) : MultiItemEntity, Serializable

/**
 * 老师功能菜单分组
 */
data class TeacherMenuSection(
    override val isHeader: Boolean,
    val header: String,
    val menu: TeacherMenu
) : SectionEntity, Serializable {
    constructor(header: String) : this(
        true, header,
        TeacherMenu(header)
    )

    constructor(menu: TeacherMenu) : this(false, "", menu)
}

/**
 * 家长功能菜单结果
 */
data class ParentMenuResult(
    @SerializedName("Id")
    val id: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("OrderNum")
    val orderNum: String,
    @SerializedName("MenuList")
    val menuList: MutableList<ParentMenu>
) : Serializable

/**
 * 家长功能菜单
 */
data class ParentMenu(
    @SerializedName("Id")
    val id: String,
    @SerializedName("ParentId")
    val parentId: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("SystemId")
    val systemId: String,
    @SerializedName("Sn")
    val sn: String,
    @SerializedName("OrderNum")
    val orderNum: String,
    @SerializedName("Icon")
    val icon: String
) : Serializable {
    constructor(name: String) : this("", "", name, "", "", "", "")
}

/**
 * 家长能菜单类型
 */
data class ParentMenuMultiItem(
    override val itemType: Int,
    val menuSectionList: MutableList<ParentMenuSection>
) : MultiItemEntity, Serializable

/**
 * 家长功能菜单分组
 */
data class ParentMenuSection(
    override val isHeader: Boolean,
    val header: String,
    val menu: ParentMenu
) : SectionEntity, Serializable {
    constructor(header: String) : this(
        true, header,
        ParentMenu(header)
    )

    constructor(menu: ParentMenu) : this(false, "", menu)
}

/**
 * 事务提醒类型
 */
data class TransAlertMultiItem(override var itemType: Int, val transList: MutableList<TransAlert>) :
    MultiItemEntity, Serializable

/**
 * 事务提醒
 */
data class TransAlert(
    @SerializedName("Content")
    val content: String,
    @SerializedName("CreateTime")
    val createTime: String,
    @SerializedName("Id")
    val id: String,
    @SerializedName("IsRead")
    val isRead: Int,
    @SerializedName("PushMessageType")
    val pushMessageType: Int,
    @SerializedName("PushMessageTypeStr")
    val pushMessageTypeStr: String,
    @SerializedName("ThirdId")
    val thirdId: String,
    @SerializedName("ExParam")
    val exParam: String,
    @SerializedName("Title")
    val title: String
) : Serializable

/**
 * 今日行程类型
 */
data class ScheduleMultiItem(override var itemType: Int, val transList: MutableList<BusPlan>) :
    MultiItemEntity, Serializable

/**
 * 校车线路
 */
data class BusPlan(
    @SerializedName("PlanId")
    val planId: String,
    @SerializedName("LineId")
    val lineId: String,
    @SerializedName("LineName")
    val lineName: String,
    @SerializedName("StartPoint")
    val startPoint: String,
    @SerializedName("EndPoint")
    val endPoint: String,
    @SerializedName("StudentNum")
    val studentNum: Int,
    @SerializedName("StopNum")
    val stopNum: Int,
    @SerializedName("PlanType")
    val planType: Int,
    @SerializedName("DriverId")
    val driverId: String,
    @SerializedName("Driver")
    val driver: String,
    @SerializedName("DriverPhone")
    val driverPhone: String,
    @SerializedName("SchoolBusId")
    val schoolBusId: String,
    @SerializedName("VideoPhoneId")
    val videoPhoneId: String,
    @SerializedName("StartDate")
    val startDate: String,
    @SerializedName("EndDate")
    val endDate: String,
    @SerializedName("PlanStatus")
    val planStatus: Int,
    @SerializedName("PlateNo")
    val plateNo: String,
    @SerializedName("TeacherName")
    val teacherName: String,
    @SerializedName("Account")
    val account: String,
    @SerializedName("IsToday")
    val isToday: Int,
    @SerializedName("PlanTaskStatus")
    val planTaskStatus: Int,
    @SerializedName("TaskId")
    val taskId: String
) : Serializable

data class Avatar(@SerializedName("AvatarUrl") val avatarUrl: String) : Serializable