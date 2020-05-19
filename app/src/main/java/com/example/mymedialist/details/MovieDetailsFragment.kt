package com.example.mymedialist.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymedialist.R
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentMovieDetailsBinding
import com.example.mymedialist.repository.MovieRepository

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
        val dao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(dao)

        val viewModelFactory = MovieDetailsViewModelFactory(application, movieEntity, datasource)
        val movieDetailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)
        binding.movieDetailsViewModel = movieDetailsViewModel

        movieDetailsViewModel.EditAndNavigateToMainList.observe(this, Observer {
            if(it == true) {
                this.findNavController().navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMainListFragment()
                )
                movieDetailsViewModel.doneNavigating()
            }
        })


        return binding.root
    }
}