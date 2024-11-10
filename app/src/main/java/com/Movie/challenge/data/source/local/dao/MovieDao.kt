package com.Movie.androidtask.data.source.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.Movie.challenge.data.model.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM Result")
    suspend fun getAll(): List<Result>

    @Query("SELECT * FROM Result WHERE id = :movieId LIMIT 1")
    suspend fun getMovieDetails(movieId: Int): Result

    @Query("SELECT * FROM Result Where title LIKE '%' || :keyWord || '%'")
    suspend fun getFilterdMoviesByKeyWord(keyWord: String): List<Result>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(user: Result)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(user: List<Result>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovie(movie: Result) // to set favourite or not
    @Delete
    fun delete(user: Result)
}