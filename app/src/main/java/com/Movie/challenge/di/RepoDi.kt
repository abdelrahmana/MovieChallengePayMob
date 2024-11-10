package com.Movie.androidtask.di

import android.content.Context
import com.Movie.androidtask.data.source.database.AppDataBase
import com.Movie.androidtask.data.source.database.dao.MovieDao
import com.Movie.androidtask.data.source.remote.repository.MovieRepository
import com.Movie.androidtask.data.source.remote.repository.MovieRepositoryImplementer
import com.Movie.challenge.data.source.remote.EndPoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class, FragmentComponent::class,
    ActivityComponent::class)
class RepoDi {
    @Provides
    fun getRepo(
        localDataBase: AppDataBase,@ApplicationContext context : Context,endPoints: EndPoints,movieDao: MovieDao
    ): MovieRepository {
        return  MovieRepositoryImplementer(localDataBase,context,endPoints,movieDao)
    }


}