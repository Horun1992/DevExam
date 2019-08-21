package test.shuhrat.devexam.di.modul

import dagger.Module
import dagger.Provides
import test.shuhrat.devexam.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.shuhrat.devexam.data.repository.network.DevExamInternetService
import test.shuhrat.devexam.di.ApplicationComponentScope
import javax.inject.Singleton

@Module
class RetrofitModule {
    @ApplicationComponentScope
    @Provides
    fun provideRetrofitService() : DevExamInternetService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(DevExamInternetService::class.java)
    }
}