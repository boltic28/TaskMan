package com.boltic28.taskmanager.ui.screens.ideafragment

import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.IdeaFragmentInteractor
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.constant.IDEA_EXTRA
import com.boltic28.taskmanager.ui.constant.NO_ID
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class IdeaFragmentModel @Inject constructor(
    @AdapterForIdea
    val adapter: ItemAdapter,
    private val interactor: IdeaFragmentInteractor,
    override var userManager: UserManager,
    val messenger: Messenger
) : BaseEntityFragmentModel<Idea>() {

    override val extraKey: String = IDEA_EXTRA

    override fun refresh() {
        disposables + interactor.getIdeaById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                mItem.onNext(item)
            }
    }

    fun update(idea: Idea) {
        disposables + interactor.update(idea)
            .subscribeOn(Schedulers.io())
            .subscribe { _ -> refresh() }
    }

    fun delete(idea: Idea) {
        disposables + interactor.delete(idea)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getParentName(idea: Idea): Single<String> =
        interactor.getParentName(idea)

    fun loadFreeElementsIntoAdapter(idea: Idea, nav: NavController) {
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = false
            override fun onActionButtonClick(item: Any) {
                attachTo(idea, item)
            }

            override fun onViewClick(item: Any) {
                goToItemFragment(item, nav)
            }
        })
        disposables + interactor.getStepsGoalsKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.refreshData(list)
            }
    }

    private fun attachTo(idea: Idea, item: Any) {
        if (item is Step) {
            disposables + interactor.update(idea.copy(stepId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ ->
                    refresh()
                }
        }
        if (item is Goal) {
            disposables + interactor.update(idea.copy(goalId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
        if (item is KeyResult) {
            disposables + interactor.update(idea.copy(keyId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
        }
    }

    fun convertToTask(
        item: Idea,
        name: String,
        description: String,
        closeDate: LocalDate,
        cycle: String
    ): Single<Long> =
        interactor.create(
            Task(
                id = NO_ID,
                stepId = item.stepId,
                goalId = item.goalId,
                keyId = item.keyId,
                name = name,
                description = description,
                icon = R.drawable.task_ph.toString(),
                date = item.date,
                dateClose = LocalDateTime.of(closeDate, LocalTime.now()),
                cycle = Cycle.fromString(cycle),
                isDone = false,
                isStarted = false
            )
        )

    fun convertToGoal(
        item: Idea,
        name: String,
        description: String,
        closeDate: LocalDate
    ): Single<Long> =
        interactor.create(
            Goal(
                id = NO_ID,
                name = name,
                description = description,
                icon = R.drawable.goal_ph.toString(),
                date = item.date,
                dateClose = LocalDateTime.of(closeDate, LocalTime.now()),
                isDone = false,
                isStarted = false,
                steps = emptyList(),
                tasks = emptyList(),
                ideas = emptyList(),
                keys = emptyList(),
                progress = Progress.PROGRESS_0
            )
        )

    fun convertToStep(
        item: Idea,
        name: String,
        description: String,
        closeDate: LocalDate
    ): Single<Long> =
        interactor.create(
            Step(
                id = NO_ID,
                goalId = item.goalId,
                keyId = item.keyId,
                name = name,
                description = description,
                icon = R.drawable.step_ph.toString(),
                date = item.date,
                dateClose = LocalDateTime.of(closeDate, LocalTime.now()),
                isDone = false,
                isStarted = false,
                progress = Progress.PROGRESS_0,
                tasks = emptyList(),
                ideas = emptyList()
            )
        )
}