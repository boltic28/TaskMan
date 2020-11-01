package com.boltic28.taskmanager.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.boltic28.taskmanager.utils.Messenger
import com.boltic28.taskmanager.utils.NetworkChecker
import dagger.MapKey
import dagger.Module
import dagger.Provides
import kotlin.reflect.KClass

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideMessenger(): Messenger =
        Messenger(app)

    @Provides
    fun provideNetworkChecker(): NetworkChecker =
        NetworkChecker(app)


    @Provides
    fun provideApplication(): App = app

    @Provides
    fun providesViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory = factory

}


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(
    val value: KClass<out ViewModel>
)
