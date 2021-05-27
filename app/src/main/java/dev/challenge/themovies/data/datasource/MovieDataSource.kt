package dev.challenge.themovies.data.datasource

import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.challenge.themovies.api.MovieService
import dev.challenge.themovies.model.Movie
import java.io.IOException

class MovieDataSource(private val movieService: MovieService) :
    PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val data = movieService.getMovies(page = page)
            LoadResult.Page(
                data = data.movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.page == 500) null else page + 1
            )


        } catch (t: Throwable) {
            var exception = t

            if (t is IOException) {
                exception = IOException("Please check internet connection")
            }
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {

        return state.anchorPosition

    }

}