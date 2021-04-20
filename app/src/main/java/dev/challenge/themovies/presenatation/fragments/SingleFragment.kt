package dev.challenge.themovies.presenatation.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.challenge.themovies.R
import dev.challenge.themovies.databinding.FragmentSingleBinding
import dev.challenge.themovies.presenatation.viewmodels.MainViewModel

@AndroidEntryPoint
class SingleFragment : Fragment(R.layout.fragment_single) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentSingleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSingleBinding.bind(view)

        val toolbar = binding.toolbar as Toolbar
        toolbar.elevation = 0.0F
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.title = null
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)

        toolbar.setNavigationOnClickListener {
            binding.root.findNavController().navigateUp()
        }

        val movie = arguments?.let { SingleFragmentArgs.fromBundle(it) }?.movie

        binding.data = movie


    }

}