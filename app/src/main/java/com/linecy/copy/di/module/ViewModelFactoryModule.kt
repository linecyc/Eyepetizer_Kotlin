package com.linecy.copy.di.module

import android.arch.lifecycle.ViewModelProvider
import com.linecy.copy.mvvm.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * @author by linecy
 */
@Module
abstract class ViewModelFactoryModule {

  @Binds abstract
  fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}