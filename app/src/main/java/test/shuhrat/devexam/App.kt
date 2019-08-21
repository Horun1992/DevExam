package test.shuhrat.devexam

import android.app.Application
import test.shuhrat.devexam.di.component.AppComponent
import test.shuhrat.devexam.di.component.DaggerAppComponent

class App : Application() {
    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
        component.inject(this)

    }


    //function for getting dagger instance from Another place
    fun getApplicationAppComponent() : AppComponent = component
}