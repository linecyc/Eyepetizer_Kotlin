package com.linecy.copy.ui.home.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView.ViewHolder
import android.widget.TextView
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.home.listener.OnHeaderClickListener
import com.linecy.eyepetizer.data.model.ItemList

/**
 * 推荐页头部时间.
 * @author by linecy.
 */
class HeaderViewHolder(private val viewDataBinding: ViewDataBinding,
    onHeaderClickListener: OnHeaderClickListener?) : ViewHolder(
    viewDataBinding.root) {

  init {
    viewDataBinding.root.findViewById<TextView>(R.id.bannerTitle).setOnClickListener(
        { onHeaderClickListener?.onBannerHeaderClick() })
  }

  fun bindData(list: ItemList?) {
    viewDataBinding.setVariable(BR.headerData, list)
    viewDataBinding.executePendingBindings()
  }
}