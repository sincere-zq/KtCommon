package com.witaction.common.utils

import com.scwang.smart.refresh.layout.SmartRefreshLayout


fun finishLoad(refrehLyaout: SmartRefreshLayout) {
    refrehLyaout.finishRefresh()
    refrehLyaout.finishLoadMore()
}