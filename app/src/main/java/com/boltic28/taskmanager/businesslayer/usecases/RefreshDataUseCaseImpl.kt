package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.firebaseworker.dto.RemoteRepo
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Observable
import io.reactivex.Observable.merge
import io.reactivex.Single
import io.reactivex.Single.zip
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
) : RefreshDataUseCase {

    override fun clearLocalData(): Single<Int> =
        zip(goalRepository.deleteAll(),
            stepRepository.deleteAll(),
            taskRepository.deleteAll(),
            ideaRepository.deleteAll(),
            keyRepository.deleteAll(),
            { g, s, t, i, k -> g + s + t + i + k })

    override fun refreshAllData(): Observable<BaseItem> =
        merge(
            refreshKeys(), refreshSteps(),
            refreshTasks(),refreshIdeas()
        ).mergeWith(refreshGoals())
            .doOnNext {
                putDataIntoLocalDB(it)
            }

    private fun putDataIntoLocalDB(item: BaseItem) {
        when (item) {
            is Goal -> goalRepository.refreshData(item)
            is Step -> stepRepository.refreshData(item)
            is Task -> taskRepository.refreshData(item)
            is Idea -> ideaRepository.refreshData(item)
            is KeyResult -> keyRepository.refreshData(item)
            else -> throw ClassCastException()
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun refreshGoals(): Observable<Goal> =
        remoteRepoGoal.readAll()


    override fun refreshSteps(): Observable<Step> =
        remoteRepoStep.readAll()


    override fun refreshTasks(): Observable<Task> =
        remoteRepoTask.readAll()


    override fun refreshIdeas(): Observable<Idea> =
        remoteRepoIdea.readAll()


    override fun refreshKeys(): Observable<KeyResult> =
        remoteRepoKey.readAll()

}