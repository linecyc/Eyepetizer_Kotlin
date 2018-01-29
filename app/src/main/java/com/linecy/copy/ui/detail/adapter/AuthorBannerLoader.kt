package com.linecy.copy.ui.detail.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.databinding.ItemBannerAuthorBinding
import com.linecy.copy.ui.banner.BannerCreator
import com.linecy.copy.ui.home.listener.EventHandling
import com.linecy.eyepetizer.data.model.ItemList

/**
 * @author by linecy.
 */
class AuthorBannerLoader : BannerCreator {
  private var viewDataBinding: ItemBannerAuthorBinding? = null

  override fun onCreateView(context: Context): View {

    // 返回页面布局文件
    viewDataBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.item_banner_author,
        null, false)
    viewDataBinding?.eventHandling = EventHandling()

    return viewDataBinding?.root!!
  }

  override fun onBindData(context: Context, position: Int, data: Any?) {
    if (data is ItemList) {
      viewDataBinding?.setVariable(BR.authorBannerItem, data)
      viewDataBinding?.executePendingBindings()
    }
  }

}