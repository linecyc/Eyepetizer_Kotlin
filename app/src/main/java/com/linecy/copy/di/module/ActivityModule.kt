package com.linecy.copy.di.module

import com.linecy.copy.ui.home.MainActivity
import com.linecy.copy.ui.splash.SplashActivity
import com.linecy.copy.ui.splash.WelcomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy
 */
@Module abstract class ActivityModule {

  @ContributesAndroidInjector(modules = [(FindModule::class)])
  abstract fun contributeHomeActivity(): MainActivity


  @ContributesAndroidInjector
  abstract fun contributeWelcomeActivity(): WelcomeActivity

  @ContributesAndroidInjector
  abstract fun contributeSplashActivity(): SplashActivity

}