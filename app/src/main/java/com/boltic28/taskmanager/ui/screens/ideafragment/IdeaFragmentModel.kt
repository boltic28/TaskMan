package com.boltic28.taskmanager.ui.screens.ideafragment

import android.os.Bundle
import androidx.navigation.NavController
import com.boltic28.taskmanager.R
import com.boltic28.taskmanager.businesslayer.interactors.IdeaFragmentInteractor
import com.boltic28.taskmanager.datalayer.Cycle
import com.boltic28.taskmanager.datalayer.Progress
import com.boltic28.taskmanager.datalayer.entities.*
import com.boltic28.taskmanager.signtools.UserManager
import com.boltic28.taskmanager.ui.adapter.ItemAdapter
import com.boltic28.taskmanager.ui.adapter.controllers.HolderController
import com.boltic28.taskmanager.ui.base.BaseEntityFragmentModel
import com.boltic28.taskmanager.ui.constant.*
import com.boltic28.taskmanager.utils.Messenger
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class IdeaFragmentModel @Inject constructor(
    @AdapterForIdea
    val adapter: ItemAdapter,
    val messenger: Messenger,
    private val interactor: IdeaFragmentInteractor,
    override var userManager: UserManager
) : BaseEntityFragmentModel<Idea>() {

    override val extraKey: String = IDEA_EXTRA

    override fun refresh() {
        disposables + interactor.getItemById(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { item ->
                mItem.onNext(item)
            }
    }

    override fun update(item: BaseItem) {
        disposables + interactor.update(item as Idea)
            .subscribeOn(Schedulers.io())
            .subscribe { _ -> refresh() }
    }

    override fun delete(item: BaseItem, nav: NavController) {
        disposables + interactor.delete(item as Idea)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{ deleted ->
                messenger.showMessage("$deleted idea converted")
                val bundle = Bundle()
                bundle.putString(LOAD_LIST, IDEA_EXTRA)
                nav.navigate(R.id.mainFragment, bundle)
            }
    }

    fun getParentName(idea: Idea): Single<String> =
        interactor.getParentName(idea)

    fun loadFreeElementsIntoAdapter(idea: Idea, nav: NavController) {
        adapter.setAdapterListener(object : HolderController.OnActionClickListener {
            override fun isNeedToShowConnection(): Boolean = false
            override fun onActionButtonClick(item: BaseItem) {
                attachTo(idea, item)
            }

            override fun onViewClick(item: BaseItem) {
                goToItemFragment(item, nav)
            }
        })
        disposables + interactor.getParentItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.loadNewData(list)
            }
    }

    private fun attachTo(idea: Idea, item: Any) {
        if (item is Step) {
            disposables + interactor.update(idea.copy(stepId = item.id))
                .subscribeOn(Schedulers.io())
                .subscribe { _ -> refresh() }
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
        openDate: LocalDateTime,
        closeDate: LocalDateTime,
        cycle: String
    ): Single<Long> =
        interactor.create(
            Task(
                id = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                uid = "t" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                stepId = item.stepId,
                goalId = item.goalId,
                keyId = item.keyId,
                name = name,
                description = description,
                icon = TASK_EXTRA,
                date = openDate,
                dateClose = closeDate,
                cycle = Cycle.fromString(cycle),
                isDone = false,
                isStarted = false
            )
        )

    fun convertToGoal(
        name: String,
        description: String,
        openDate: LocalDateTime,
        closeDate: LocalDateTime
    ): Single<Long> =
        interactor.create(
            Goal(
                id = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                uid = "g" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                name = name,
                description = description,
                icon = GOAL_EXTRA,
                date = openDate,
                dateClose = closeDate,
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
        openDate: LocalDateTime,
        closeDate: LocalDateTime
    ): Single<Long> =
        interactor.create(
            Step(
                id = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                uid = "s" + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                goalId = item.goalId,
                keyId = item.keyId,
                name = name,
                description = description,
                icon = STEP_EXTRA,
                date = openDate,
                dateClose = closeDate,
                isDone = false,
                isStarted = false,
                progress = Progress.PROGRESS_0,
                tasks = emptyList(),
                ideas = emptyList()
            )
        )
}