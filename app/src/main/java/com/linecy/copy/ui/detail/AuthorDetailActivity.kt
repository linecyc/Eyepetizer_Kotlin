package com.linecy.copy.ui.detail

import android.arch.lifecycle.ViewModel
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import com.linecy.copy.R
import com.linecy.copy.databinding.ActivityAuthorDetailBinding
import com.linecy.copy.ui.BaseActivity
import com.linecy.copy.ui.detail.fragment.AuthorFragment
import com.linecy.core.data.model.ItemList
import kotlinx.android.synthetic.main.activity_author_detail.appBar
import kotlinx.android.synthetic.main.activity_author_detail.tabLayout
import kotlinx.android.synthetic.main.activity_author_detail.toolbar
import kotlinx.android.synthetic.main.activity_author_detail.viewPager
import kotlinx.android.synthetic.main.layout_bar.ibBack
import kotlinx.android.synthetic.main.layout_bar.ibShared

/**
 * @author by linecy.
 */
class AuthorDetailActivity : BaseActivity<ActivityAuthorDetailBinding, ViewModel>(),
    AppBarLayout.OnOffsetChangedListener, OnClickListener {

  private lateinit var tvTitle: TextView
  private lateinit var ivAuthorBg: ImageView
  private var title: String? = null
  private var height: Int = 0

  companion object {
    const val EXTRA_DATA = "extra_data"
  }

  override fun layoutResId(): Int {
    return R.layout.activity_author_detail
  }

  override fun onInitView(savedInstanceState: Bundle?) {
    hideToolBar()
    val itemList = intent.extras.getParcelable<ItemList>(EXTRA_DATA)
    mDataBinding.itemList = itemList
    appBar.addOnOffsetChangedListener(this)
    title = itemList?.data?.author?.name
    val data = ArrayList<ItemList>()

    for(i in 0 until 10){
      data.add(itemList)
    }
    val list = ArrayList<Fragment>()
    list.add(AuthorFragment.newInstance(AuthorFragment.TYPE_HOME,data))
    list.add(AuthorFragment.newInstance(AuthorFragment.TYPE_VIDEO,data))
    list.add(AuthorFragment.newInstance(AuthorFragment.TYPE_DYNAMIC,data))
    viewPager.adapter = AuthorFragmentAdapter(supportFragmentManager, list)
    viewPager.offscreenPageLimit = list.size
    tabLayout.setupWithViewPager(viewPager)
    tvTitle = findViewById(R.id.title)
    ivAuthorBg = findViewById(R.id.ivAuthorBg)
    ibBack.setOnClickListener(this)
    ibShared.setOnClickListener(this)
  }

  override fun onDestroy() {
    appBar.removeOnOffsetChangedListener(this)

    super.onDestroy()
  }

  override fun onClick(p0: View?) {
    when (p0?.id) {
      R.id.ibBack -> finish()

    //R.id.ibShared->
    }
  }

  /**
   * 当背景被折叠时展示title.
   */
  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

    if (height == 0) {
      height = ivAuthorBg.height - toolbar.height
    }
    if (Math.abs(verticalOffset) >= height) {
      toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.primary_color))
      tvTitle.text = title
    } else {
      toolbar.setBackgroundColor(Color.TRANSPARENT)
      tvTitle.text = null
    }
  }

  inner class AuthorFragmentAdapter(
      fm: FragmentManager, val list: List<Fragment>) : FragmentPagerAdapter(fm) {
    private val title = arrayOf(getString(R.string.author_home), getString(R.string.author_video),
        getString(
            R.string.author_dynamic))

    init {

    }

    override fun getItem(position: Int): Fragment {
      return list[position]
    }

    override fun getCount(): Int {
      return list.size
    }

    override fun getPageTitle(position: Int): CharSequence {
      return title[position]
    }
  }

}