package dev.challenge.themovies.api

import dev.challenge.themovies.BuildConfig
import dev.challenge.themovies.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse
}