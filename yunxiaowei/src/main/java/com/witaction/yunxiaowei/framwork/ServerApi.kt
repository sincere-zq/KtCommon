package com.witaction.yunxiaowei.framwork

import com.witaction.yunxiaowei.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ServerApi {
    companion object {
        //账号密码登录
        const val URL_LOGIN_BY_ACCOUNT = "Login/LoginByPwd"
        //获取登录验证码
        const val URL_GET_LOGIN_CODE = "Login/GetVerCode_Login"
        //验证码登录
        const val URL_LOGIN_BY_CODE = "Login/LoginByVerCode"
        //刷新token
        const val URL_REFRESH_TOKEN = "Login/UpdateAccessToken"
        //初始密码
        const val URL_INIT_PWD = "Login/SaveInitPassword"
        //获取用户信息
        const val URL_GET_USER_INFO = "MyCenter/GetUserInfo"
        //获取首页数据
        const val URL_GET_HOME_DATAS = "HomePage/GetHomePageInfo"
        //切换身份
        const val URL_SWITCH_IDENTITY = "MyCenter/SwitchUserType"
        //更新头像
        const val URL_UPDATE_AVATAR = "MyCenter/UploadAvatar"
        //保存用户真实姓名
        const val URL_SAVE_REAL_NAME = "MyCenter/SaveBasicInfo"
        //初始化密码
        const val URL_RESET_PWD = "MyCenter/SaveUpdatePassword"
        //获取初始化密码验证码
        const val URL_GET_RESET_PWD_CODE = "MyCenter/GetVerCode_ChangePwd"
        //重置手机号
        const val URL_RESET_PHONE = "MyCenter/SaveUpdatePhone"
        //获取重置手机号验证码
        const val URL_GET_RESET_PHONE_CODE = "MyCenter/GetVerCode_UpdatePhone"
        //获取人员人脸照片列表
        const val URL_GET_PERSON_FACE_LIST = "MyCenter/GetFacePhotoList"
        //删除人员人脸
        const val URL_DELETE_FACE = "MyCenter/DeleteFacePhoto"
        //上传人员人脸
        const val URL_UPLOAD_FACE = "MyCenter/UploadFacePhoto"
        //获取班级列表
        const val URL_GET_CLASS_LIST = "ClassAffairs/GetClassList"
        //获取学生花名册
        const val URL_GET_STU_ROSTER = "ClassAffairs/GetStudentRosterListData"
        //更新学籍号
        const val URL_UPDATE_STU_STATUS = "ClassAffairs/UpdateIdentityNo"
        //获取班级课表
        const val URL_GET_CLASS_TIMETABLE = "CourseTable/GetClassCourseTable"
        //获取学生课表
        const val URL_GET_STU_TIMETABLE = "CourseTable/GetStudentCourseTable"

    }

    @POST(URL_LOGIN_BY_ACCOUNT)
    fun loginByAccount(@Body params: MutableMap<String, Any>): Call<BResp<LoginResult>>

    @POST(URL_GET_LOGIN_CODE)
    fun getLoginCode(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_LOGIN_BY_CODE)
    fun loginByCode(@Body params: MutableMap<String, Any>): Call<BResp<LoginResult>>

    @POST(URL_REFRESH_TOKEN)
    fun refreshToken(@Body params: MutableMap<String, Any>): Call<BResp<LoginResult>>

    @POST(URL_GET_USER_INFO)
    fun getUserInfo(): Call<BResp<User>>

    @POST(URL_GET_HOME_DATAS)
    fun getHomeData(): Call<BResp<HomeDataResult>>

    @POST(URL_SWITCH_IDENTITY)
    fun switchIdentity(): Call<BResp<Any>>

    @POST(URL_UPDATE_AVATAR)
    fun updateAvatar(@Body params: MutableMap<String, Any>): Call<BResp<UpdateAvatarResult>>

    @POST(URL_SAVE_REAL_NAME)
    fun saveRealName(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_RESET_PWD)
    fun resetPwd(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_GET_RESET_PWD_CODE)
    fun getResetPwdCode(): Call<BResp<Any>>

    @POST(URL_RESET_PHONE)
    fun resetPhone(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_GET_RESET_PHONE_CODE)
    fun getResetPhoneCode(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_GET_PERSON_FACE_LIST)
    fun getPersonFaceList(@Body params: MutableMap<String, Any>): Call<BResp<PersonFace>>

    @POST(URL_DELETE_FACE)
    fun deleteFace(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_UPLOAD_FACE)
    fun uploadFace(@Body params: MutableMap<String, Any>): Call<BResp<PersonFace>>

    @POST(URL_GET_CLASS_LIST)
    fun getClassList(@Body params: MutableMap<String, Any>): Call<BResp<ClassBean>>

    @POST(URL_GET_STU_ROSTER)
    fun getStudentRoster(@Body params: MutableMap<String, Any>): Call<BResp<StudentRoster>>

    @POST(URL_UPDATE_STU_STATUS)
    fun updateStuStatus(@Body params: MutableMap<String, Any>): Call<BResp<Any>>

    @POST(URL_GET_CLASS_TIMETABLE)
    fun getClassTimetable(@Body params: MutableMap<String, Any>): Call<BResp<ClassTimetable>>

    @POST(URL_GET_STU_TIMETABLE)
    fun getStuTimetable(@Body params: MutableMap<String, Any>): Call<BResp<ClassTimetable>>
}