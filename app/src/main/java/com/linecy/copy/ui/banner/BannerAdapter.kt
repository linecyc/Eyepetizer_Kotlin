package com.linecy.copy.ui.banner

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * @author by linecy.
 */
class BannerAdapter(private val bannerCreator: BannerCreator?) : PagerAdapter() {


  private val list = ArrayList<Any>()


  override fun getCount(): Int {
    return list.size
  }


  override fun isViewFromObject(view: View, `object`: Any): Boolean {
    return view == `object`
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any {
    val view = createView(container, position)
    container.addView(view)
    return view
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View)
  }

  private fun createView(viewGroup: ViewGroup, position: Int): View {

    if (bannerCreator == null) {
      throw RuntimeException("banner creator is null")
    }

    val view = bannerCreator.onCreateView(viewGroup.context)
    val count = count
    if (count > 0) {
      val data = list[position]
      if (list.size > 0) {
        bannerCreator.onBindData(viewGroup.context, position, data)
      }
    }

    return view
  }


  fun refreshData(list: List<Any>?) {
    this.list.clear()
    if (null != list && list.isNotEmpty()) {
      this.list.addAll(list)
    }
    notifyDataSetChanged()
  }

}