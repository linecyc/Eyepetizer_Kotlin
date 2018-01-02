package com.linecy.copy.ui.home.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.databinding.ItemBannerImageBinding
import com.linecy.copy.ui.banner.BannerCreator
import com.linecy.core.data.model.HomeModel

/**
 * @author by linecy
 */
class BannerLoader : BannerCreator {
  private var viewDataBinding: ViewDataBinding? = null

  override fun onCreateView(context: Context): View {
    // 返回页面布局文件
    viewDataBinding = DataBindingUtil.inflate<ItemBannerImageBinding>(
        LayoutInflater.from(context),
        R.layout.item_banner_image,
        null, false)
    return viewDataBinding?.root!!
  }

  override fun onBindData(context: Context, position: Int, data: Any?) {
    if (data is HomeModel.ItemList) {
      viewDataBinding?.setVariable(BR.bannerItem, data)
      viewDataBinding?.executePendingBindings()
    }
  }

}