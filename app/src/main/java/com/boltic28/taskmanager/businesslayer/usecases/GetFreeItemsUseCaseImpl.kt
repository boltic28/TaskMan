package com.boltic28.taskmanager.businesslayer.usecases

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single

class GetFreeItemsUseCaseImpl(
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val keyRepository: KeyRepository,
    private val ideaRepository: IdeaRepository
): GetFreeItemsUseCase {

    override fun getFreeTasks(): Single<List<Task>> =
        taskRepository.getAllFree()

    override fun getFreeSteps(): Single<List<Step>> =
        stepRepository.getAllFree()

    override fun getFreeIdeas(): Single<List<Idea>> =
        ideaRepository.getAllFree()

    override fun getFreeKeys(): Single<List<KeyResult>> =
        keyRepository.getAllFree()
}