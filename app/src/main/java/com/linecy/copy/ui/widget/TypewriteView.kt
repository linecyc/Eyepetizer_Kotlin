package com.linecy.copy.ui.widget

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 *打字效果view
 *
 * @author by linecy.
 */
class TypewriteView : AppCompatTextView {

  private val subscriptions by lazy { CompositeSubscription() }

  private var realText: CharSequence? = null
  private var timeDelay: Long = 100
  private var isRun = false

  constructor(context: Context) : this(context, null)
  constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,
      defStyleAttr)

  /**
   *开启打字效果
   */
  fun start() {
    if (isRun) {
      return
    } else {
      isRun = true
      if (TextUtils.isEmpty(realText)) {
        realText = text
      }

      text = null
      realText?.let {
        val tempText = CharArray(it.length)
        subscriptions.add(Observable.interval(timeDelay, MILLISECONDS)
            .take(it.length)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ i ->
              tempText[i.toInt()] = it[i.toInt()]
              setText(tempText, 0, it.length)

            }, { e -> e.printStackTrace() }, { isRun = false }
            ))
      }
    }
  }

  /**
   * 停止的时候调用
   */
  fun stop() {
    isRun = false
    subscriptions.clear()
    //text = null
    //this.visibility = View.INVISIBLE
  }

  /**
   * 设置打字效果的间隔时间
   */
  fun setTimeDelay(time: Long, timeUtils: TimeUnit = MILLISECONDS) {
    if (time <= 0) {
      throw RuntimeException("delay time must more than 0")
    }

    when (timeUtils) {
      MILLISECONDS -> this.timeDelay = time
      TimeUnit.SECONDS -> this.timeDelay = time * 1000
      TimeUnit.MINUTES -> this.timeDelay = time * 1000 * 60
      else -> throw RuntimeException("time to long")
    }
  }

  /**
   * 设置展示打字效果的总时间
   */
  fun setTotalTime(time: Long, timeUtils: TimeUnit = MILLISECONDS) {

    if (!TextUtils.isEmpty(text)) {
      setTimeDelay(time, timeUtils)
      this.timeDelay = this.timeDelay / text.length
    } else {
      Timber.i("text is null,typewrite animator will ignore")
    }
  }


  override fun onSaveInstanceState(): Parcelable {
    val ss = TypeSavedState(super.onSaveInstanceState())
    ss.text = realText
    return ss
  }

  override fun onRestoreInstanceState(state: Parcelable?) {
    if (state is TypeSavedState) {
      realText = state.text
    }
    super.onRestoreInstanceState(state)
  }


  class TypeSavedState : BaseSavedState {
    internal var text: CharSequence? = null

    constructor(superState: Parcelable) : super(superState)

    override fun writeToParcel(out: Parcel, flags: Int) {
      super.writeToParcel(out, flags)
      TextUtils.writeToParcel(text, out, flags)
    }

    override fun toString(): String {
      var str = ("TextView.SavedState{"
          + Integer.toHexString(System.identityHashCode(this)))
      if (text != null) {
        str += " text=" + text!!
      }
      return str + "}"
    }

    private constructor(`in`: Parcel) : super(`in`) {
      text = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(`in`)
    }

    companion object {

      val CREATOR: Parcelable.Creator<TypeSavedState> = object : Parcelable.Creator<TypeSavedState> {
        override fun createFromParcel(`in`: Parcel): TypeSavedState {
          return TypeSavedState(`in`)
        }

        override fun newArray(size: Int): Array<TypeSavedState?> {
          return arrayOfNulls(size)
        }
      }
    }
  }
}