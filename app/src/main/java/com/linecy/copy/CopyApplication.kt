package com.linecy.copy

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.linecy.copy.di.component.DaggerAppComponent
import com.linecy.copy.di.module.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * @author by linecy
 */

class CopyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

  @Inject
  lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

  @Inject
  lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return fragmentInjector
  }

  override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
        .inject(this)

  }

}
