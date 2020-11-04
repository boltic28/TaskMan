package com.boltic28.taskmanager.datalayer.di

import com.boltic28.taskmanager.di.AppScope
import com.boltic28.taskmanager.ui.base.BaseViewModel
import dagger.Subcomponent

@AppScope
@Subcomponent(
    modules = [DataBaseModule::class, RepositoryModule::class]
)
interface DataComponent {

    fun inject(model: BaseViewModel)
}