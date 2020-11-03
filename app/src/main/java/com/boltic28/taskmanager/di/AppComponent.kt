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
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    modules = [AndroidInjectionModule::class, AppModule::class]
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

//    fun getFragmentComponent(
//        fragmentModule: FragmentModule,
//        interactModule: InteractModule,
//        goalFragModule: GoalFragmentModule,
//        stepFragModule: StepFragmentModule,
//        taskFragModule: TaskFragmentModule,
//        ideaFragModule: IdeaFragmentModule,
//        keyFragModule: KeyFragmentModule
//    ): LocalFragmentComponent

    @Subcomponent(
        modules = [
            ScreensModule::class, AppModule::class,
            ActivityModule::class, FragmentModule::class,
            InteractModule::class, GoalFragmentModule::class,
            StepFragmentModule::class, TaskFragmentModule::class,
            IdeaFragmentModule::class, KeyFragmentModule::class
        ]
    )
    interface LocalActivityComponent : ActivityComponent

//    @FragmentScope
//    @Subcomponent(
//        modules = [
//            ScreensModule::class, ActivityModule::class, FragmentModule::class,
//            InteractModule::class, GoalFragmentModule::class,
//            StepFragmentModule::class, TaskFragmentModule::class,
//            IdeaFragmentModule::class, KeyFragmentModule::class
//        ]
//    )
//    interface LocalFragmentComponent : FragmentComponent

    @Component.Builder
    interface Builder{
        fun addModule(module: AppModule): Builder
        fun createComponent(): AppComponent
    }
}