package com.linecy.copy.ui

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.linecy.copy.R
import com.linecy.copy.databinding.LayoutBaseBinding
import com.linecy.copy.ui.widget.RollSquareView
import com.linecy.copy.utils.AppContainer
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<VB : ViewDataBinding, VM : ViewModel?> : AppCompatActivity(), OnClickListener {

  @Inject
  lateinit var mViewModelFactory: ViewModelProvider.Factory

  protected lateinit var mDataBinding: VB
  private var mBaseBinding: LayoutBaseBinding? = null
  protected var mViewModel: VM? = null
  private var mToolbar: RelativeLayout? = null
  private var mTvTitle: TextView? = null
  private var mIbHomeAsUp: ImageButton? = null

  private var dialog: AlertDialog? = null
  private lateinit var squareView: RollSquareView


  protected abstract fun layoutResId(): Int

  protected abstract fun onInitView(savedInstanceState: Bundle?)


  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    init()
    onInitView(savedInstanceState)
  }

  private fun init() {
    setContentView(layoutResId())

    if (mViewModel != null) {
      lifecycle.addObserver(mViewModel as LifecycleObserver)

    }
  }

  override fun setContentView(layoutResID: Int) {
    val appContainer = AppContainer.DEFAULT
    val rootView = appContainer.bind(this)
    mBaseBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_base, rootView, true)
    mDataBinding = DataBindingUtil.inflate(layoutInflater, layoutResID, null, false)

    // content
    val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT)
    mDataBinding.root.layoutParams = params
    mBaseBinding?.appContainer?.addView(mDataBinding.root)
    // title
    mToolbar = mBaseBinding?.root?.findViewById(R.id.containerToolBar)
    mIbHomeAsUp = mBaseBinding?.root?.findViewById(R.id.containerUp)
    mTvTitle = mBaseBinding?.root?.findViewById(R.id.containerTitle)

    mIbHomeAsUp?.setOnClickListener(this)

  }

  fun <V : View> lazyInitView(id: Int): Lazy<V> = lazy {
    findViewById<V>(id)
  }


  override fun onDestroy() {
    super.onDestroy()
    hideLoadingDialog()
    if (mViewModel != null) {
      lifecycle.removeObserver(mViewModel as LifecycleObserver)
    }
  }


  override fun onClick(p0: View?) {
    when (p0?.id) {
      R.id.containerUp -> finish()

    }
  }

  protected fun hideToolBar() {
    mToolbar?.visibility = View.GONE
  }

  protected fun setDisplayHomeAsUp(show: Boolean) {
    mIbHomeAsUp?.visibility = if (show) View.VISIBLE else View.GONE
  }

  protected fun setToolbarTitle(title: CharSequence) {
    mTvTitle?.text = title
  }

  protected fun setToolbarTitle(@StringRes titleRes: Int) {
    mTvTitle?.setText(titleRes)
  }

  protected fun showToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
  }

  protected fun showLoadingDialog() {
    loadingDialog()
  }

  protected fun hideLoadingDialog() {
    if (dialog != null && dialog?.isShowing!!) {
      dialog?.dismiss()
    }
  }

  private fun loadingDialog() {
    if (!this.isFinishing) {
      if (dialog == null) {
        val builder = AlertDialog.Builder(this, R.style.dialog)
        dialog = builder.create()
        val view = LayoutInflater.from(this).inflate(R.layout.layout_loading, null)
        squareView = view.findViewById(R.id.rollSquareView)
        squareView.visibility = View.VISIBLE
        dialog?.setView(view, 200, 0, 200, 0)
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

}
