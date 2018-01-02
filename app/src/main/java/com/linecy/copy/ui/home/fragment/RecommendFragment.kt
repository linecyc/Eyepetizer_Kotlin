package com.linecy.copy.ui.home.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentRecommendBinding
import com.linecy.copy.mvvm.OnLoadingStateChangedListener
import com.linecy.copy.mvvm.viewmodel.RecommendViewModel
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.home.adapter.RecommendAdapter
import com.linecy.copy.ui.home.listener.OnBannerTitleClickListener
import com.linecy.copy.ui.home.listener.OnItemClickListener
import com.linecy.copy.ui.misc.ViewContainer
import com.linecy.core.data.model.HomeModel
import com.linecy.core.data.model.HomeModel.ItemList
import kotlinx.android.synthetic.main.fragment_recommend.recyclerView
import kotlinx.android.synthetic.main.fragment_recommend.swipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_recommend.viewContainer

/**
 * @author by linecy
 */
class RecommendFragment : BaseFragment<FragmentRecommendBinding>(),
    ViewContainer.OnReloadCallback, OnLoadingStateChangedListener, OnItemClickListener<HomeModel.ItemList>
    , OnBannerTitleClickListener {


  private lateinit var mAdapter: RecommendAdapter
  private lateinit var mRecommendViewModel: RecommendViewModel

  override fun layoutResId(): Int {
    return R.layout.fragment_recommend
  }


  override fun onInitView(savedInstanceState: Bundle?) {
    mRecommendViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        RecommendViewModel::class.java)
    mRecommendViewModel.addOnLoadingStateChangedListener(this)
    swipeRefreshLayout.setColorSchemeResources(R.color.primary_color_dark)
    swipeRefreshLayout.setOnRefreshListener {
      mRecommendViewModel.onRefresh()
    }
    viewContainer.setDisplayedChildId(R.id.content)
    viewContainer.setOnReloadCallback(this)
    fBinding.fHomeModel = mRecommendViewModel
    val manager = LinearLayoutManager(context)
    recyclerView.layoutManager = manager
    mAdapter = RecommendAdapter(context)
    mAdapter.setOnItemClickListener(this)
    mAdapter.setOnBannerTitleClickListener(this)
    recyclerView.adapter = mAdapter
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val isFirst = manager
            .findFirstCompletelyVisibleItemPosition() == 0
        swipeRefreshLayout.isEnabled = isFirst
      }
    })
    mRecommendViewModel.onStart()
  }


  override fun onShowLoading() {
    if (!swipeRefreshLayout.isRefreshing) {
      showLoadingDialog()
    }
  }

  override fun onHideLoading() {
    hideLoadingDialog()
  }

  override fun onReload() {
    mRecommendViewModel.onRefresh()
  }


  override fun onItemClick(position: Int, data: ItemList?) {
  }

  override fun onAuthorItemClick(position: Int, data: ItemList?) {
  }

  override fun onBannerTitleClick() {
    val parent = this.parentFragment
    if (parent is HomeFragment) {
      parent.setCurrentPage(2)
    }
  }
}