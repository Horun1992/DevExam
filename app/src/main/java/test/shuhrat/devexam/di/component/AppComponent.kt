package test.shuhrat.devexam.di.component

import dagger.Component
import test.shuhrat.devexam.App
import test.shuhrat.devexam.data.repository.network.DevExamInternetService
import test.shuhrat.devexam.di.ApplicationComponentScope
import test.shuhrat.devexam.di.modul.AppModule
import test.shuhrat.devexam.di.modul.RetrofitModule
import javax.inject.Singleton

@ApplicationComponentScope
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {
    fun inject(app: App)

    fun getRetrofit() : DevExamInternetService
}