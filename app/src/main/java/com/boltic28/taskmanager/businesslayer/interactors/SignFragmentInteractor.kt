package com.boltic28.taskmanager.businesslayer.interactors

import com.boltic28.taskmanager.businesslayer.usecases.RefreshDataUseCase

class SignFragmentInteractor(
    dataRefresher: RefreshDataUseCase
) :
    RefreshDataUseCase by dataRefresher