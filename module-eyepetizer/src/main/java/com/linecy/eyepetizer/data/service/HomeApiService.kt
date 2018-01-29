package com.linecy.eyepetizer.data.service

import com.linecy.eyepetizer.data.model.HomeModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * @author by linecy
 */
interface HomeApiService {

  /**
   * 获取首页数据
   */
  @GET("v4/tabs/selected")
  fun getHomeMoreData(@Query("date") date: String?, @Query(
      "num") num: String?): Observable<HomeModel>
}