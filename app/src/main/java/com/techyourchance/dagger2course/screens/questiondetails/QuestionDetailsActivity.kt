package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.common.dependencyinjection.Service
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.activities.BaseActivity
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator
import com.techyourchance.dagger2course.screens.common.dialogs.ServerErrorDialogFragment
import com.techyourchance.dagger2course.screens.common.toolbar.MyToolbar
import com.techyourchance.dagger2course.screens.common.viewsmvc.ViewMvcFactory
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsViewMvc.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String

    private lateinit var detailsViewMvc: QuestionDetailsViewMvc

    @field:Service private lateinit var fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
    @field:Service private lateinit var dialogsNavigator: DialogsNavigator
    @field:Service private lateinit var screensNavigator: ScreensNavigator
    @field:Service private lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        detailsViewMvc = compositionRoot.viewMvcFactory.newQuestsDetailsViewMvc(null)

        setContentView(detailsViewMvc.rootView)

        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!


    }

    override fun onStart() {
        super.onStart()
        detailsViewMvc.registerListener(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        detailsViewMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    override fun onBackClicked() {
        screensNavigator.navigateBack()
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            detailsViewMvc.showProgressIndication()
            try {
                when(val result = fetchQuestionDetailsUseCase.fetchQuestion(questionId))
                {
                    is FetchQuestionDetailsUseCase.Result.Success -> {
                        detailsViewMvc.bindQuestion(result.question.body)
                    }
                    is FetchQuestionDetailsUseCase.Result.Failure -> onFetchFailed()
                }
            } finally {
                detailsViewMvc.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerErrorDialog()
    }

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }
}
