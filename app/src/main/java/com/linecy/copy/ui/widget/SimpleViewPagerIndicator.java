package com.linecy.copy.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleViewPagerIndicator extends LinearLayout {

  private static final int COLOR_TEXT_NORMAL = 0xFF000000;
  private static final int COLOR_INDICATOR_COLOR = 0xff1cd39b;
  private OnItemStateClickListener onItemStateClickListener;

  private String[] mTitles;
  private int mTabCount;
  private int mIndicatorColor = COLOR_INDICATOR_COLOR;
  private float mTranslationX;
  private Paint mPaint = new Paint();
  private int mTabWidth;

  public SimpleViewPagerIndicator(Context context) {
    this(context, null);
  }

  public SimpleViewPagerIndicator(Context context, AttributeSet attrs) {
    super(context, attrs);
    mPaint.setColor(mIndicatorColor);
    mPaint.setStrokeWidth(12F);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (mTabCount > 0) {
      mTabWidth = w / mTabCount;
    }
  }

  public void setTitles(String[] titles) {
    mTitles = titles;
    mTabCount = titles.length;
    generateTitleView();
  }

  public void setIndicatorColor(int indicatorColor) {
    this.mIndicatorColor = indicatorColor;
  }

  @Override protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);
    canvas.save();
    canvas.translate(mTranslationX, getHeight() - 2);
    canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
    canvas.restore();
  }

  /**
   * 0-1:position=0 ;1-0:position=0;
   */
  public void scroll(int position, float offset) {

    mTranslationX = getWidth() / mTabCount * (position + offset);
    invalidate();
  }

  public void onSelectorPage(int position) {
    setSelectorTitleColor(position);
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    return super.dispatchTouchEvent(ev);
  }

  private void generateTitleView() {

    if (getChildCount() > 0) this.removeAllViews();
    int count = mTitles.length;

    setWeightSum(count);

    for (int position = 0; position < count; position++) {
      final TextView tv = new TextView(getContext());
      LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
      lp.weight = 1;
      tv.setGravity(Gravity.CENTER);
      if (0 == position) {
        tv.setTextColor(COLOR_INDICATOR_COLOR);
      } else {
        tv.setTextColor(COLOR_TEXT_NORMAL);
      }
      tv.setText(mTitles[position]);
      TextPaint paint = tv.getPaint();
      paint.setFakeBoldText(true);
      tv.setTag(position);
      tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
      tv.setLayoutParams(lp);
      int padding = dip2px(getContext(), 18);
      //tv.setPadding(5, padding, 5, padding);
      tv.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View v) {
          if (onItemStateClickListener != null) {
            onItemStateClickListener.onItemStateClick((int) tv.getTag());
          }
        }
      });
      addView(tv);
    }
  }

  void setSelectorTitleColor(int position) {
    int count = mTitles.length;
    if (position <= count) {
      if (getChildCount() > 0) {
        for (int i = 0; i < count; i++) {
          TextView tv = (TextView) getChildAt(i);
          if (position == i) {
            tv.setTextColor(COLOR_INDICATOR_COLOR);
          } else {
            tv.setTextColor(COLOR_TEXT_NORMAL);
          }
        }
      } else {
        generateTitleView();
      }
    }
  }

  public interface OnItemStateClickListener {

    void onItemStateClick(int position);
  }

  public void setOnItemStateClickListener(OnItemStateClickListener l) {
    onItemStateClickListener = l;
  }

  public static int dip2px(Context context, float dpValue) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
        context.getResources().getDisplayMetrics());
  }
}
