package com.techyourchance.dagger2course.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsViewMvc
import com.techyourchance.dagger2course.screens.questionslist.QuestionListViewMvc

class ViewMvcFactory(private val layoutInflater: LayoutInflater) {

    fun newQuestsListViewMvc(parent: ViewGroup?): QuestionListViewMvc {
        return QuestionListViewMvc(layoutInflater, parent)
    }

    fun newQuestsDetailsViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflater, parent)
    }
}