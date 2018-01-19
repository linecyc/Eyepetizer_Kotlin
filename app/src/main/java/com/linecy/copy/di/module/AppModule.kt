package com.linecy.copy.di.module

import android.app.Application
import android.content.Context
import com.linecy.copy.CopyApplication
import com.linecy.core.utils.ActivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author by linecy
 */
@Module(
    includes = arrayOf(NetworkModule::class, HomeModule::class))
class AppModule(private val application: CopyApplication) {

  @Provides
  @Singleton internal fun provideAppContext(): Context {
    return application.applicationContext
  }

  @Provides
  @Singleton
  fun provideActivityManager(): ActivityManager {
    return ActivityManager()
  }

  @Provides
  @Singleton
  fun provideApplication(): Application {
    return application
  }

}