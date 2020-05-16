package com.example.mymedialist.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.R
import com.example.mymedialist.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMovieDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie_details, container, false
        )

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val movieEntity = MovieDetailsFragmentArgs.fromBundle(arguments!!).selectedMovieEntity

        val viewModelFactory = MovieDetailsViewModelFactory(application, movieEntity)
        val movieDetailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)
        binding.movieDetailsViewModel = movieDetailsViewModel




        return binding.root
    }
}