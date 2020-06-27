package com.example.mymedialist.addmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentAddMediaBinding
import com.example.mymedialist.repository.MovieRepository
import com.example.mymedialist.util.hideKeyboard
import timber.log.Timber


class AddMediaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddMediaBinding.inflate(inflater)

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val movieDao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(movieDao)

        val viewModelFactory = AddMediaViewModelFactory(datasource, application)
        val addMediaViewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddMediaViewModel::class.java)
        binding.addMediaViewModel = addMediaViewModel

        val adapter = AddMediaAdapter(AddMediaAdapter.OnClickListener {
            this.findNavController().navigate(
                AddMediaFragmentDirections.actionAddMediaFragmentToAddItemFragment(it)
            )
        })
        binding.apiMoviesList.adapter = adapter

        addMediaViewModel.searchMovies.observe(this, Observer {
            addMediaViewModel.changeLoadingStatus(AddMediaViewModel.TmdbApiStatus.LOADING)
            Timber.i("Search clicked and status was set to ${addMediaViewModel.status.value}")
            if (it == true) {
                addMediaViewModel.searchForMovies(binding.editText.text.toString())
                this.activity!!.hideKeyboard()
                addMediaViewModel.doneSearching()
            }
        })

        addMediaViewModel.moviesList.observe(this, Observer {
            addMediaViewModel.changeLoadingStatus(AddMediaViewModel.TmdbApiStatus.DONE)
            Timber.i("MovieList Changed and status was set to ${addMediaViewModel.status.value}")
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
