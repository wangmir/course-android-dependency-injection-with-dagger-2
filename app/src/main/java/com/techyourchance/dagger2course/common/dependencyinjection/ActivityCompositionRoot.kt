package com.techyourchance.dagger2course.common.dependencyinjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.screens.common.ScreensNavigator

class ActivityCompositionRoot(
        val activity: AppCompatActivity,
        private val appCompositionRoot: AppCompositionRoot) {

    val layoutInflater = LayoutInflater.from(activity)

    val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    val application get() = appCompositionRoot.application

    val fragmentManager get() = activity.supportFragmentManager

    val stackoverflowApi get() = appCompositionRoot.stackoverflowApi
}
