package com.linecy.copy.di.module

import com.linecy.copy.ui.home.MainActivity
import com.linecy.copy.ui.home.fragment.FindFragment
import com.linecy.copy.ui.home.fragment.HomeFragment
import com.linecy.copy.ui.splash.SplashActivity
import com.linecy.copy.ui.splash.WelcomeActivity
import com.linecy.copy.ui.splash.fragment.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy
 */
@Module abstract class ActivityModule {

  @ContributesAndroidInjector(modules = arrayOf(FindModule::class))
  abstract fun contributeHomeActivity(): MainActivity

  @ContributesAndroidInjector(modules = arrayOf(FindModule::class))
  abstract fun contributeHomeFragment(): HomeFragment

  @ContributesAndroidInjector(modules = arrayOf(FindModule::class))
  abstract fun contributeFindFragment(): FindFragment


  @ContributesAndroidInjector
  abstract fun contributeWelcomeActivity(): WelcomeActivity

  @ContributesAndroidInjector
  abstract fun contributeWelcomeFragment(): WelcomeFragment


  @ContributesAndroidInjector
  abstract fun contributeSplashActivity(): SplashActivity

}