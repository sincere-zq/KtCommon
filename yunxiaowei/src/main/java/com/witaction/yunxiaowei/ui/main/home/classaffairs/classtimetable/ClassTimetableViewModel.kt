package com.witaction.yunxiaowei.ui.main.home.classaffairs.classtimetable

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.witaction.common.extension.launch
import com.witaction.yunxiaowei.BResp
import com.witaction.yunxiaowei.ClassTimetable
import com.witaction.yunxiaowei.CourseSection
import com.witaction.yunxiaowei.framwork.ServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ClassTimetableViewModel : ViewModel() {
    var fromType = ClassTimetableActivity.TYPE_CLASS

    val id by lazy { MutableLiveData<String>() }

    val timetableResult by lazy { MutableLiveData<BResp<ClassTimetable>>() }

    val timetableDatas by lazy { MutableLiveData<MutableList<CourseSection>>() }

    fun getClassTimetable() {
        launch {
            id.value?.let {
                when (fromType) {
                    ClassTimetableActivity.TYPE_CLASS -> {
                        timetableResult.value = ServerRepository.getClassTimetable(mutableMapOf("ClassId" to it))
                    }
                    ClassTimetableActivity.TYPE_CHILD -> {
                        timetableResult.value = ServerRepository.getClassTimetable(mutableMapOf("StudentId" to it))
                    }
                }
            }
        }
    }

    fun generateDatas(data: ClassTimetable) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = mutableListOf<CourseSection>()
            for (section in 0 until data.daySection) {
                result.add(CourseSection(section, mutableListOf("", "", "", "", "", "", "")))
            }
            for (weekCourse in data.courseList) {
                for (course in weekCourse.list) {
                    result[course.section - 1].courseList[course.dayOfWeek - 1] = course.courseName
                }
            }
            withContext(Dispatchers.Main) {
                timetableDatas.value = result
            }
        }
    }
}