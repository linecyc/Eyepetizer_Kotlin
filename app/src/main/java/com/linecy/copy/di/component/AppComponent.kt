package com.linecy.copy.di.component

import com.linecy.copy.CopyApplication
import com.linecy.copy.di.module.ActivityModule
import com.linecy.copy.di.module.AppModule
import com.linecy.copy.di.module.FragmentModule
import com.linecy.copy.di.module.ViewModelFactoryModule
import com.linecy.copy.di.module.ViewModelModule
import com.linecy.eyepetizer.utils.ActivityManager
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author by linecy
 */
@Singleton
@Component(
    modules = [(AndroidInjectionModule::class), (AndroidSupportInjectionModule::class), (AppModule::class), (ViewModelFactoryModule::class), (ActivityModule::class), (FragmentModule::class), (ViewModelModule::class)])
interface AppComponent {

  /* @Component.Builder
   interface Builder {
     @BindsInstance
     fun application(copyApplication: CopyApplication)

     fun build(): AppComponent

   }*/

  //管理activity工具类
  fun activityManager(): ActivityManager

  //提供retrofit
  fun retrofit(): Retrofit

  fun inject(application: CopyApplication)

}