package com.Movie.androidtask.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.Movie.androidtask.data.source.database.dao.GenrDao
import com.Movie.androidtask.data.source.database.dao.MovieDao
import com.Movie.challenge.data.model.Genre
import com.Movie.challenge.data.model.ProductionCompany
import com.Movie.challenge.data.model.ProductionCountry
import com.Movie.challenge.data.model.Result
import com.Movie.challenge.data.model.RoomConverter
import com.Movie.challenge.data.model.SpokenLanguage

@Database(entities = [Result::class, ProductionCompany::class
    , ProductionCountry::class, SpokenLanguage::class, Genre::class], version = 2)
@TypeConverters(RoomConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genrDao() : GenrDao
}