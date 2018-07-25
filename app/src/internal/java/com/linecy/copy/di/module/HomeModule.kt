package com.linecy.copy.di.module

import com.linecy.copy.BuildConfig
import com.linecy.copy.data.datasource.HomeDataSource
import com.linecy.copy.data.datasource.MockHomeDataSource
import com.linecy.eyepetizer.repository.HomeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author by linecy.
 */

@Module
class HomeModule {


  @Provides
  @Singleton
  fun provideHomeRepository(homeDataSource: HomeDataSource,
      mockHomeDataSource: MockHomeDataSource): HomeRepository {
    return if (!BuildConfig.DEBUG) mockHomeDataSource else homeDataSource
  }
}
