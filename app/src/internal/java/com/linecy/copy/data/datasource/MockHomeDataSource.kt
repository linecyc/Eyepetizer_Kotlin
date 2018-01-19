package com.linecy.copy.data.datasource

import com.linecy.core.data.model.HomeModel
import com.linecy.core.repository.HomeRepository
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




