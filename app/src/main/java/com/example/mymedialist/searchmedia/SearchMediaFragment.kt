package com.example.mymedialist.searchmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentSearchMediaBinding
import com.example.mymedialist.repository.MovieRepository
import com.example.mymedialist.util.hideKeyboard
import timber.log.Timber


class SearchMediaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchMediaBinding.inflate(inflater)

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val movieDao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(movieDao)

        val viewModelFactory = SearchMediaViewModelFactory(application)
        val addMediaViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchMediaViewModel::class.java)
        binding.searchMediaViewModel = addMediaViewModel

        val adapter = SearchMediaAdapter(SearchMediaAdapter.OnClickListener {
            this.findNavController().navigate(
                SearchMediaFragmentDirections.actionAddMediaFragmentToAddItemFragment(it)
            )
        })
        binding.apiMoviesList.adapter = adapter

        addMediaViewModel.searchMovies.observe(viewLifecycleOwner, Observer {
            addMediaViewModel.changeLoadingStatus(SearchMediaViewModel.TmdbApiStatus.LOADING)
            Timber.i("Search clicked and status was set to ${addMediaViewModel.status.value}")
            if (it == true) {
                addMediaViewModel.searchForMovies(binding.editText.text.toString())
                this.requireActivity().hideKeyboard()
                addMediaViewModel.doneSearching()
            }
        })

        addMediaViewModel.moviesList.observe(viewLifecycleOwner, Observer {
            addMediaViewModel.changeLoadingStatus(SearchMediaViewModel.TmdbApiStatus.DONE)
            Timber.i("MovieList Changed and status was set to ${addMediaViewModel.status.value}")
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
