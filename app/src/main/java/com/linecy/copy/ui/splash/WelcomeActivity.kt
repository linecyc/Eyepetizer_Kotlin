package com.linecy.copy.ui.splash

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import android.view.WindowManager
import com.linecy.copy.R
import com.linecy.copy.databinding.ActivityWelcomeBinding
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.home.MainActivity
import com.linecy.copy.ui.splash.fragment.WelcomeFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_welcome.videoWelcome
import javax.inject.Inject

/**
 * @author by linecy
 */
class WelcomeActivity : BaseActivity<ActivityWelcomeBinding, ViewModel>(), HasSupportFragmentInjector {

  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return fragmentInjector
  }

  override fun layoutResId(): Int {
    return R.layout.activity_welcome
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()

    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN)
    hideToolBar()
    val list = ArrayList<Fragment>()
    list.add(WelcomeFragment.newInstance("第一页"))
    list.add(WelcomeFragment.newInstance("第二页"))
    list.add(WelcomeFragment.newInstance("第三页"))
    list.add(WelcomeFragment.newInstance("第四页"))


    mDataBinding.viewPager.offscreenPageLimit = 4
    mDataBinding.viewPager.adapter = FragmentAdapter(supportFragmentManager, list)
    //assets.locales.asSequence().map { Timber.i("********>>"+it) }
    //val uri = Uri.parse("file:///android_asset/video/landing.mp4")
    val uri = Uri.parse("android.resource://" + packageName + "/video/" + R.raw.landing)
    videoWelcome.setVideoURI(uri)
    videoWelcome.start()
    videoWelcome.setOnCompletionListener({
      if (!this.isFinishing && null != videoWelcome) {
        videoWelcome.start()
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
      }
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    if (videoWelcome != null) {
      //videoWelcome.top
    }
  }

  fun onSkip(v: View) {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
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