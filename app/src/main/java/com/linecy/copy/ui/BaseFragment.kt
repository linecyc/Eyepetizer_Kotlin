package com.linecy.copy.ui

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linecy.copy.R
import com.linecy.copy.ui.widget.RollSquareView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * @author by linecy
 */
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

  @Inject
  protected lateinit var mViewModelFactory: ViewModelProvider.Factory
  protected var fBinding: VB? = null
  private var dialog: AlertDialog? = null
  private lateinit var squareView: RollSquareView

  override fun onAttach(context: Context?) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    if (layoutResId() != 0) {
      fBinding = DataBindingUtil.inflate(inflater, layoutResId(), container,
          false)
      return if (fBinding != null) {
        fBinding!!.root
      } else {
        inflater.inflate(layoutResId(), container, false)
      }
    }
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onInitView(savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    hideLoadingDialog()
  }

  protected abstract fun layoutResId(): Int

  protected abstract fun onInitView(savedInstanceState: Bundle?)


  protected fun showLoadingDialog() {
    loadingDialog()
  }

  protected fun hideLoadingDialog() {
    if (null != dialog && dialog?.isShowing!!) {
      dialog?.dismiss()
    }
  }

  private fun loadingDialog() {

    if (dialog == null) {
      val builder = AlertDialog.Builder(context, R.style.dialog)
      dialog = builder.create()
      val view = LayoutInflater.from(context).inflate(R.layout.layout_loading, null)
      squareView = view.findViewById(R.id.rollSquareView)
      squareView.visibility = View.VISIBLE
      dialog?.setView(view, 200, 200, 200, 200)
      dialog?.setCancelable(false)
      dialog?.show()
    } else {
      if (null != squareView && View.VISIBLE != squareView.visibility) {
        squareView.visibility = View.VISIBLE
      }
      dialog?.show()
    }
  }
}