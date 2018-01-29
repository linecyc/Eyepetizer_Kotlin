package com.linecy.eyepetizer.repository

import com.linecy.eyepetizer.data.model.HomeModel
import rx.Observable

/**
 * @author by linecy
 */
interface HomeRepository {


  fun getHomeMoreData(date: String?, num: String?): Observable<HomeModel>
}