package com.linecy.copy.di.module

import com.linecy.copy.ui.EmptyFragment
import com.linecy.copy.ui.detail.fragment.AuthorFragment
import com.linecy.copy.ui.home.fragment.FindFragment
import com.linecy.copy.ui.home.fragment.HomeFragment
import com.linecy.copy.ui.home.fragment.RecommendFragment
import com.linecy.copy.ui.splash.fragment.WelcomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author by linecy
 */
@Module abstract class FragmentModule {

  @ContributesAndroidInjector(modules = [(HomeModule::class)])
  abstract fun contributeHomeFragment(): HomeFragment

  @ContributesAndroidInjector(modules = [(HomeModule::class)])
  abstract fun contributeFindFragment(): FindFragment


  @ContributesAndroidInjector
  abstract fun contributeWelcomeFragment(): WelcomeFragment


  @ContributesAndroidInjector
  abstract fun contributeRecommendFragment(): RecommendFragment

  @ContributesAndroidInjector
  abstract fun contributeEmptyFragment(): EmptyFragment

  @ContributesAndroidInjector
  abstract fun contributeAuthor(): AuthorFragment

}