package com.linecy.copy.ui.misc

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.linecy.copy.R

/**
 * @author by linecy
 */
class ViewContainer(context: Context?, attrs: AttributeSet?) : BatterViewAnimator(
    context, attrs) {
  val emptyMessage = lazyInitView<TextView>(R.id.emptyMessage)
  val emptyImage = lazyInitView<TextView>(R.id.emptyImage)
  val errorMessage = lazyInitView<TextView>(R.id.errorMessage)
  val errorImage = lazyInitView<TextView>(R.id.errorImage)

  init {
    View.inflate(context, R.layout.view_container, this)
  }

  private var onReloadCallback: OnReloadCallback? = null
  private var onEmptyCallback: OnEmptyCallback? = null


  override fun onFinishInflate() {
    super.onFinishInflate()
//    val emptyMessage: TextView = findViewById(R.id.emptyMessage)
//    val emptyImage: ImageView = findViewById(R.id.emptyImage)
//    val errorMessage: TextView = findViewById(R.id.errorMessage)
//    val errorImage: TextView = findViewById(R.id.errorImage)


    findViewById<LinearLayout>(R.id.error).setOnClickListener({ onReloadCallback?.onReload() })

    findViewById<LinearLayout>(R.id.empty).setOnClickListener({ onEmptyCallback?.onEmpty() })
  }

  /**
   * 添加错误时回调
   */
  fun setOnReloadCallback(onReloadCallback: OnReloadCallback?) {
    this.onReloadCallback = onReloadCallback
  }

  /**
   * 添加空页面回调
   */
  fun setOnEmptyCallback(onEmptyCallback: OnEmptyCallback?) {
    this.onEmptyCallback = onEmptyCallback
  }


  /**
   * 设置错误信息
   */
  fun setErrorMessage(@NonNull charSequence: CharSequence) {
    errorMessage.value.text = charSequence
  }

  fun setErrorMessage(@StringRes msgResId: Int) {
    errorMessage.value.setText(msgResId)
  }


  /**
   * 设置空页面信息
   */
  fun setEmptyMessage(@NonNull charSequence: CharSequence) {
    emptyMessage.value.text = charSequence
  }

  fun setEmptyMessage(@StringRes msgResId: Int) {
    emptyMessage.value.setText(msgResId)
  }


  fun setErrorMessageColor(@ColorRes color: Int) {
    errorMessage.value.setTextColor(ContextCompat.getColor(context, color))
  }


  private fun <V : View> lazyInitView(id: Int): Lazy<V> = lazy {
    findViewById<V>(id)
  }


  interface OnReloadCallback {

    fun onReload()
  }

  interface OnEmptyCallback {

    fun onEmpty()
  }
}