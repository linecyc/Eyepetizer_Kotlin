package com.linecy.copy.ui.home.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentHomeBinding
import com.linecy.copy.mvvm.viewmodel.HomeViewModel
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.home.adapter.HomeAdapter
import com.linecy.core.data.model.HomeModel

/**
 * @author by linecy
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {


  private lateinit var mAdapter: HomeAdapter
  private lateinit var mHomeViewModel: HomeViewModel

  override fun layoutResId(): Int {
    return R.layout.fragment_home
  }


  override fun onInitView(savedInstanceState: Bundle?) {
    mAdapter = HomeAdapter(context)
    mHomeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(
        HomeViewModel::class.java)
    fBinding.swipeRefreshLayout.setColorSchemeResources(R.color.primary_color_dark)
    fBinding.swipeRefreshLayout.setOnRefreshListener { mHomeViewModel.onRefresh() }

    fBinding.fHomeModel = mHomeViewModel
    val manager = LinearLayoutManager(context)
    fBinding.recyclerView.layoutManager = manager
    fBinding.recyclerView.adapter = mAdapter
    fBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val lastVisibleItemPosition = manager.findLastVisibleItemPosition()
        if (lastVisibleItemPosition + 1 == fBinding.recyclerView.adapter.itemCount) {

          mHomeViewModel.onLoadMore()
        }
      }
    })

    mHomeViewModel.mObservableHomeModel.observe(this,
        Observer<HomeModel> {
          mHomeViewModel.mHomeModel.set(it)

        })

    mHomeViewModel.viewStyle.isLoading.addOnPropertyChangedCallback(object :
        Observable.OnPropertyChangedCallback() {
      override fun onPropertyChanged(p0: Observable?, p1: Int) {
        if (mHomeViewModel.viewStyle.isLoading.get()) {
          showLoadingDialog()
        } else {
          hideLoadingDialog()
        }
      }
    })
    mHomeViewModel.onStart()


  }


}