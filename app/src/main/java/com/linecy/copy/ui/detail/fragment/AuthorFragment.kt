package com.linecy.copy.ui.detail.fragment

import android.os.Bundle
import android.support.annotation.IntDef
import android.support.v7.widget.LinearLayoutManager
import com.linecy.copy.R
import com.linecy.copy.databinding.FragmentAuthorBinding
import com.linecy.copy.mvvm.viewmodel.AuthorDetailViewModel
import com.linecy.copy.ui.BaseFragment
import com.linecy.copy.ui.detail.adapter.AuthorHomeAdapter
import com.linecy.core.data.model.ItemList
import kotlinx.android.synthetic.main.fragment_author.recyclerView

/**
 * 未找到作者详情的接口，直接复制多个itemList代替。
 *
 * @author by linecy.
 */
class AuthorFragment : BaseFragment<FragmentAuthorBinding>() {


  companion object {
    private const val EXTRA_TYPE = "extra_type"
    private const val EXTRA_DATA = "extra_data"

    const val TYPE_HOME: Long = 1
    const val TYPE_VIDEO: Long = 2
    const val TYPE_DYNAMIC: Long = 3

    fun newInstance(@PageType type: Long, list: ArrayList<ItemList>?): AuthorFragment {
      val fragment = AuthorFragment()
      val bundle = Bundle()
      bundle.putLong(EXTRA_TYPE, type)
      bundle.putParcelableArrayList(EXTRA_DATA, list)
      fragment.arguments = bundle
      return fragment
    }
  }

  @IntDef(TYPE_HOME, TYPE_VIDEO, TYPE_DYNAMIC)
  @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
  annotation class PageType


  override fun layoutResId(): Int {
    return R.layout.fragment_author
  }

  override fun onInitView(savedInstanceState: Bundle?) {

    val authorDetail = mViewModelFactory.create(AuthorDetailViewModel::class.java)
    fBinding.authorDetail = authorDetail

    val list: ArrayList<ItemList>? = arguments?.getParcelableArrayList(EXTRA_DATA)

    val type = arguments?.getLong(EXTRA_TYPE)
    recyclerView.layoutManager = LinearLayoutManager(context)

    if (null != type) {
      recyclerView.adapter = AuthorHomeAdapter(context, type)
    }

    if (null != list) {
      authorDetail.loadData(list)
    }
  }

}