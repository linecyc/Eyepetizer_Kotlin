package com.linecy.copy.di.module

import android.content.Context
import com.linecy.copy.BuildConfig
import com.linecy.copy.di.CacheInterceptor
import com.linecy.copy.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

/**
 * @author by linecy
 */

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideRetrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(httpClient).addCallAdapterFactory(
        RxJavaCallAdapterFactory.create()).addConverterFactory(
        MoshiConverterFactory.create(moshi)).build()
  }


  @Provides
  @Singleton
  fun provideMoshi(): Moshi {
    return Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
  }


  @Provides
  @Singleton
  fun provideOkHttpClient(context: Context): OkHttpClient {
    val builder = OkHttpClient.Builder().connectTimeout(30, SECONDS).writeTimeout(20, SECONDS)
    builder.addInterceptor(CacheInterceptor(context))

    if (BuildConfig.DEBUG) {
      val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.i(it) })
      interceptor.level = BODY
      builder.addInterceptor(interceptor)
    }
    return builder.build()
  }
}
