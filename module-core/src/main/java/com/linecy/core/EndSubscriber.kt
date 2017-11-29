package com.linecy.core

import android.support.annotation.CallSuper
import rx.Subscriber

/**
 * @author by linecy
 */
abstract class EndSubscriber<T> : Subscriber<T>() {

  @CallSuper override fun onCompleted() {
    onEnd()
  }

  @CallSuper override fun onError(e: Throwable) {
    onEnd()
  }

  abstract fun onEnd()
}