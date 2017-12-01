package com.linecy.copy.ui.home

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.linecy.copy.R
import com.linecy.copy.databinding.ActivityMainBinding
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.home.fragment.FindFragment
import com.linecy.copy.ui.home.fragment.HomeFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


/**
 * @author by linecy
 */
class MainActivity : BaseActivity<ActivityMainBinding, ViewModel>(), HasSupportFragmentInjector {


  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return fragmentInjector
  }

  override fun layoutResId(): Int {
    return R.layout.activity_main
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    //setToolbarTitle("首页")
    hideToolBar()
    val list = ArrayList<Fragment>()
    list.add(HomeFragment())
    list.add(FindFragment())
    list.add(FindFragment())
    list.add(FindFragment())
    mDataBinding.viewPager.adapter = FragmentAdapter(supportFragmentManager, list)
    mDataBinding.viewPager.offscreenPageLimit = 3
//    mDataBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//      override fun onPageScrolled(position: Int, positionOffset: Float,
//          positionOffsetPixels: Int) {
//      }
//
//      override fun onPageSelected(position: Int) {
//        setToolbarTitle(mDataBinding.viewPager.adapter.getPageTitle(position))
//      }
//
//      override fun onPageScrollStateChanged(state: Int) {}
//    })
    mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager)
    mDataBinding.tabLayout.getTabAt(0)?.setIcon(R.drawable.selector_home_button)
    mDataBinding.tabLayout.getTabAt(1)?.setIcon(R.drawable.selector_live_button)
    mDataBinding.tabLayout.getTabAt(2)?.setIcon(R.drawable.selector_match_button)
    mDataBinding.tabLayout.getTabAt(3)?.setIcon(R.drawable.selector_relativity_button)
  }


  inner class FragmentAdapter constructor(manager: FragmentManager,
      private val list: ArrayList<Fragment>) : FragmentPagerAdapter(
      manager) {

    private val titleList = arrayOf("首页", "发现", "热门", "我的")


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