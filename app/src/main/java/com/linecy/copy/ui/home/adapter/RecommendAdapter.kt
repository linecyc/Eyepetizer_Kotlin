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
import com.linecy.copy.ui.home.listener.OnBannerTitleClickListener
import com.linecy.copy.ui.home.listener.OnItemClickListener
import com.linecy.core.data.model.HomeModel


class RecommendAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var list = ArrayList<HomeModel.ItemList>()
  private var banner = ArrayList<HomeModel.ItemList>()
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private val typeBanner = 0
  private val typeList = 1
  private var onItemClickListener: OnItemClickListener<HomeModel.ItemList>? = null
  private var onBannerTitleClickListener: OnBannerTitleClickListener? = null
  override fun getItemCount(): Int {
    return if (list.size > 0) {
      list.size + 1
    } else {
      0
    }
  }


  override fun getItemViewType(position: Int): Int {
    return if (position == 0) {
      typeBanner
    } else {
      typeList
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    when (getItemViewType(position)) {
      typeBanner -> if (holder is BannerViewHolder) {
        holder.bindData(banner)
      }
      else -> if (holder is ViewHolder) {
        holder.bindData(position - 1, list[position - 1])
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      typeBanner ->
        BannerViewHolder(
            DataBindingUtil.inflate(inflater, R.layout.item_banner, parent, false),
            onBannerTitleClickListener)
      else -> ViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_home, parent,
          false))
    }
  }


  inner class ViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
      dataBinding.root) {
    private var p = 0
    private var data: HomeModel.ItemList? = null

    init {
      dataBinding.root.findViewById<ImageView>(R.id.ivCover).setOnClickListener(
          { onItemClickListener?.onItemClick(p, data) })
      dataBinding.root.findViewById<LinearLayout>(R.id.layoutAuthor).setOnClickListener(
          { onItemClickListener?.onAuthorItemClick(p, data) })
    }

    fun bindData(position: Int, itemListModel: HomeModel.ItemList) {
      this.p = position
      this.data = itemListModel
      dataBinding.setVariable(BR.homeAdapterItem, itemListModel)
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
      if (homeModel.count > 5) {
        (homeModel.itemList!!.subList(0, 5))
            .filter { it.type.equals("video") }
            .forEach { this.banner.add(it) }
      }
      homeModel.itemList!!.subList(5, homeModel.count)
          .filter { it.type.equals("video") }
          .forEach { this.list.add(it) }
    }
    notifyDataSetChanged()
  }


  fun setOnItemClickListener(l: OnItemClickListener<HomeModel.ItemList>) {
    this.onItemClickListener = l
  }

  fun setOnBannerTitleClickListener(l: OnBannerTitleClickListener) {
    this.onBannerTitleClickListener = l
  }
}
