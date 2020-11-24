package com.boltic28.taskmanager.datalayer.di

import android.content.Context
import androidx.room.Room
import com.boltic28.taskmanager.datalayer.room.AppDataBase
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule(private val context: Context) {

    @Provides
    fun provideDataBase(): AppDataBase =
        Room
            .databaseBuilder(context, AppDataBase::class.java, AppDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
}