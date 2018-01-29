package com.linecy.copy.ui.detail.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView.ViewHolder
import com.linecy.copy.BR
import com.linecy.eyepetizer.data.model.ItemList

/**
 * 作者页首页头部.
 * @author by linecy.
 */
class HeaderViewHolder(private val viewDataBinding: ViewDataBinding) : ViewHolder(
    viewDataBinding.root) {
  fun bindData(list: ItemList?) {
    viewDataBinding.setVariable(BR.headerData, list)
    viewDataBinding.executePendingBindings()
  }
}