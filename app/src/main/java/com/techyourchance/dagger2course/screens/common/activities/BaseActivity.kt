package com.techyourchance.dagger2course.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.common.dependnecyinjection.*

open class BaseActivity: AppCompatActivity() {

    private val appCompositionRoot get() = (application as MyApplication).appModule

    val activityComponent by lazy {
        DaggerActivityComponent
                .builder()
                .activityModule(ActivityModule(this, appCompositionRoot))
                .build()
    }

    private val presentationComponent by lazy {
        DaggerPresentationComponent
                .builder()
                .presentationModule(PresentationModule(activityComponent))
                .build()
    }

    protected val injector get() = Injector(presentationComponent)
}
