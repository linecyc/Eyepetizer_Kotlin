package com.linecy.copy.data.datasource

import com.linecy.core.data.model.HomeModel
import com.linecy.core.data.service.HomeApiService
import com.linecy.core.repository.HomeRepository
import retrofit2.Retrofit
import rx.Observable
import timber.log.Timber
import javax.inject.Inject

/**
 * @author by linecy
 */
class HomeDataSource @Inject internal constructor(retrofit: Retrofit) : HomeRepository {
  private val service: HomeApiService = retrofit.create(HomeApiService::class.java)

  override fun getHomeData(): Observable<HomeModel> {
    Timber.i("------------------>>>>getHomeData")
    return service.getHomeData()
  }

  override fun getHomeMoreData(data: String, num: String): Observable<HomeModel> {
    return service.getHomeMoreData(data, num)
  }


}