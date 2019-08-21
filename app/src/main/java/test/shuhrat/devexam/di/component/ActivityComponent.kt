package test.shuhrat.devexam.di.component

import dagger.Component
import test.shuhrat.devexam.App
import test.shuhrat.devexam.data.repository.network.DevExamInternetService
import test.shuhrat.devexam.di.ActivityComponentScope
import test.shuhrat.devexam.di.modul.ActivityModule
import test.shuhrat.devexam.di.modul.AppModule
import test.shuhrat.devexam.di.modul.RetrofitModule
import test.shuhrat.devexam.ui.baseviews.BaseActivity
import test.shuhrat.devexam.ui.itemslistactivity.ItemsListActivity
import test.shuhrat.devexam.ui.singinactivity.SingInActivity
import javax.inject.Singleton

@ActivityComponentScope
@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: ItemsListActivity)

    fun inject(activity: SingInActivity)

}