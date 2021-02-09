package com.techyourchance.dagger2course.common.dependnecyinjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
        val activity: AppCompatActivity,
        private val appModule: AppModule
) {

    private val screensNavigator by lazy {
        ScreensNavigator(activity)
    }

    @Provides
    fun activity() = this.activity
    @Provides
    fun screensNavigator(activity: AppCompatActivity) = screensNavigator

    @Provides
    fun application() = appModule.application
    @Provides
    fun layoutInflater() = LayoutInflater.from(activity)
    @Provides
    fun fragmentManager() = activity.supportFragmentManager
    @Provides
    fun stackoverflowApi() = appModule.stackoverflowApi()
}
