package com.linecy.core.data.inject

import com.linecy.copy.data.datasource.HomeDataSource
import com.linecy.core.repository.HomeRepository
import retrofit2.Retrofit

/**
 * @author by linecy
 */
object Injection {

  fun provideHomeRepository(retrofit: Retrofit): HomeRepository {
    return HomeDataSource(retrofit)
  }
}