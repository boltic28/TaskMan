package com.boltic28.taskmanager.ui.di

import androidx.fragment.app.Fragment
import com.boltic28.taskmanager.ui.base.BaseFragment
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule

@Module(includes = [AndroidInjectionModule::class])
open class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideActivity(): Fragment = fragment
}