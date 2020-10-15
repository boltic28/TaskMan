package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.KeyResult
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class KeyInteractorImpl(
    private val keyRepository: KeyRepository,
    private val stepService: StepRepository,
    private val taskService: TaskRepository,
    private val ideaService: IdeaRepository
): KeyInteractor {
    override fun insert(item: KeyResult): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun update(item: KeyResult): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: KeyResult): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Single<KeyResult> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Single<List<KeyResult>> {
        TODO("Not yet implemented")
    }


    override fun getChildrenFor(item: KeyResult): Single<KeyResult> =
        Single.just(item)
            .zipWith(
                stepService.getAllForGoal(item.id),
                BiFunction<KeyResult, List<Step>, KeyResult> { key, steps ->
                    key.copy(steps = steps)
                })
            .zipWith(
                taskService.getAllForGoal(item.id),
                BiFunction<KeyResult, List<Task>, KeyResult> { key, tasks ->
                    key.copy(tasks = tasks)
                })
            .zipWith(
                ideaService.getAllForGoal(item.id),
                BiFunction<KeyResult, List<Idea>, KeyResult> { key, ideas ->
                    key.copy(ideas = ideas)
                })
}