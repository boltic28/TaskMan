package com.boltic28.taskmanager.datalayer.room.dagger

import android.content.Context
import androidx.room.Room
import com.boltic28.taskmanager.datalayer.room.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDataBase(): AppDataBase =
        Room
            .databaseBuilder(context, AppDataBase::class.java, AppDataBase.DB_NAME)
            .build()
}