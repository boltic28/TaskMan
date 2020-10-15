package com.boltic28.taskmanager.businesslayer

import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.datalayer.room.goal.DefaultGoalRepository
import com.boltic28.taskmanager.datalayer.room.goal.GoalRepository
import com.boltic28.taskmanager.datalayer.room.idea.DefaultIdeaRepository
import com.boltic28.taskmanager.datalayer.room.idea.IdeaRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.DefaultKeyRepository
import com.boltic28.taskmanager.datalayer.room.keyresult.KeyRepository
import com.boltic28.taskmanager.datalayer.room.step.DefaultStepRepository
import com.boltic28.taskmanager.datalayer.room.step.StepRepository
import com.boltic28.taskmanager.datalayer.room.task.DefaultTaskRepository
import com.boltic28.taskmanager.datalayer.room.task.TaskRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GoalInteractorImpl(
    private val keyRepository: KeyRepository,
    private val stepRepository: StepRepository,
    private val taskRepository: TaskRepository,
    private val ideaRepository: IdeaRepository,
    private var goalRepository: GoalRepository
): GoalInteractor {
    override fun insert(item: Goal): Single<Long> {
        TODO("Not yet implemented")
    }

    override fun update(item: Goal): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun delete(item: Goal): Single<Int> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Single<Goal> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Single<List<Goal>> {
        TODO("Not yet implemented")
    }

    override fun getChildrenFor(item: Goal): Single<Goal> =
        Single.just(item)
            .zipWith(keyRepository.getAllForGoal(item.id),
                BiFunction<Goal, List<KeyResult>, Goal> { goal, keys ->
                    goal.copy(keys = keys)
                })
            .zipWith(
                stepRepository.getAllForGoal(item.id),
                BiFunction<Goal, List<Step>, Goal> { goal, steps ->
                    goal.copy(steps = steps)
                })
            .zipWith(
                taskRepository.getAllForGoal(item.id),
                BiFunction<Goal, List<Task>, Goal> { goal, tasks ->
                    goal.copy(tasks = tasks)
                })
            .zipWith(
                ideaRepository.getAllForGoal(item.id),
                BiFunction<Goal, List<Idea>, Goal> { goal, ideas ->
                    goal.copy(ideas = ideas)
                })
}