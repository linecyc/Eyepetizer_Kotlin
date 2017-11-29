package com.linecy.copy.di.module

import com.linecy.copy.data.datasource.HomeDataSource
import com.linecy.core.repository.HomeRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author by linecy
 */

@Module
class FindModule {


  @Provides
  @Singleton

  fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository {
    return homeDataSource
  }
}
