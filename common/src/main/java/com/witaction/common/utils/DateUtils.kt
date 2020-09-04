package com.witaction.common.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**
     * 年月日时分秒
     */
    const val yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
    const val YEAR_MONTH_DAY_HOUR_MIN_SEC_ = "yyyy/MM/dd HH:mm:ss"
    const val MONTH_DAY_HOUR_MIN = "MM-dd HH:mm"
    /**
     * 时分秒
     */
    const val HH_mm_ss = "HH:mm:ss"
    /**
     * 时分
     */
    const val HH_mm = "HH:mm"
    /**
     * 年月日时分
     */
    const val yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm"
    const val YEAR_MONTH_DAY_HOUR_MIN_POINT = "yyyy.MM.dd  HH:mm"
    /**
     * 年月日
     */
    const val yyyy_MM_dd = "yyyy-MM-dd"
    /**
     * 年月日 点
     */
    const val YEAR_MONTH_DAY_POINT = "yyyy.MM.dd"
    /**
     * 年月日 星期
     */
    const val yyyy_MM_dd_EEEE = "yyyy-MM-dd  EEEE"

    /**
     * 时分
     */
    const val H_mm = "H:mm"

    /**
     * 月日
     */
    const val MONTH_DAY = "M.dd"

    /**
     * 获取年月时分
     *
     * @param date
     * @return
     */
    fun getTime(date: Date?): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat(yyyy_MM_dd_HH_mm_ss)
        return format.format(date)
    }

    /**
     * 获取年月日
     *
     * @param date
     * @return
     */
    fun getDate(date: Date?): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat(yyyy_MM_dd)
        return format.format(date)
    }

    /**
     * 获取传入日期的前一天的日期
     *
     * @param date
     * @return
     */
    fun getBeforeDay(date: Date?): Date? {
        var date = date
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        date = calendar.time
        return date
    }

    /**
     * 获取传入日期的后一天数据
     *
     * @param date
     * @return
     */
    fun getAfterDay(date: Date?): Date? {
        var date = date
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        date = calendar.time
        return date
    }

    /**
     * 获取前一天的日期
     *
     * @return
     */
    fun getYesterdayDate(): String? {
        val format = SimpleDateFormat(yyyy_MM_dd)
        val calendar = Calendar.getInstance() //此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, -1) //得到前一天
        return format.format(calendar.time)
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    fun getCurSystemDate(): String {
        val format = SimpleDateFormat(yyyy_MM_dd)
        val d1 = Date(System.currentTimeMillis())
        return format.format(d1)
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    fun getSystemTime(): String? {
        return getSystemTime(yyyy_MM_dd_HH_mm)
    }

    /**
     * 获取系统当前时间
     *
     * @return
     */
    fun getSystemTime(formatTime: String?): String? {
        val format = SimpleDateFormat(formatTime)
        val d1 = Date(System.currentTimeMillis())
        return format.format(d1)
    }

    /**
     * 获取当前系统日期及星期
     *
     * @return
     */
    fun getCurSystemDateWeek(): String? {
        val format = SimpleDateFormat(yyyy_MM_dd_EEEE)
        val d1 = Date(System.currentTimeMillis())
        return format.format(d1)
    }

    /**
     * 获取年月日星期
     *
     * @param date
     * @return
     */
    fun getDateWeek(date: Date?): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat(yyyy_MM_dd_EEEE)
        return format.format(date)
    }


    /**
     * 根据时间获取星期
     *
     * @param pTime
     * @return
     */
    fun getWeek(pTime: String?): String? {
        var Week = ""
        val format = SimpleDateFormat(yyyy_MM_dd)
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(pTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (c[Calendar.DAY_OF_WEEK] == 1) {
            Week += "星期日"
        }
        if (c[Calendar.DAY_OF_WEEK] == 2) {
            Week += "星期一"
        }
        if (c[Calendar.DAY_OF_WEEK] == 3) {
            Week += "星期二"
        }
        if (c[Calendar.DAY_OF_WEEK] == 4) {
            Week += "星期三"
        }
        if (c[Calendar.DAY_OF_WEEK] == 5) {
            Week += "星期四"
        }
        if (c[Calendar.DAY_OF_WEEK] == 6) {
            Week += "星期五"
        }
        if (c[Calendar.DAY_OF_WEEK] == 7) {
            Week += "星期六"
        }
        return Week
    }

    /**
     * 从年月日时分秒类型的时间中，获取时分
     *
     * @param date
     * @return
     */
    fun getDateTime(date: String?): String? {
        val _oldformat =
            SimpleDateFormat(yyyy_MM_dd_HH_mm_ss)
        var _date: Date? = null
        _date = try {
            _oldformat.parse(date)
        } catch (e: ParseException) {
            return ""
        }
        val _newformat = SimpleDateFormat(H_mm)
        return _newformat.format(_date)
    }

    /**
     * 从年月日时分秒类型的时间中，获取时分
     *
     * @param date
     * @return
     */
    fun getDateTime(date: Date?): String? {
        val format = SimpleDateFormat(HH_mm)
        return format.format(date)
    }

    /**
     * 获取新的时间格式
     *
     * @param date
     * @return
     */
    fun getNewformatByOldformat(
        date: String?,
        oldformat: String?,
        newformat: String?
    ): String? {
        val _oldformat = SimpleDateFormat(oldformat)
        var _date: Date? = null
        _date = try {
            _oldformat.parse(date)
        } catch (e: ParseException) {
            return ""
        }
        val _newformat = SimpleDateFormat(newformat)
        return _newformat.format(_date)
    }

    /**
     * 从年月日类型的时间中，月日
     *
     * @param date
     * @return
     */
    fun getMonthDay(date: String?): String? {
        val _oldformat = SimpleDateFormat(yyyy_MM_dd)
        var _date: Date? = null
        _date = try {
            _oldformat.parse(date)
        } catch (e: ParseException) {
            return ""
        }
        val _newformat = SimpleDateFormat("M.dd")
        return _newformat.format(_date)
    }

    /**
     * 通过时分秒获取时分
     *
     * @param date
     * @return
     */
    fun getDateTimeByTime(date: String?): String? {
        val _oldformat = SimpleDateFormat(HH_mm_ss)
        var _date: Date? = null
        _date = try {
            _oldformat.parse(date)
        } catch (e: ParseException) {
            return ""
        }
        val _newformat =
            SimpleDateFormat("H:mm") //一个H  那么09:00  会返回9:00
        return _newformat.format(_date)
    }

    /**
     * 通过日期星期得到日期
     *
     * @param date
     * @return
     */
    fun getDateByEEEE(date: String?): String? {
        val _oldformat = SimpleDateFormat(yyyy_MM_dd_EEEE)
        var _date: Date? = null
        _date = try {
            _oldformat.parse(date)
        } catch (e: ParseException) {
            return ""
        }
        val _newformat = SimpleDateFormat(yyyy_MM_dd)
        return _newformat.format(_date)
    }

    /**
     * 比较两个时间，结束时间大于开始时间为TRUE
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun timeCompare(
        startTime: String?,
        endTime: String?
    ): Boolean { //格式化时间
        return timeCompare(yyyy_MM_dd_HH_mm, startTime, endTime)
    }

    /**
     * 比较两个时间，结束时间大于开始时间为TRUE
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun timeCompare(
        pattern: String?,
        startTime: String?,
        endTime: String?
    ): Boolean { //格式化时间
        val CurrentTime = SimpleDateFormat(pattern)
        try {
            val startDate = CurrentTime.parse(startTime)
            val endDate = CurrentTime.parse(endTime)
            return if (endDate.time - startDate.time > 0) {
                true
            } else {
                false
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 比较两个日期，结束日期大于等于开始日期为TRUE   30天内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun dateCompare(
        startTime: String?,
        endTime: String?
    ): Boolean { //格式化时间
        try {
            val currentTime = SimpleDateFormat(yyyy_MM_dd)
            val startDate = currentTime.parse(startTime)
            val endDate = currentTime.parse(endTime)
            val dateDistance = endDate.time - startDate.time
            val dayDistance = dateDistance / (1000 * 60 * 60 * 24)
            return if (dayDistance >= 0 && dayDistance <= 30) {
                true
            } else {
                false
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 比较两个日期，结束日期大于等于开始日期为TRUE
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun dateCompareYMD(
        startTime: String?,
        endTime: String?
    ): Boolean { //格式化时间
        val currentTime = SimpleDateFormat(yyyy_MM_dd)
        try {
            val startDate = currentTime.parse(startTime)
            val endDate = currentTime.parse(endTime)
            val dateDistance = endDate.time - startDate.time
            val dayDistance = dateDistance / (1000 * 60 * 60 * 24)
            return if (dayDistance >= 0) {
                true
            } else {
                false
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 比较两个日期带上下午，结束日期大于等于开始日期为TRUE
     * 同一天 如果开始是上午，则结束可以上午 下午
     * 同一天 如果开始是下午，结束是下午
     */
    fun DateCompareYMDAmPm(
        startYMD: String?,
        startIsAm: Boolean,
        endYMD: String?,
        endIsAm: Boolean
    ): Boolean { //格式化时间
        val CurrentTime = SimpleDateFormat(yyyy_MM_dd)
        try {
            val startDate = CurrentTime.parse(startYMD)
            val endDate = CurrentTime.parse(endYMD)
            val dateDistance = endDate.time - startDate.time
            val dayDistance = dateDistance / (1000 * 60 * 60 * 24)
            return if (dayDistance > 0) { //开始时间比结束时间大一天以上
                true
            } else if (dayDistance == 0L) { //开始时间和结束时间同一天
                if (startIsAm) {
                    true
                } else {
                    !endIsAm
                }
            } else {
                false
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 获取年月时分
     *
     * @param date
     * @return
     */
    fun getTime(
        date: Date?,
        pattern: String?
    ): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    fun getDateByFormat(date: Long, format: String?): String? {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date)
    }

    /**
     * 根据格式字符串时间获取Calender
     *
     * @param dateString
     * @param pattern
     * @return
     */
    fun getCalender(dateString: String?, pattern: String?): Calendar? {
        val calendar = Calendar.getInstance()
        if (!TextUtils.isEmpty(dateString)) {
            val dateFormat = SimpleDateFormat(pattern)
            try {
                val parse = dateFormat.parse(dateString)
                calendar.time = parse
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
        return calendar
    }

    /**
     * 根据Calendar获取年月日
     *
     * @param calendar
     * @return
     */
    fun getYearMonthDayByCalender(calendar: Calendar): String? {
        val month = calendar[Calendar.MONTH] + 1
        return calendar[Calendar.YEAR].toString() + "-" + month + "-" + calendar[Calendar.DAY_OF_MONTH]
    }

    /**
     * 获取时间间隔几天  2018-6-14  2018-6-15 返回2
     *
     * @param startTime
     * @param endTime
     * @return
     */
    fun dateDiff(
        startTime: String?,
        endTime: String?,
        format: String?
    ): Long {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return 0
        }
        // 按照传入的格式生成一个simpledateformate对象
        val sd = SimpleDateFormat(format)
        val nd = 1000 * 24 * 60 * 60.toLong() // 一天的毫秒数
        val nh = 1000 * 60 * 60.toLong() // 一小时的毫秒数
        val nm = 1000 * 60.toLong() // 一分钟的毫秒数
        val ns: Long = 1000 // 一秒钟的毫秒数
        val diff: Long
        var day: Long = 0
        try { // 获得两个时间的毫秒时间差异
            diff = (sd.parse(endTime).time
                    - sd.parse(startTime).time)
            day = diff / nd // 计算差多少天
            val hour = diff % nd / nh // 计算差多少小时
            val min = diff % nd % nh / nm // 计算差多少分钟
            val sec = diff % nd % nh % nm / ns // 计算差多少秒
            return if (day >= 1) {
                day + 1
            } else {
                if (day == 0L) {
                    1
                } else {
                    0
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

    fun getMonthFormat(month: Int): String? {
        return if (month + 1 < 10) {
            "0" + (month + 1)
        } else "" + (month + 1)
    }
}