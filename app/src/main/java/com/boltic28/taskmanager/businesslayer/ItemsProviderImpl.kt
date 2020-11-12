package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class ItemsProviderImpl(
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private val stepRepository: StepRepository,
    private val goalRepository: GoalRepository,
    private val keyRepository: KeyRepository
): ItemsProvider {

    override fun getFreeTasks(): Single<List<Task>> =
        taskRepository.getAllFree()

    override fun getTasks(): Single<List<Task>> =
        taskRepository.getAll()

    override fun getFreeIdeas(): Single<List<Idea>> =
        ideaRepository.getAllFree()

    override fun getIdeas(): Single<List<Idea>> =
        ideaRepository.getAll()

    override fun getFreeKeys(): Single<List<KeyResult>> =
        keyRepository.getAllFree()

    override fun getKeys(): Single<List<KeyResult>> =
        keyRepository.getAll()

    override fun getFreeSteps(): Single<List<Step>> =
        stepRepository.getAllFree()

    override fun getSteps(): Single<List<Step>> =
        stepRepository.getAll()

    override fun getGoals(): Single<List<Goal>> =
        goalRepository.getAll()

    override fun getFreeParents(): Single<List<ParentItem>> =
        Single.zip(Single.just(mutableListOf<ParentItem>()),
            goalRepository.getAll(),
            keyRepository.getAll(),
            stepRepository.getAll(),
            { list, goals, keys, steps ->
                list.addAll(goals)
                list.addAll(keys)
                list.addAll(steps)
                return@zip list
            }
        )

    override fun getGoalById(id: Long): Single<Goal> =
        goalRepository.getById(id)

    override fun getStepById(id: Long): Single<Step> =
        stepRepository.getById(id)

    override fun getTaskById(id: Long): Single<Task> =
        taskRepository.getById(id)

    override fun getIdeaById(id: Long): Single<Idea> =
        ideaRepository.getById(id)

    override fun getKeyById(id: Long): Single<KeyResult> =
        keyRepository.getById(id)
}