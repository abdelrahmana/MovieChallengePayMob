package com.Movie.androidtask.data.source.remote.repository

import android.content.Context
import com.Movie.androidtask.data.source.database.AppDataBase
import com.Movie.androidtask.data.source.database.dao.MovieDao
import com.Movie.challenge.data.model.Genre
import com.Movie.challenge.data.model.MovieResponse
import com.Movie.challenge.data.model.Result
import com.Movie.challenge.data.source.remote.EndPoints
import com.banquemisr.challenge05.R
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess

import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import javax.inject.Inject

class MovieRepositoryImplementer @Inject constructor(val localDataBase: AppDataBase,
                                                     @ApplicationContext val context : Context?,val webService : EndPoints,
                                                     val movieDao : MovieDao) : MovieRepository{
    override suspend fun getMovieFromLocal(genreId : Int,completion: (List<Result>?, String?) -> Unit) {
        try {
            val movieDao = localDataBase.movieDao()
            var movies: List<Result> = ArrayList()
           movies = movieDao.getAll()
            var returnedList : List<Result>?=movies
            if (genreId !=0)
             {
                returnedList =  movies.filter { it.genre_ids?.contains(genreId)==true }
            }
            completion.invoke(returnedList, null)
        }catch (e : Exception){
            completion.invoke(null, e.toString())

        }
    }
    override suspend fun getGenreFromLocal(completion: (List<Genre>?, String?) -> Unit) {
        try {
            val genDao = localDataBase.genrDao()
            val list = genDao.getGenrFromLocal()
            completion.invoke(list, null)
        }catch (e : Exception){
            completion.invoke(null, e.toString())
        }
    }

    override suspend fun getMovieDetailsFromLocal(
        movieId: Int,
        completion: (Result?, String?) -> Unit
    ) {
        try {
            val movieDao = movieDao//localDataBase.movieDao()
            val movie: Result = movieDao.getMovieDetails(movieId)
            completion.invoke(movie, null)
        }catch (e : Exception){
            completion.invoke(null, e.toString())

        }
    }

    override suspend fun updateMovie(movieObj: Result) {
        val movieDao = movieDao//localDataBase.movieDao()
        movieDao.updateMovie(movie = movieObj)
    }

    override suspend fun getMovieList(queryMap: HashMap<String,Any>,completion: (MovieResponse?, String?) -> Unit) {
        val res =  webService.getMovies(queryMap)
        val movieDao =  localDataBase.movieDao()
       val allMovies  = movieDao.getAll()
        res.onSuccess {
            data?.results?.map {item->
               val currentItem =  allMovies.find { item.id == it.id }
                item.isFavourite = currentItem?.isFavourite // set item to favourite
            }
                completion(data, null)
            if (!data?.results.isNullOrEmpty())
                try {
                    movieDao.insertMovies(data?.results?:ArrayList())
                }catch (e:Exception){
                    completion(null ,e.toString())

                }

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,context?.getString(R.string.error_happened)) // handle error from error body
        }
    }
    override suspend fun getMovieDetails(movieId : Int,completion: (Result?, String?) -> Unit) {
        val res =  webService.getMovieDetails(movieId)
        res.onSuccess {
            completion(data, null)

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,context?.getString(R.string.error_happened)) // handle error from error body
        }
    }

    override suspend fun getMovieByKeyWord(
        hashMap: HashMap<String,Any>,
        completion: (MovieResponse?, String?) -> Unit
    ){
        val res =  webService.getMovieBySearch(hashMap)
        val movieDao =  localDataBase.movieDao()
        val allMovies = movieDao.getAll()
        res.onSuccess {
            data?.results?.map {item->
                val currentItem =  allMovies.find { item.id == it.id }
                item.isFavourite = currentItem?.isFavourite // set item to favourite
            }
            completion(data, null)

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,context?.getString(R.string.error_happened)) // handle error from error body
        }
    }

    override suspend fun getMovieByKeyWordLocale(
        keyWord : String,
        completion: (List<Result>?, String?) -> Unit
    ) {
        val movieDao =  localDataBase.movieDao()
        try {
           val listResult =  movieDao.getFilterdMoviesByKeyWord(keyWord)
            completion(listResult ,null)
        }
        catch (e : Exception){
            completion(null ,context?.getString(R.string.error_happened)) // handle error from error body
        }
    }
    override suspend fun getGenreTypes(completion: (List<Genre>?, String?) -> Unit) {
        val res = webService.getGenerTypes()
        res.onSuccess {
            localDataBase.genrDao().insertGen(data?.genres?:ArrayList())
            completion(data?.genres, null)

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,context?.getString(R.string.error_happened)) // handle error from error body
        }
    }
    override suspend fun getFilteredMovies(queryMap: HashMap<String, Any>,completion: (MovieResponse?, String?) -> Unit) {
        val res =  webService.getFilteredMovies(queryMap)
        val movieDao =  localDataBase.movieDao()
        val allMovies = movieDao.getAll()
        res.onSuccess {
            data?.results?.map {item->
                val currentItem =  allMovies.find { item.id == it.id }
                item.isFavourite = currentItem?.isFavourite // set item to favourite
            }
            completion(data, null)

        }
        res.onException {
            completion(null ,message.toString())


        }
        res.onError {
            completion(null ,context?.getString(R.string.error_happened)) // handle error from error body
        }
    }


    }

