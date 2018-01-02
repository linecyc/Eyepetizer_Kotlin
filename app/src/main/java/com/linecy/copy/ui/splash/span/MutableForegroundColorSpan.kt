package com.linecy.copy.ui.splash.span

import android.graphics.Color
import android.support.annotation.ColorInt
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

/**
 * @author by linecy
 */
class MutableForegroundColorSpan : CharacterStyle(), UpdateAppearance {
  private var mColor = Color.WHITE
  private var mAlpha = 0


  override fun updateDrawState(tp: TextPaint?) {
    if (null != tp) {
      tp.color = mColor
      tp.alpha = mAlpha
    }
  }

  fun getColor(): Int {
    return mColor
  }

  fun setColor(@ColorInt color: Int) {
    this.mColor = color
  }

  fun setAlpha(alpha: Int) {
    this.mAlpha = alpha
  }

}