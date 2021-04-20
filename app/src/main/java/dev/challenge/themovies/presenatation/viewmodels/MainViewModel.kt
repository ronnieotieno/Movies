package dev.challenge.themovies.presenatation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.challenge.themovies.data.respository.MovieRepository
import dev.challenge.themovies.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private var currentResult: Flow<PagingData<Movie>>? = null
    fun getMovies(): Flow<PagingData<Movie>> {
        val newResult =
            repository.getMovies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}