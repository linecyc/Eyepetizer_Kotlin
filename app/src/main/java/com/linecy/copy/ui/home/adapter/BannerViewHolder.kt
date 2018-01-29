package com.linecy.copy.ui.home.adapter

import android.databinding.ViewDataBinding
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView.ViewHolder
import android.widget.LinearLayout
import com.linecy.copy.BR
import com.linecy.copy.R
import com.linecy.copy.ui.banner.BannerAdapter
import com.linecy.copy.utils.DensityUtils
import com.linecy.eyepetizer.data.model.ItemList

/**
 * @author by linecy
 */
class BannerViewHolder(private val viewDataBinding: ViewDataBinding) : ViewHolder(
    viewDataBinding.root) {
  private val bannerView: ViewPager = viewDataBinding.root.findViewById(R.id.banner)

  init {
    bannerView.adapter = BannerAdapter(BannerLoader())
    val lp = bannerView.layoutParams
    if (lp is LinearLayout.LayoutParams) {
      val margin = DensityUtils.dp2px(bannerView.context, 20f)
      lp.setMargins(margin, 0, margin, 0)
      bannerView.layoutParams = lp
    }

  }

  fun bindData(list: List<ItemList>) {
    viewDataBinding.setVariable(BR.bannerList, list)
    viewDataBinding.executePendingBindings()
  }
}