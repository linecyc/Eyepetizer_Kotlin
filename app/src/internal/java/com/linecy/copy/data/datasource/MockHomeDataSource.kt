package com.linecy.copy.data.datasource

import com.linecy.eyepetizer.data.model.HomeModel
import com.linecy.eyepetizer.repository.HomeRepository
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author by linecy.
 */


class MockHomeDataSource @Inject internal constructor() : HomeRepository {
  override fun getHomeMoreData(date: String?, num: String?): Observable<HomeModel> {
    val data = MockResponse.createHomeData(16)
    return Observable.just(data).delay(2, TimeUnit.SECONDS)
  }
}




