package com.example.mathsolver.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.mathsolver.BuildConfig
import com.example.mathsolver.MathSolverApplication
import com.example.mathsolver.data.database.MathExpressionDao
import com.example.mathsolver.data.database.MathExpressionDatabase
import com.example.mathsolver.data.network.WolframAlphaService
import com.example.mathsolver.data.repository.MathExpressionRepository
import com.example.mathsolver.ui.view.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWolframAlphaService(retrofit: Retrofit): WolframAlphaService {
        return retrofit.create(WolframAlphaService::class.java)
    }

    @Provides
    @Singleton
    fun provideMathExpressionRepository(
        wolframAlphaService: WolframAlphaService,
        mathExpressionDao: MathExpressionDao
    ): MathExpressionRepository {
        return MathExpressionRepository(wolframAlphaService, mathExpressionDao)
    }

    @Provides
    @Singleton
    fun provideMathExpressionDao(database: MathExpressionDatabase): MathExpressionDao {
        return database.mathExpressionDao()
    }

    @Provides
    @Singleton
    fun provideMathExpressionDatabase(application: Application): MathExpressionDatabase {
        return Room.databaseBuilder(
            application,
            MathExpressionDatabase::class.java,
            "math_expression_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMainViewModel(
        repository: MathExpressionRepository
    ): MainViewModel {
        return MainViewModel(repository)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }


}




