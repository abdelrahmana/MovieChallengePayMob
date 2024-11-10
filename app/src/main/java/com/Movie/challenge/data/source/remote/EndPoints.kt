package com.Movie.challenge.data.source.remote

import com.Movie.challenge.data.model.MovieResponse
import com.Movie.challenge.data.model.Result
import com.Movie.challenge.data.model.SourceModel.Companion.FILTERMOVIES
import com.Movie.challenge.data.model.SourceModel.Companion.GENRE
import com.Movie.challenge.data.model.SourceModel.Companion.MOVIEDETAILS
import com.Movie.challenge.data.model.SourceModel.Companion.MOVIEPOPULARLIST
import com.Movie.challenge.data.model.SourceModel.Companion.SEARCHKEYWORD
import com.Movie.challenge.data.model.genertype.GenRes
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface EndPoints {
    @GET(MOVIEPOPULARLIST) // movie list
    suspend fun getMovies(
        @QueryMap hashMap: HashMap<String,Any>
    ): ApiResponse<MovieResponse>

    @GET(MOVIEDETAILS) // movie details
    suspend fun getMovieDetails(@Path ("id")movieDetailsId : Int
    ): ApiResponse<Result>

    @GET(GENRE) // movie details
    suspend fun getGenerTypes(
    ): ApiResponse<GenRes>

    @GET(FILTERMOVIES) // filter movie by genre
    suspend fun getFilteredMovies(@QueryMap hashMap: HashMap<String,Any>
    ): ApiResponse<MovieResponse>

    @GET(SEARCHKEYWORD) // search movie
    suspend fun getMovieBySearch(@QueryMap hashMap: HashMap<String,Any>
    ): ApiResponse<MovieResponse>


}