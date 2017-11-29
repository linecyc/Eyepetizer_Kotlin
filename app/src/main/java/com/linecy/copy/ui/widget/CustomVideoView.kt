package com.linecy.copy.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

/**
 * @author by linecy
 */
class CustomVideoView : VideoView {
  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr)

  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
      context, attrs, defStyleAttr, defStyleRes)


  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val width = View.getDefaultSize(0, widthMeasureSpec)
    val height = View.getDefaultSize(0, heightMeasureSpec)
    setMeasuredDimension(width, height)
  }
}