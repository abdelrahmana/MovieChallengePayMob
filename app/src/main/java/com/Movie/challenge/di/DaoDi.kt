package com.Movie.androidtask.di

import android.content.Context
import androidx.room.Room
import com.Movie.androidtask.data.model.Constants
import com.Movie.androidtask.data.source.database.AppDataBase
import com.Movie.androidtask.data.source.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class, FragmentComponent::class, ActivityComponent::class,
    ServiceComponent::class)
class DaoDi {
    @Provides
    fun getDataBase(@ApplicationContext context: Context): AppDataBase {
       return Room.databaseBuilder(
            context,
            AppDataBase::class.java, Constants.DATABASENAME
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    fun provideMovieDao(database: AppDataBase): MovieDao {
        return database.movieDao()
    }
}