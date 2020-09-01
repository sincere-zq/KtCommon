package com.witaction.common.widget

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import com.witaction.common.R
import kotlinx.android.synthetic.main.dialog_confirm.*

/**
 * 确认弹窗
 */

class ConfirmDialog private constructor(context: Context, private val builder: Builder) :
    Dialog(context, R.style.confirm_dialog_style) {

    companion object {
        fun build(context: Context, init: Builder.() -> Unit) = Builder(context, init).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_confirm)
        tv_content.text = builder.message
        builder.cancelText?.let { tv_left.setText(it) }
        builder.confirmText?.let { tv_right.setText(it) }
        builder.cancelListener?.let { tv_left.setOnClickListener { it(this) } }
        builder.confirmListener?.let { tv_right.setOnClickListener { it(this) } }
    }

    class Builder private constructor() {

        constructor(context: Context, init: Builder.() -> Unit) : this() {
            this.context = context
            init()
        }

        lateinit var context: Context
        lateinit var message: String
        var cancelText: Int? = null
        var confirmText: Int? = null
        var cancelListener: ConfirmListener? = null
        var confirmListener: ConfirmListener? = null

        fun message(init: Builder.() -> String) = apply { message = init() }
        fun cancel(init: Builder.() -> Int) = apply { cancelText = init() }
        fun confirmText(init: Builder.() -> Int) = apply { confirmText = init() }
        fun cancelListener(init: Builder.() -> ConfirmListener) =
            apply { cancelListener = init() }

        fun confirmListener(init: Builder.() -> ConfirmListener) =
            apply { confirmListener = init() }

        fun build() = ConfirmDialog(context, this)
    }
}
private typealias ConfirmListener = (dialog: DialogInterface) -> Unit