package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.Goal
import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class KeyFragmentInteractorImpl(
    private val keyRepository: KeyRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val goalRepository: GoalRepository
) : KeyFragmentInteractor {

    override fun insert(item: KeyResult): Single<Long> =
        keyRepository.insert(item)

    override fun update(item: KeyResult): Single<Int> =
        keyRepository.update(item)

    override fun delete(item: KeyResult): Single<Int> =
        keyRepository.delete(item)

    override fun getKeyById(id: Long): Single<KeyResult> =
        keyRepository.getById(id)

    override fun getGoalById(id: Long): Single<Goal> =
        goalRepository.getById(id)

    override fun getGoals(): Single<List<Goal>> =
        goalRepository.getAll()

    override fun getFreeIdeas(): Single<List<Idea>> =
        ideaRepository.getAllFree()

    override fun getFreeTasks(): Single<List<Task>> =
        taskRepository.getAllFree()

    override fun updateTask(item: Task): Single<Int> =
        taskRepository.update(item)

    override fun updateIdea(item: Idea): Single<Int> =
        ideaRepository.update(item)

    override fun setChildrenFor(item: KeyResult): Single<KeyResult> =
        Single.just(item)
            .zipWith(
                taskRepository.getAllForKey(item.id), { key, tasks ->
                    key.copy(tasks = tasks)
                })
            .zipWith(
                ideaRepository.getAllForKey(item.id), { key, ideas ->
                    key.copy(ideas = ideas)
                })

    override fun setProgressFor(key: KeyResult): KeyResult {
        val pointsToFull = key.tasks.size
        val done = key.tasks.filter { it.isDone }.size
        val isStarted = key.tasks.any { it.isStarted }
        val nKey = if (pointsToFull == 0) {
            key.copy(progress = Progress.DONE, isStarted = isStarted)
        } else {
            when ((done * 100.0) / pointsToFull) {
                100.0 -> key.copy(progress = Progress.DONE, isDone = true, isStarted = isStarted)
                in 75.0..99.0 -> key.copy(progress = Progress.PROGRESS_80, isStarted = isStarted)
                in 55.0..74.0 -> key.copy(progress = Progress.PROGRESS_60, isStarted = isStarted)
                in 35.0..54.0 -> key.copy(progress = Progress.PROGRESS_40, isStarted = isStarted)
                in 15.0..34.0 -> key.copy(progress = Progress.PROGRESS_20, isStarted = isStarted)
                else -> key.copy(progress = Progress.PROGRESS_0, isStarted = isStarted)
            }
        }
        keyRepository.update(nKey).subscribeOn(Schedulers.io()).subscribe()
        return nKey
    }
}