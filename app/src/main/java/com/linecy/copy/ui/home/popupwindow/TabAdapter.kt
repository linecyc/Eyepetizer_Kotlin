package com.linecy.copy.ui.home.popupwindow

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.home.popupwindow.TabAdapter.ViewHolder
import com.linecy.core.data.model.TabModel

/**
 * @author by linecy
 */
class TabAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {

  private val lists = ArrayList<TabModel>()
  private val inflater: LayoutInflater = LayoutInflater.from(context)


  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.bindData(lists[position], position)
  }

  override fun getItemCount(): Int {
    return lists.size
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    val viewBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.item_pop_tab,
        parent, false)

    return ViewHolder(viewBinding)
  }


  inner class ViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
      viewDataBinding.root) {
    private var itemPosition = 1

    fun bindData(tab: TabModel, position: Int) {
      itemPosition = position
      viewDataBinding.setVariable(BR.itemTab, tab)
      viewDataBinding.executePendingBindings()
    }
  }


  fun refreshData(list: List<TabModel>?) {
    this.lists.clear()
    if (list != null && list.isNotEmpty()) {
      this.lists.addAll(list)
    }
    notifyDataSetChanged()
  }
}