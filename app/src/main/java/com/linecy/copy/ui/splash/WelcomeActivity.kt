package com.linecy.copy.ui.splash

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.databinding.ViewDataBinding
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.WindowManager
import com.linecy.copy.R
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.home.MainActivity
import com.linecy.copy.ui.splash.fragment.WelcomeFragment
import kotlinx.android.synthetic.main.activity_welcome.videoWelcome
import kotlinx.android.synthetic.main.activity_welcome.viewPager

/**
 * @author by linecy
 */
class WelcomeActivity : BaseActivity<ViewDataBinding, ViewModel>() {

  override fun layoutResId(): Int {
    return R.layout.activity_welcome
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    val preferences = getSharedPreferences("my_copy", Context.MODE_PRIVATE)
    if (preferences.getBoolean("lunch", false)) {
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    } else {
      window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          WindowManager.LayoutParams.FLAG_FULLSCREEN)
      hideToolBar()
      val list = ArrayList<Fragment>()
      list.add(WelcomeFragment.newInstance("每日编辑精选，一如既往"))
      list.add(WelcomeFragment.newInstance("关注越多，发现越多"))
      list.add(WelcomeFragment.newInstance("离线自动缓存，精彩永不下线"))
      list.add(WelcomeFragment.newInstance("登录即可订阅、评论和同步已收藏视频"))


      viewPager.offscreenPageLimit = 4
      viewPager.adapter = FragmentAdapter(supportFragmentManager, list)
      val uri = Uri.parse("android.resource://" + packageName + "/video/" + R.raw.landing)
      videoWelcome.setVideoURI(uri)
      videoWelcome.setOnCompletionListener {
        if (!this.isFinishing && null != videoWelcome) {
          videoWelcome.start()
        }
      }
    }

  }

  override fun onResume() {
    super.onResume()
    if (null != videoWelcome) {
      videoWelcome.start()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    if (videoWelcome != null) {
      videoWelcome.stopPlayback()
    }
  }


  inner class FragmentAdapter constructor(manager: FragmentManager,
      private val list: ArrayList<Fragment>) : FragmentPagerAdapter(
      manager) {

    private val titleList = arrayOf("第一页", "第二页", "第三页", "第四页")


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