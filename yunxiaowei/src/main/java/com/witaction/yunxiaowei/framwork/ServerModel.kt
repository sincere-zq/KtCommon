package com.witaction.yunxiaowei

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BResp<T>(
    @SerializedName("IsSuccess")
    var isSuccess: Int,
    @SerializedName("Message")
    var msg: String,
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
    @SerializedName("Name") var name: String,
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
    @SerializedName("Name") val name: String,
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

data class HomeDataMenu(override val itemType: Int, val menuParentItem: FunctionMenu) :
    MultiItemEntity, Serializable

data class HomeDataBanner(override val itemType: Int, val bannerList: MutableList<HomeBanner>) :
    MultiItemEntity, Serializable

/**
 * 更新头像结果
 */
data class UpdateAvatarResult(@SerializedName("AvatarUrl") val avatarUrl: String) : Serializable

/**
 * 人员人脸
 */
data class PersonFace(
    @SerializedName("SortCode") val sortCode: String,
    @SerializedName("FaceId") val faceId: String,
    @SerializedName("ImageFilePath") val imageFilePath: String
) : Serializable

/**
 * 班级
 */
data class ClassBean(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Grade") val grade: String,
    @SerializedName("IsClassTeacher") val isClassTeacher: Boolean,
    @SerializedName("ClassTeacherName") val classTeacherName: String,
    @SerializedName("ClassTeacherAccount") val classTeacherAccount: String,
    @SerializedName("Scount") val scount: Int
) : Serializable

/**
 * 学生花名册
 */
data class StudentRoster(
    @SerializedName("Id") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("AvatarUrl") val avatarUrl: String,
    @SerializedName("IdentityNo") val identityNo: String,
    @SerializedName("Sex") val sex: String,
    @SerializedName("StudentNo") val studentNo: String,
    @SerializedName("IdentityNoValid") val identityNoValid: Int
) : Serializable

/**
 * 课表
 */
data class ClassTimetable(
    @SerializedName("ClassId") val classId: String,
    @SerializedName("ClassName") val className: String,
    @SerializedName("Semeter") val semeter: String,
    @SerializedName("DaySection") val daySection: Int,
    @SerializedName("CourseList") val courseList: MutableList<WeekCourse>
) : Serializable

/**
 * 周课程
 */
data class WeekCourse(
    @SerializedName("DayOfWeek") val dayOfWeek: Int,
    @SerializedName("List") val list: MutableList<CourseBean>
) : Serializable

/**
 * 课程
 */
data class CourseBean(
    @SerializedName("DayOfWeek") val dayOfWeek: Int,
    @SerializedName("DayOfWeekStr") val dayOfWeekStr: String,
    @SerializedName("TeacherName") val teacherName: String,
    @SerializedName("CourseName") val courseName: String,
    @SerializedName("ClassRoomName") val classRoomName: String,
    @SerializedName("ClassName") val className: String,
    @SerializedName("Section") val section: Int,
    @SerializedName("DaySection") val daySection: Int
) : Serializable

/**
 * 节次课程
 */
data class CourseSection(
    val section: Int,
    val courseList: MutableList<String>
) : Serializable
