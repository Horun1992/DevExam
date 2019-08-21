package test.shuhrat.devexam.di.modul

import dagger.Module
import dagger.Provides
import test.shuhrat.devexam.App
import test.shuhrat.devexam.di.ApplicationComponentScope
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {
    @ApplicationComponentScope
    @Provides
    fun provideApplication() = application

}