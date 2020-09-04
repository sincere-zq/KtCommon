package com.witaction.yunxiaowei

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BResp<T>(
    @SerializedName("IsSuccess")
    var isSuccess: Int,
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

/**
 * 登录结果
 */
data class LoginResult(
    @SerializedName("AccessToken") val accessToken: String,
    @SerializedName("RefreshToken") val refreshToken: String
) : Serializable

/**
 * 用户信息
 */
data class User(
    @SerializedName("PersonId") val personId: String,
    @SerializedName("Name") val name: String,
    @SerializedName("AvatarUrl") var avatarUrl: String,
    @SerializedName("UserType") var userType: Int,
    @SerializedName("UserTypeText") val userTypeText: String,
    @SerializedName("ShowSwitchBtn") val showSwitchBtn: Int
) : Serializable

/**
 * 学校信息
 */
data class SchoolInfo(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("SLogo") val sLogo: String
) : Serializable

/**
 * 首页banner
 */
data class HomeBanner(
    @SerializedName("Id") val id: String,
    @SerializedName("Url") val url: String,
    @SerializedName("SLogo") val sLogo: String
) : Serializable

/**
 * 功能菜单
 */
data class FunctionMenu(
    @SerializedName("Id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("Sn") val sn: String,
    @SerializedName("Url") val url: String,
    @SerializedName("OrderNum") val orderNum: Int,
    @SerializedName("ParentId") val parentId: String,
    @SerializedName("Icon") val icon: String,
    @SerializedName("SubMenuList") val subMenuList: MutableList<FunctionMenu>
) : Serializable

/**
 * 首页数据返回结果
 */
data class HomeDataResult(
    @SerializedName("SchoolInfo") val schoolInfo: SchoolInfo,
    @SerializedName("AdList") val adList: MutableList<HomeBanner>,
    @SerializedName("MenuList") val menuList: MutableList<FunctionMenu>
) : Serializable

data class HomeDataMenu(override val itemType: Int, val menuList: MutableList<FunctionMenu>) :
    MultiItemEntity, Serializable

data class HomeDataBanner(override val itemType: Int, val bannerList: MutableList<HomeBanner>) :
    MultiItemEntity, Serializable

