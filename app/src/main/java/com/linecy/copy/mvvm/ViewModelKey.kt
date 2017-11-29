package com.linecy.copy.mvvm

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER)
@Retention(RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
