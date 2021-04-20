package dev.challenge.themovies.data.respository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.challenge.themovies.api.MovieService
import dev.challenge.themovies.data.datasource.MovieDataSource
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: MovieService) {
    fun getMovies() = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            MovieDataSource(movieService)
        }
    ).flow
}