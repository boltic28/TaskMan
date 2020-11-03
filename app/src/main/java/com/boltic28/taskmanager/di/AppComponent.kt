package com.boltic28.taskmanager.di

import androidx.lifecycle.ViewModel
import com.boltic28.taskmanager.ui.di.*
import com.boltic28.taskmanager.ui.screens.goalfragment.GoalFragmentModule
import com.boltic28.taskmanager.ui.screens.ideafragment.IdeaFragmentModule
import com.boltic28.taskmanager.ui.screens.keyfragment.KeyFragmentModule
import com.boltic28.taskmanager.ui.screens.stepfragment.StepFragmentModule
import com.boltic28.taskmanager.ui.screens.taskfragment.TaskFragmentModule
import dagger.Component
import dagger.Subcomponent

@AppScope
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    val activityComponent: ActivityComponent

    fun inject(model: ViewModel)
//    fun inject(fragment: BaseFragment<*>)

    fun getActivityComponent(
        activityModule: ActivityModule,
        interactModule: InteractModule,
        goalFragModule: GoalFragmentModule,
        stepFragModule: StepFragmentModule,
        taskFragModule: TaskFragmentModule,
        ideaFragModule: IdeaFragmentModule,
        keyFragModule: KeyFragmentModule
    ): LocalActivityComponent

    @ActivityScope
    @Subcomponent(
        modules = [ScreensModule::class, ActivityModule::class,
            InteractModule::class, GoalFragmentModule::class,
            StepFragmentModule::class, TaskFragmentModule::class,
            IdeaFragmentModule::class, KeyFragmentModule::class
        ]
    )
    interface LocalActivityComponent : ActivityComponent

    @Component.Builder
    interface Builder{
        fun addModule(module: AppModule): Builder
        fun createComponent(): AppComponent
    }
}