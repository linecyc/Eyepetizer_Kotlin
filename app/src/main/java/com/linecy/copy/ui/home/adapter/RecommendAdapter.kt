package com.linecy.copy.ui.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.home.listener.OnHeaderClickListener
import com.linecy.copy.ui.home.listener.OnItemClickListener
import com.linecy.eyepetizer.data.model.HomeModel
import com.linecy.eyepetizer.data.model.ItemList


class RecommendAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var list = ArrayList<ItemList>()
  private var banner = ArrayList<ItemList>()
  private var headerData: ItemList? = null
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private val typeHeader = 0
  private val typeBanner = 1
  private val typeList = 2
  private var onItemClickListener: OnItemClickListener<ItemList>? = null
  private var onHeaderClickListener: OnHeaderClickListener? = null
  override fun getItemCount(): Int {
    return if (list.size > 0) {
      list.size + 2
    } else {
      0
    }
  }


  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0 -> typeHeader
      1 -> typeBanner
      else -> typeList
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    when (getItemViewType(position)) {
      typeHeader -> if (holder is HeaderViewHolder) {
        holder.bindData(headerData)
      }
      typeBanner -> if (holder is BannerViewHolder) {
        holder.bindData(banner)
      }
      else -> if (holder is ViewHolder) {
        holder.bindData(position - 2, list[position - 2])
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      typeHeader ->
        HeaderViewHolder(
            DataBindingUtil.inflate(inflater, R.layout.item_recomend_header, parent, false),
            onHeaderClickListener)

      typeBanner ->
        BannerViewHolder(
            DataBindingUtil.inflate(inflater, R.layout.item_banner, parent, false))

      else -> ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_style1, parent,
          false))
    }
  }


  inner class ViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
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

  /**
   *
   * 刷新数据
   *
   * 暂未找到分页加载的参数
   */
  fun refreshData(homeModel: HomeModel?) {
    this.list.clear()
    this.banner.clear()

    if (homeModel?.itemList != null) {
      val temp = ArrayList<ItemList>()

      if (homeModel.count > 5 && null != homeModel.itemList) {
        for (i in 0 until homeModel.count) {
          when (homeModel.itemList!![i].type) {

            "video" -> temp.add(homeModel.itemList!![i])

            "textHeader" -> headerData = homeModel.itemList!![i]
          }
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


  fun setOnItemClickListener(l: OnItemClickListener<ItemList>) {
    this.onItemClickListener = l
  }

  fun setOnHeaderClickListener(l: OnHeaderClickListener) {
    this.onHeaderClickListener = l
  }
}
