package com.linecy.copy.ui.home

import android.arch.lifecycle.ViewModel
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.linecy.copy.R
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.home.fragment.FindFragment
import com.linecy.copy.ui.home.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.viewPager


/**
 * @author by linecy
 */
class MainActivity : BaseActivity<ViewDataBinding, ViewModel>() {

  override fun layoutResId(): Int {
    return R.layout.activity_main
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    hideToolBar()
    val list = ArrayList<Fragment>()
    list.add(HomeFragment())
    list.add(FindFragment())
    list.add(FindFragment())
    list.add(FindFragment())
    viewPager.adapter = FragmentAdapter(supportFragmentManager, list)
    viewPager.offscreenPageLimit = 3

    tabLayout.setupWithViewPager(viewPager)
    tabLayout.getTabAt(0)?.setIcon(R.drawable.selector_home_button)
    tabLayout.getTabAt(1)?.setIcon(R.drawable.selector_live_button)
    tabLayout.getTabAt(2)?.setIcon(R.drawable.selector_match_button)
    tabLayout.getTabAt(3)?.setIcon(R.drawable.selector_relativity_button)
  }


  inner class FragmentAdapter constructor(manager: FragmentManager,
      private val list: ArrayList<Fragment>) : FragmentPagerAdapter(
      manager) {

    private val titleList = arrayOf("首页", "关注", "通知", "我的")


    override fun getItem(position: Int): Fragment {
      return list[position]
    }

    override fun getCount(): Int {
      return list.size
    }

    override fun getPageTitle(position: Int): CharSequence {
      return titleList[position]
    }


  }
}