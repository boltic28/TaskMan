package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observable.zip
import io.reactivex.schedulers.Schedulers

class RefreshDataUseCaseImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository,
    private val remoteRepoGoal: RemoteRepo<Goal>,
    private val remoteRepoStep: RemoteRepo<Step>,
    private val remoteRepoTask: RemoteRepo<Task>,
    private val remoteRepoIdea: RemoteRepo<Idea>,
    private val remoteRepoKey: RemoteRepo<KeyResult>
): RefreshDataUseCase {

    override fun refreshAllData() {
        goalRepository.deleteAll().subscribeOn(Schedulers.io()).subscribe()
        stepRepository.deleteAll().subscribeOn(Schedulers.io()).subscribe()
        taskRepository.deleteAll().subscribeOn(Schedulers.io()).subscribe()
        ideaRepository.deleteAll().subscribeOn(Schedulers.io()).subscribe()
        keyRepository.deleteAll().subscribeOn(Schedulers.io()).subscribe()

        refreshGoals().subscribeOn(Schedulers.io()).subscribe()
        refreshSteps().subscribeOn(Schedulers.io()).subscribe()
        refreshTasks().subscribeOn(Schedulers.io()).subscribe()
        refreshIdeas().subscribeOn(Schedulers.io()).subscribe()
        refreshKeys().subscribeOn(Schedulers.io()).subscribe()
    }

    override fun refreshGoals(): Observable<Goal> =
        remoteRepoGoal.readAll()
            .doOnNext { item ->
                goalRepository.refreshData(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }

    override fun refreshSteps(): Observable<Step> =
        remoteRepoStep.readAll()
            .doOnNext { item ->
                stepRepository.refreshData(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }

    override fun refreshTasks(): Observable<Task> =
        remoteRepoTask.readAll()
            .doOnNext { item ->
                taskRepository.refreshData(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }

    override fun refreshIdeas(): Observable<Idea> =
        remoteRepoIdea.readAll()
            .doOnNext { item ->
                ideaRepository.refreshData(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }

    override fun refreshKeys(): Observable<KeyResult> =
        remoteRepoKey.readAll()
            .doOnNext { item ->
                keyRepository.refreshData(item)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
            }
}