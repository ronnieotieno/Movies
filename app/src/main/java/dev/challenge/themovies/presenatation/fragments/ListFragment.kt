package dev.challenge.themovies.presenatation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.challenge.themovies.PRODUCT_VIEW_TYPE
import dev.challenge.themovies.R
import dev.challenge.themovies.databinding.FragmentListBinding
import dev.challenge.themovies.model.Movie
import dev.challenge.themovies.presenatation.adapters.Adapter
import dev.challenge.themovies.presenatation.adapters.LoadingStateAdapter
import dev.challenge.themovies.presenatation.viewmodels.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private var job: Job? = null
    private val loadingStateAdapter = LoadingStateAdapter { adapter.retry() }

    private val adapter = Adapter { navigate(it) }
    private var hasBeenCalled = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentListBinding.bind(view)
        setAdapter()


        if (!hasBeenCalled) {
            job?.cancel()
            job = lifecycleScope.launch {
                viewModel.getMovies().collectLatest {
                    adapter.submitData(it)

                }
            }
            hasBeenCalled = true
        }

    }

    private fun setAdapter() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return if (viewType == PRODUCT_VIEW_TYPE) 1
                else 2
            }
        }
        binding.list.layoutManager = gridLayoutManager
        binding.list.adapter = adapter
        binding.list.adapter = adapter.withLoadStateFooter(
            footer = loadingStateAdapter
        )

        adapter.addLoadStateListener { loadingState ->

            binding.progress.isVisible = loadingState.refresh is LoadState.Loading

        }

    }

    private fun navigate(movie: Movie) {

        binding.root.findNavController().navigate(ListFragmentDirections.toSingleFragment(movie))
    }
}