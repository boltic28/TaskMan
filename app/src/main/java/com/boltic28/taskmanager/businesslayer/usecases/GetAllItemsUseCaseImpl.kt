package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class GetAllItemsUseCaseImpl(
    private val goalRepository: GoalRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val keyRepository: KeyRepository,
    private val ideaRepository: IdeaRepository
): GetAllItemsUseCase {

    override fun getGoals(): Single<List<Goal>> =
        goalRepository.getAll()

    override fun getTasks(): Single<List<Task>> =
        taskRepository.getAll()

    override fun getSteps(): Single<List<Step>> =
        stepRepository.getAll()

    override fun getIdeas(): Single<List<Idea>> =
        ideaRepository.getAll()

    override fun getKeys(): Single<List<KeyResult>> =
        keyRepository.getAll()
}