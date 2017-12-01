package com.linecy.core.repository

import com.linecy.core.data.model.HomeModel
import rx.Observable

/**
 * @author by linecy
 */
interface HomeRepository {


  fun getHomeMoreData(date: String?, num: String?): Observable<HomeModel>
}