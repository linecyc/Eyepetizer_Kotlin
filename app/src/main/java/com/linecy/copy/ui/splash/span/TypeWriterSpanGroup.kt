package com.linecy.copy.ui.splash.span

/**
 * @author linecy
 */


class TypeWriterSpanGroup(private val mAlpha: Float) {
  private val mSpans = ArrayList<MutableForegroundColorSpan>()


  fun addSpan(span: MutableForegroundColorSpan) {
    span.setAlpha((mAlpha * 255).toInt())
    mSpans.add(span)
  }


  fun setAlpha(alpha: Float) {
    val size = mSpans.size
    var total = 1.0f * size * alpha
    (0 until size)
        .asSequence()
        .map { mSpans[it] }
        .forEach {
          if (total >= 1.0f) {
            it.setAlpha(255)
            total -= 1.0f
          } else {
            it.setAlpha((total * 255).toInt())
            total = 0.0f
          }
        }
  }

  fun getAlpha(): Float {
    return mAlpha
  }

}