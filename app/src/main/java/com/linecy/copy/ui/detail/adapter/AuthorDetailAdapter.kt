package com.linecy.copy.ui.detail.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linecy.copy.R
import com.linecy.copy.R.layout
import com.linecy.copy.ui.detail.fragment.AuthorFragment.Companion.TYPE_DYNAMIC
import com.linecy.copy.ui.detail.fragment.AuthorFragment.Companion.TYPE_HOME
import com.linecy.copy.ui.detail.fragment.AuthorFragment.PageType
import com.linecy.copy.ui.home.listener.OnItemClickListener
import com.linecy.eyepetizer.data.model.ItemList


class AuthorDetailAdapter(
    context: Context, @PageType private val type: Long) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var list = ArrayList<ItemList>()
  private var banner = ArrayList<ItemList>()
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private val typeHeader = 0
  private val typeBanner = 1
  private val typeListHeader = 2
  private val typeList = 3
  private var onItemClickListener: OnItemClickListener<ItemList>? = null


  override fun getItemCount(): Int {
    return when (type) {
      TYPE_HOME -> {
        if (list.size > 0) {
          list.size + 3
        } else {
          0
        }
      }

      else -> list.size
    }

  }


  override fun getItemViewType(position: Int): Int {
    return when (type) {
      TYPE_HOME -> {
        when (position) {
          0 -> typeHeader
          1 -> typeBanner
          2 -> typeListHeader
          else -> typeList
        }
      }

      else -> typeList
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    when (getItemViewType(position)) {
      typeBanner -> if (holder is AuthorBannerViewHolder) {
        holder.bindData(banner)
      }
      typeList -> if (holder is ItemStyle2ViewHolder) {
        if (type == TYPE_HOME) {
          holder.bindData(position - 3, list[position - 3])
        } else {
          holder.bindData(position, list[position])
        }
      } else if (holder is ItemStyle3ViewHolder) {
        holder.bindData(position, list[position])
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      typeHeader ->
        HeaderViewHolder(
            DataBindingUtil.inflate(inflater, layout.item_author_home_header, parent, false))

      typeBanner ->
        AuthorBannerViewHolder(
            DataBindingUtil.inflate(inflater, layout.item_banner, parent, false))

      typeListHeader -> ListHeaderViewHolder(
          DataBindingUtil.inflate(inflater, layout.item_author_home_list_header, parent, false))

      else -> if (type == TYPE_DYNAMIC) {
        ItemStyle3ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_style3, parent,
            false), onItemClickListener = onItemClickListener)
      } else {
        ItemStyle2ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_style2, parent,
            false), onItemClickListener = onItemClickListener)
      }
    }
  }


  /**
   * 列表的头
   */
  inner class ListHeaderViewHolder(viewDataBinding: ViewDataBinding) : ViewHolder(
      viewDataBinding.root)


  /**
   *
   * 刷新数据
   *
   * 暂未找到分页加载的参数
   */
  fun refreshData(list: List<ItemList>?) {
    this.list.clear()
    this.banner.clear()

    if (list != null && list.size > 5) {
      val temp = ArrayList<ItemList>()

      for (i in 0 until list.size) {
        when (list[i].type) {
          "video" -> temp.add(list[i])
        }
      }

      if (temp.size > 5) {
        this.banner.addAll(temp.subList(0, 5))
        this.list.addAll(temp.subList(5, temp.size))
      } else {
        this.list.addAll(temp)
      }
    }
    notifyDataSetChanged()
  }
}
