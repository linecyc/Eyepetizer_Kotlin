package com.linecy.core.data.service

import com.linecy.core.data.model.HomeModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * @author by linecy
 */
interface HomeApiService {

  companion object {
    val BASE_URL: String
      get() = "http://baobab.kaiyuanapp.com/api/"
  }

  /**
   * 获取首页第一页数据
   */
  @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
  fun getHomeData(): Observable<HomeModel>

  /**
   * 获取首页第一页之后的数据
   */
  @GET("v2/feed")
  fun getHomeMoreData(@Query("date") data: String, @Query("num") num: String): Observable<HomeModel>
}