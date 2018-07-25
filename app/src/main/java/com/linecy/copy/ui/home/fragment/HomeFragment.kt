package com.linecy.copy.ui.home.fragment

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import com.linecy.copy.R
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.EmptyFragment
import com.linecy.copy.ui.home.adapter.TabAdapter
import com.linecy.copy.ui.home.listener.OnTabChangedListener
import com.linecy.copy.ui.home.popupwindow.TabPopWindow
import com.linecy.eyepetizer.data.model.TabModel
import kotlinx.android.synthetic.main.fragment_home.homeViewPager
import kotlinx.android.synthetic.main.fragment_home.ivSearchController
import kotlinx.android.synthetic.main.fragment_home.ivTabController
import kotlinx.android.synthetic.main.fragment_home.titleRecyclerView

/**
 * @author by linecy
 */
class HomeFragment : BaseFragment<ViewDataBinding>(),
    OnTabChangedListener, OnClickListener {
  private val mTabPopWindow by lazy { TabPopWindow(context) }

  override fun layoutResId(): Int {
    return R.layout.fragment_home
  }


  override fun onInitView(savedInstanceState: Bundle?) {

    ivTabController.setOnClickListener(this)
    ivSearchController.setOnClickListener(this)
    val titleManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    titleRecyclerView.layoutManager = titleManager
    val titleAdapter = TabAdapter(context)
    titleAdapter.setOnTabChangedListener(this)
    titleRecyclerView.adapter = titleAdapter
    val listTitle = createTabList()
    mTabPopWindow.refreshData(listTitle)
    val listTab = ArrayList<TabModel>()
    listTab.add(TabModel(0, "发现", ""))
    listTab.add(TabModel(0, "推荐", ""))
    listTab.add(TabModel(0, "日报", ""))
    listTab.addAll(listTitle)
    titleAdapter.refreshData(listTab)
    val homeAdapter = HomeFragmentAdapter(childFragmentManager)
    homeViewPager.adapter = homeAdapter
    val list = ArrayList<Fragment>()
    list.add(FindFragment())
    list.add(RecommendFragment())

    (2 until titleAdapter.itemCount).mapTo(list) { EmptyFragment.newInstance(listTab[it]) }

    homeAdapter.refreshData(list)
    homeViewPager.offscreenPageLimit = list.size
    homeViewPager.currentItem = 1

    homeViewPager.addOnPageChangeListener(object : OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {
      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      }

      override fun onPageSelected(position: Int) {
        titleAdapter.setSelectPosition(position)
        scroll(titleManager, titleRecyclerView, position)
      }

    })
  }

  override fun onDestroyView() {
    if (mTabPopWindow.isShowing) {
      mTabPopWindow.dismiss()
    }
    super.onDestroyView()
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.ivTabController -> {
        mTabPopWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0)
      }

//        R.id.ivSearchController
//      ->

    }
  }

  fun scroll(manager: LinearLayoutManager, recyclerView: RecyclerView, position: Int) {
    val first = manager.findFirstVisibleItemPosition()
    val last = manager.findLastVisibleItemPosition()
    val center = (last + first) / 2
    val interval = (last - first) / 2
    if (position < center) {
      val p = position - interval
      recyclerView.scrollToPosition(if (p >= 0) p else p + 1)
    } else {
      val p = position + interval
      recyclerView.scrollToPosition(if (p <= manager.itemCount) p else p - 1)
    }
  }

  override fun onTabChanged(position: Int) {
    homeViewPager.currentItem = position
  }

  private fun createTabList(): ArrayList<TabModel> {
    val list = ArrayList<TabModel>()
    list.add(TabModel(1, "创意", "技术与审美结合，探索视觉的无限可能"))
    list.add(TabModel(2, "音乐", "全球最酷、最炫、最有态度的音乐集合"))
    list.add(TabModel(3, "旅行", "发现世界的奇妙和辽阔"))
    list.add(TabModel(4, "科普", "每天获得新知识"))
    list.add(TabModel(5, "搞笑", "哈哈哈哈哈哈哈哈"))
    list.add(TabModel(6, "时尚", "优雅地行走在潮流尖端"))
    list.add(TabModel(7, "运动", "冲浪、滑板、跑酷、骑行、生命停不下来"))
    list.add(TabModel(8, "动画", "有趣的人永远不缺童心"))
    list.add(TabModel(9, "广告", "为广告人的精彩创意人点赞"))
    list.add(TabModel(10, "开胃", "眼球和味蕾，一个都不放过"))
    list.add(TabModel(11, "生活", "匠心、健康、生活感悟"))
    list.add(TabModel(12, "剧情", "用一个好故事、描述生活的不可思议"))
    list.add(TabModel(13, "预告", "电影、剧集、戏剧抢先看"))
    list.add(TabModel(14, "集锦", "最好的部分 + 有化学反应的混剪"))
    list.add(TabModel(15, "记录", "告诉他们为什么与众不同"))
    list.add(TabModel(16, "游戏", "欢迎来到惊险刺激的新世界"))
    list.add(TabModel(17, "萌宠", "来自汪星、喵星、蠢萌的你"))
    list.add(TabModel(18, "综艺", "全球网红在表演什么"))

    return list
  }


  inner class HomeFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val list = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment {
      return list[position]
    }

    override fun getCount(): Int {
      return list.size
    }

    fun refreshData(list: List<Fragment>) {
      this.list.clear()
      this.list.addAll(list)
      notifyDataSetChanged()
    }

  }


  fun setCurrentPage(position: Int) {
    homeViewPager.currentItem = position
  }
}