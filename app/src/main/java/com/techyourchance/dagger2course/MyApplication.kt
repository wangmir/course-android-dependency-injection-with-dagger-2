package com.techyourchance.dagger2course

import android.app.Application
import com.techyourchance.dagger2course.common.dependnecyinjection.AppModule

class MyApplication: Application() {

    public lateinit var appModule: AppModule

    override fun onCreate() {
        appModule = AppModule(this)
        super.onCreate()
    }

}
