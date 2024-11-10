package com.Movie.androidtask.data.source.remote.repository

import com.Movie.challenge.data.model.Genre
import com.Movie.challenge.data.model.MovieResponse
import com.Movie.challenge.data.model.Result

interface MovieRepository {
    suspend fun getMovieFromLocal(genreId: Int, completion: (List<Result>?, String?) -> Unit)

    suspend fun getMovieByKeyWordLocale(
        keyWord: String,
        completion: (List<Result>?, String?) -> Unit
    )

    suspend fun getGenreFromLocal(completion: (List<Genre>?, String?) -> Unit)
    suspend fun getMovieByKeyWord(
        hashMap: HashMap<String, Any>,
        completion: (MovieResponse?, String?) -> Unit
    )

    suspend fun getMovieDetailsFromLocal(movieId: Int, completion: (Result?, String?) -> Unit)
    suspend fun updateMovie(movieObj: Result)

    suspend fun getMovieList(
        queryMap: HashMap<String, Any>,
        completion: (MovieResponse?, String?) -> Unit
    )

    suspend fun getGenreTypes(completion: (List<Genre>?, String?) -> Unit)

    suspend fun getMovieDetails(movieId: Int, completion: (Result?, String?) -> Unit)
    suspend fun getFilteredMovies(
        queryMap: HashMap<String, Any>,
        completion: (MovieResponse?, String?) -> Unit
    )

}