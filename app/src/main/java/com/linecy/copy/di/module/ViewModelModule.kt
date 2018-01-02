package com.linecy.copy.di.module

import android.arch.lifecycle.ViewModel
import com.linecy.copy.mvvm.ViewModelKey
import com.linecy.copy.mvvm.viewmodel.FindViewModel
import com.linecy.copy.mvvm.viewmodel.WelcomeViewModel
import com.linecy.copy.mvvm.viewmodel.RecommendViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author by linecy
 */
@Module abstract class ViewModelModule {


  @Binds
  @IntoMap
  @ViewModelKey(FindViewModel::class)
  abstract fun bindFindViewModel(findViewModel: FindViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RecommendViewModel::class)
  abstract fun bindHomeViewModel(recommendViewModel: RecommendViewModel): ViewModel


  @Binds
  @IntoMap
  @ViewModelKey(WelcomeViewModel::class)
  abstract fun bindWelcomeViewModel(welcomeViewModel: WelcomeViewModel): ViewModel

}