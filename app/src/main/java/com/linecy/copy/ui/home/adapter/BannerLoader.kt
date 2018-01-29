package com.linecy.copy.ui.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.databinding.ItemBannerImageBinding
import com.linecy.copy.ui.banner.BannerCreator
import com.linecy.copy.ui.home.listener.EventHandling
import com.linecy.eyepetizer.data.model.ItemList

/**
 * @author by linecy
 */
class BannerLoader : BannerCreator {
  private var viewDataBinding: ItemBannerImageBinding? = null

  override fun onCreateView(context: Context): View {

    // 返回页面布局文件
    viewDataBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.item_banner_image,
        null, false)
    viewDataBinding?.eventHandling = EventHandling()

    return viewDataBinding?.root!!
  }

  override fun onBindData(context: Context, position: Int, data: Any?) {
    if (data is ItemList) {
      viewDataBinding?.setVariable(BR.bannerItem, data)
      viewDataBinding?.executePendingBindings()
    }
  }

}