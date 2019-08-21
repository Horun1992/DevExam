package test.shuhrat.devexam.di.modul

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import test.shuhrat.devexam.App
import test.shuhrat.devexam.adapters.ItemsListAdapter
import test.shuhrat.devexam.di.ActivityComponentScope
import test.shuhrat.devexam.ui.baseviews.BaseActivity
import test.shuhrat.devexam.ui.singinactivity.SingInActivity
import test.shuhrat.devexam.viewmodels.ItemsListViewModel
import test.shuhrat.devexam.viewmodels.SingInViewModel
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: BaseActivity) {
    @ActivityComponentScope
    @Provides
    fun provideAdapter() : ItemsListAdapter = ItemsListAdapter()

    @ActivityComponentScope
    @Provides
    fun provideItemsListViewModel() : ItemsListViewModel = ViewModelProviders.of(activity).get(ItemsListViewModel::class.java)

    @ActivityComponentScope
    @Provides
    fun provideSingInViewModel() : SingInViewModel = ViewModelProviders.of(activity).get(SingInViewModel::class.java)

}