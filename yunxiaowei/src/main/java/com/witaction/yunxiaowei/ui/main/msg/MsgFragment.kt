package com.witaction.yunxiaowei.ui.main.msg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.witaction.yunxiaowei.R

/**
 * A simple [Fragment] subclass.
 */
class MsgFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_msg, container, false)
    }

}
