package co.laith.clearscoredount.di

import co.laith.clearscoredount.BuildConfig
import co.laith.clearscoredount.service.ClearScoreApi
import co.laith.clearscoredount.service.ClearScoreService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    @Inject
    internal fun provideClearScoreService(clearScoreApi: ClearScoreApi): ClearScoreService {
        return ClearScoreService(clearScoreApi)
    }

    @Provides
    @Singleton
    @Inject
    internal fun provideClearScoreAPI(retrofitBuilder: Retrofit.Builder): ClearScoreApi {
        val retrofit = retrofitBuilder.baseUrl(BuildConfig.API_URL).build()
        return retrofit.create(ClearScoreApi::class.java)
    }


    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
    }

}