package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import io.reactivex.Observable
import io.reactivex.Single

interface RefreshDataUseCase {

    fun clearLocalData() : Single<Int>

    fun refreshAllData(): Observable<BaseItem>
}
