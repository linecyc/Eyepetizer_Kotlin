package com.linecy.copy.ui.home.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.home.listener.OnItemClickListener
import com.linecy.eyepetizer.data.model.ItemList

/**
 * @author by linecy.
 */
class ItemStyle1ViewHolder(private val dataBinding: ViewDataBinding,
    onItemClickListener: OnItemClickListener<ItemList>? = null) : RecyclerView.ViewHolder(
    dataBinding.root) {
  private var p = 0
  private var data: ItemList? = null

  init {
    dataBinding.root.findViewById<ImageView>(R.id.ivCover).setOnClickListener(
        { onItemClickListener?.onItemClick(p, data) })
    dataBinding.root.findViewById<LinearLayout>(R.id.layoutAuthor).setOnClickListener(
        { onItemClickListener?.onAuthorItemClick(p, data) })
  }

  fun bindData(position: Int, itemListModel: ItemList) {
    this.p = position
    this.data = itemListModel
    dataBinding.setVariable(BR.itemStyle1, itemListModel)
    dataBinding.executePendingBindings()
  }
}