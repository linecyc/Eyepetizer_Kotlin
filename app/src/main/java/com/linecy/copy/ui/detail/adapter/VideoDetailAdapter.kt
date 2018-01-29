package com.linecy.copy.ui.detail.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.databinding.HeaderVideoDetailBinding
import com.linecy.copy.ui.home.listener.OnItemClickListener
import com.linecy.eyepetizer.data.model.ItemList


class VideoDetailAdapter(
    context: Context,
    private val headerData: ItemList?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var list = ArrayList<ItemList>()

  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private var onItemClickListener: OnItemClickListener<ItemList>? = null
  private val typeHeader = 0
  private val typeList = 1

  override fun getItemCount(): Int {
    return if (list.isEmpty()) {
      0
    } else {
      list.size + 1
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0 -> typeHeader
      else -> typeList
    }
  }


  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    if (holder is ItemStyle2ViewHolder) {
      holder.bindData(position, list[position - 1])
    } else if (holder is VideoHeaderViewHolder) {
      holder.bindData()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      typeHeader -> VideoHeaderViewHolder(
          DataBindingUtil.inflate(inflater, R.layout.header_video_detail, parent, false))
      else -> ItemStyle2ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_style2, parent,
          false), onItemClickListener = onItemClickListener)
    }
  }


  inner class VideoHeaderViewHolder(private val dataBinding: HeaderVideoDetailBinding) : ViewHolder(
      dataBinding.root) {


    fun bindData() {
      dataBinding.setVariable(BR.headerVideo, headerData)
      //dataBinding.eventHandling
      dataBinding.executePendingBindings()
    }

  }

  /**
   *
   * 刷新数据
   *
   * 暂未找到分页加载的参数
   */
  fun refreshData(list: List<ItemList>?) {
    this.list.clear()

    if (list != null && list.isNotEmpty()) {

      for (i in 0 until list.size) {
        when (list[i].type) {
          "video" -> this.list.add(list[i])
        }
      }

    }
    notifyDataSetChanged()
  }
}
