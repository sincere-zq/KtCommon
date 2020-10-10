package com.witaction.yunxiaowei.ui.main.home.classaffairs.studentleave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.witaction.yunxiaowei.R

/**
 * 待审核请假
 */
class WaitExamineLeaveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait_examine_leave, container, false)
    }

}
