package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.Idea
import com.boltic28.taskmanager.datalayer.entities.Step
import com.boltic28.taskmanager.datalayer.entities.Task
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class StepInteractorImpl(
    var taskRepository: TaskRepository,
    var ideaRepository: IdeaRepository,
    var stepRepository: StepRepository
): StepInteractor {
    override fun insert(item: Step): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun update(item: Step): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Step): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Single<Step> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Single<List<Step>> {
        TODO("Not yet implemented")
    }

    override fun getAllForGoal(goalId: Long): Single<List<Step>> {
        TODO("Not yet implemented")
    }

    override fun getAllForKey(keyId: Long): Single<List<Step>> {
        TODO("Not yet implemented")
    }

    override fun getChildrenFor(item: Step): Single<Step> =
        Single.just(item)
            .zipWith(
                taskRepository.getAllForGoal(item.id),
                BiFunction<Step, List<Task>, Step> { step, tasks ->
                    step.copy(tasks = tasks)
                })
            .zipWith(
                ideaRepository.getAllForGoal(item.id),
                BiFunction<Step, List<Idea>, Step> { step, ideas ->
                    step.copy(ideas = ideas)
                })
}