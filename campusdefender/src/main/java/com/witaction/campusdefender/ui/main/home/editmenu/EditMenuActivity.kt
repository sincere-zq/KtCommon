package com.witaction.campusdefender.ui.main.home.editmenu

import android.view.View
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.eyepetizer.android.extension.gone
import com.witaction.campusdefender.AppConfig
import com.witaction.campusdefender.R
import com.witaction.campusdefender.databinding.ActivityEditMenuBinding
import com.witaction.campusdefender.ui.main.MainActivity
import com.witaction.campusdefender.ui.main.home.TeacherMenuAdapter
import com.witaction.common.base.AppManager
import com.witaction.common.base.BVMActivity
import com.witaction.common.extension.open
import com.witaction.common.utils.GlobalUtil
import com.witaction.common.utils.toast
import com.witaction.common.widget.ConfirmDialog
import com.witaction.common.widget.HeaderView

/**
 * 老师菜单编辑
 */
class EditMenuActivity : BVMActivity<ActivityEditMenuBinding, EditMenuViewModel>(),
    HeaderView.HeaderListener, OnItemDragListener {

    private val adapter by lazy { TeacherMenuAdapter(mutableListOf()) }

    private val customAdapter by lazy { CustomMenuAdapter() }

    override fun vmbinding(): Class<EditMenuViewModel> = EditMenuViewModel::class.java

    override fun viewbinding(): ActivityEditMenuBinding =
        ActivityEditMenuBinding.inflate(layoutInflater)

    override fun initView() {
        vb.headerView.setHeaderListener(this)
        vb.recyclerview.layoutManager = GridLayoutManager(this, AppConfig.HOME_MENU_NUM)
        vb.recyclerview.adapter = adapter
        adapter.addChildClickViewIds(R.id.img_edit)
        adapter.setOnItemLongClickListener { adapter, view, position ->
            vm.isEdit.value?.let {
                if (!it) {
                    vm.isEdit.value = true
                }
            }
            true
        }
        adapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.img_edit) {
                vm.customList.value?.let {
                    vm.menuInfo?.let { it1 ->
                        if (it.size >= it1.menuList.customMax) {
                            toast(
                                "${GlobalUtil.getString(R.string.customize_up_to)}${it1.menuList.customMax}${GlobalUtil.getString(
                                    R.string.num_menu
                                )}"
                            )
                            return@setOnItemChildClickListener
                        }
                    }

                    val edit = adapter.data[position].menu.isAdd
                    if (edit) {
                        it.remove(adapter.data[position].menu)
                    } else {
                        it.add(adapter.data[position].menu)
                    }
                    vm.customList.value = it
                }

                vm.allMenuList.value?.let {
                    it[position].menu.isAdd = !it[position].menu.isAdd
                    vm.allMenuList.value = it
                }
            }
        }

        vb.recyclerviewCustom.adapter = customAdapter
        customAdapter.addChildClickViewIds(R.id.img_edit)
        vb.recyclerviewCustom.layoutManager = GridLayoutManager(this, AppConfig.HOME_MENU_NUM)
        vb.recyclerviewCustom.adapter = customAdapter
        customAdapter.setOnItemLongClickListener { _, _, _ ->
            vm.isEdit.value?.let {
                if (!it) {
                    vm.isEdit.value = true
                }
            }
            true
        }
        customAdapter.draggableModule.isDragEnabled = true
        customAdapter.draggableModule.setOnItemDragListener(this)
        customAdapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.img_edit) {
                val item = customAdapter.data[position]
                vm.customList.value?.let {
                    it.remove(customAdapter.data[position])
                    vm.customList.value = it
                }
                vm.allMenuList.value?.let {
                    for (menu in it) {
                        if (!menu.isHeader && menu.menu.id == item.id) {
                            menu.menu.isAdd = !menu.menu.isAdd
                            break
                        }
                    }
                    vm.allMenuList.value = it
                }
            }
        }
    }

    override fun initData() {
        vm.allMenuList.observe(this) {
            adapter.setList(it)
        }

        vm.customList.observe(this) {
            vb.tvTitle.gone(it.size > 0)
            customAdapter.setList(it)
        }

        vm.isEdit.observe(this) {
            vb.headerView.setRightTextVisible(it)
            adapter.isEdit = it
            adapter.notifyDataSetChanged()
            customAdapter.isEdit = it
            customAdapter.notifyDataSetChanged()
        }
        vm.saveResult.observe(this) {
            hideLoading()
            if (it.isSuccess()) {
                vm.isEdit.value = false
                toast(R.string.save_success)
                AppManager.getInstance().killOthersActivity(this)
                open<MainActivity>()
                finish()
            } else {
                toast(it.msg)
            }
        }
    }

    /**
     * 保存自定义菜单
     */
    private fun saveCustomMenu() {
        vm.customList.value?.let {
            vm.menuInfo?.let { it1 ->
                if (it == it1.menuList.customList) {
                    vm.isEdit.value = false
                    onBackPressed()
                } else {
                    val builder = StringBuilder()
                    for (index in it.indices) {
                        builder.append(it[index].id).append("_").append(index).append(",")
                    }
                    if (builder.isNotEmpty()) {
                        builder.delete(builder.length - 1, builder.length)
                    }
                    showLoading()
                    vm.saveCustomMenus(builder.toString(), it1.ssosToken, it1.ssosUserId)
                }
            }
        }
    }

    override fun onRightSecondClick(view: View?) {
    }

    override fun onSubTitleClick(view: View?) {
    }

    override fun onLeftClick(view: View?) {
        onBackPressed()
    }

    override fun onBackPressed() {
        if (vm.isEdit.value != null && vm.isEdit.value == true) {
            ConfirmDialog.build(this) {
                message { GlobalUtil.getString(R.string.confirm_to_quit_edit) }
                cancelListener {
                    {
                        it.dismiss()
                    }
                }
                confirmListener {
                    {
                        it.dismiss()
                        vm.isEdit.value = false
                    }
                }
            }.show()
            return
        }
        super.onBackPressed()
    }

    override fun onRightClick(view: View?) {
    }

    override fun onRightTextClick(view: View?) {
        saveCustomMenu()
    }

    override fun onItemDragMoving(
        source: RecyclerView.ViewHolder?,
        from: Int,
        target: RecyclerView.ViewHolder?,
        to: Int
    ) {
    }

    override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
    }

    override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
    }
}
