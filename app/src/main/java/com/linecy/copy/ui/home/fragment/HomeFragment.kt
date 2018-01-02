package com.linecy.copy.ui.home.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentHomeBinding
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.EmptyFragment
import com.linecy.copy.ui.home.adapter.TabAdapter
import com.linecy.copy.ui.home.listener.OnTabChangedListener
import kotlinx.android.synthetic.main.fragment_home.homeViewPager
import kotlinx.android.synthetic.main.fragment_home.titleRecyclerView

/**
 * @author by linecy
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(),
    OnTabChangedListener {


  override fun layoutResId(): Int {
    return R.layout.fragment_home
  }


  override fun onInitView(savedInstanceState: Bundle?) {


    val titleManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    titleRecyclerView.layoutManager = titleManager
    val titleAdapter = TabAdapter(context)
    titleAdapter.setOnTabChangedListener(this)
    titleRecyclerView.adapter = titleAdapter
    val listTitle = createTitle()
    titleAdapter.refreshData(listTitle)
    val homeAdapter = HomeFragmentAdapter(childFragmentManager)
    homeViewPager.adapter = homeAdapter
    val list = ArrayList<Fragment>()
    list.add(FindFragment())
    list.add(RecommendFragment())

    (2 until titleAdapter.itemCount).mapTo(list) { EmptyFragment.newInstance(listTitle[it]) }

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

  private fun createTitle(): List<String> {
    val titles = ArrayList<String>()
    titles.add("发现")
    titles.add("推荐")
    titles.add("日报")
    titles.add("创意")
    titles.add("音乐")
    titles.add("旅行")
    titles.add("科普")
    titles.add("搞笑")
    titles.add("时尚")
    titles.add("运动")
    titles.add("动画")
    titles.add("广告")
    titles.add("开胃")
    titles.add("生活")
    titles.add("剧情")
    titles.add("预告")
    titles.add("集锦")
    titles.add("记录")
    titles.add("游戏")
    titles.add("萌宠")
    titles.add("综艺")
    return titles
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