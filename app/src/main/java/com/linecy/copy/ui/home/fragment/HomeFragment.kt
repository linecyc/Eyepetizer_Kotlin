package com.linecy.copy.ui.home.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentHomeBinding
import com.linecy.copy.mvvm.OnLoadingStateChangedListener
import com.linecy.copy.mvvm.viewmodel.HomeViewModel
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.home.adapter.HomeAdapter
import com.linecy.copy.ui.misc.ViewContainer

/**
 * @author by linecy
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(), ViewContainer.OnReloadCallback, OnLoadingStateChangedListener {


  private lateinit var mAdapter: HomeAdapter
  private lateinit var mHomeViewModel: HomeViewModel

  override fun layoutResId(): Int {
    return R.layout.fragment_home
  }


  override fun onInitView(savedInstanceState: Bundle?) {
    mAdapter = HomeAdapter(context)
    mHomeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        HomeViewModel::class.java)
    mHomeViewModel.addOnLoadingStateChangedListener(this)
    fBinding.swipeRefreshLayout.setColorSchemeResources(R.color.primary_color_dark)
    fBinding.swipeRefreshLayout.setOnRefreshListener {
      mAdapter.setRefreshFlag()
      mHomeViewModel.onRefresh()
    }
    fBinding.viewContainer.setDisplayedChildId(R.id.content)
    fBinding.viewContainer.setOnReloadCallback(this)
    fBinding.fHomeModel = mHomeViewModel
    val manager = LinearLayoutManager(context)
    fBinding.recyclerView.layoutManager = manager
    fBinding.recyclerView.adapter = mAdapter
    fBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        fBinding.swipeRefreshLayout.isEnabled = manager
            .findFirstCompletelyVisibleItemPosition() == 0
        val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
        if (lastVisibleItemPosition + 1 == fBinding.recyclerView.adapter.itemCount) {

          mHomeViewModel.onLoadMore()
        }
      }
    })
    mHomeViewModel.onStart()


  }


  override fun onShowLoading() {
    if (!fBinding.swipeRefreshLayout.isRefreshing) {
      showLoadingDialog()
    }
  }

  override fun onHideLoading() {
    hideLoadingDialog()
  }

  override fun onReload() {
    mHomeViewModel.onRefresh()
  }
}