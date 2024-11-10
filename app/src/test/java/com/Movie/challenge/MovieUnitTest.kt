package com.Movie.challenge

import com.Movie.androidtask.data.source.database.AppDataBase
import com.Movie.androidtask.data.source.database.dao.MovieDao
import com.Movie.androidtask.data.source.remote.repository.MovieRepository
import com.Movie.androidtask.data.source.remote.repository.MovieRepositoryImplementer
import com.Movie.challenge.data.model.Result
import com.Movie.challenge.data.source.remote.EndPoints
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MovieUnitTest {
    private lateinit var database: AppDataBase

    @Mock
    private lateinit var movieEndPoint: EndPoints
    @Mock
    private lateinit var movieDao: MovieDao
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        database = Mockito.mock(AppDataBase::class.java)
        // val context = ApplicationProvider.getApplicationContext<Context>()
        movieRepository = MovieRepositoryImplementer(database, null, movieEndPoint,movieDao)
        movieDao = database.movieDao()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `testDataBase_of_Movie`() {
        runBlocking {
            //movieDao.updateMovie(movie = Result(id = 10, title = "testMovie"))
            movieRepository.updateMovie(
                Result(
                    id = 10,
                    title = "testMovie"
                )
            ) // insert or update movie
            movieRepository.getMovieDetails(10) { result, s ->
                assertNotNull(result)
                assertEquals(result?.id, 10)
                assertEquals(result?.title, "testMovie")
            }

        }
    }

    @After
    fun afterTest() {
        database.close()

    }
}