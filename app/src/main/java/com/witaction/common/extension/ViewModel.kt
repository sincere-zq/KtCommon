package com.witaction.common.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * viewModelScope 协程
 */
fun ViewModel.launch(
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch {
    block.invoke(this)
}
