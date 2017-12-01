package com.linecy.copy.ui.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.home.adapter.HomeAdapter.ViewHolder
import com.linecy.core.data.model.HomeModel


class HomeAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {
  private var list = ArrayList<HomeModel.ItemList>()
  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private var isRefresh: Boolean = true

  override fun getItemCount(): Int {
    return list.size
  }

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    holder?.bindData(list[position])
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    val view: ViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.item_home, parent,
        false)
    return ViewHolder(view)
  }


  inner class ViewHolder(private val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(
      dataBinding.root) {
    init {
      dataBinding.root.setOnClickListener {

      }
    }

    fun bindData(itemListModel: HomeModel.ItemList) {
      dataBinding.setVariable(BR.homeAdapterItem, itemListModel)
      dataBinding.executePendingBindings()
    }
  }


  fun refreshData(homeModel: HomeModel?) {
    if (isRefresh) {
      this.list.clear()
      this.isRefresh = false
    }

    if (homeModel?.itemList != null) {
      homeModel.itemList!!
          .filter { it.type.equals("video") }
          .forEach { this.list.add(it) }
    }
    notifyDataSetChanged()
  }

  fun setRefreshFlag() {
    isRefresh = true
  }
}
