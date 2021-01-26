package com.techyourchance.dagger2course.common.composition

import android.app.Activity
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.questions.FetchQuestionsUseCase
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator
import com.techyourchance.dagger2course.screens.common.viewsmvc.ViewMvcFactory

class ActivityCompositionRoot(private val activity: AppCompatActivity, private val appCompositionRoot: AppCompositionRoot) {

    val viewMvcFactory get() = ViewMvcFactory(LayoutInflater.from(activity))

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    val intent get() = activity.intent

    private val fragmentManager get() = activity.supportFragmentManager
    val dialogsNavigator get() = DialogsNavigator(fragmentManager)

    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi
    val fetchQuestionDetailsUseCase get() = FetchQuestionDetailsUseCase(stackoverflowApi)
    val fetchQuestionsUseCase get () = FetchQuestionsUseCase(stackoverflowApi)

}
