package com.example.mymedialist.addmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.R
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
        val binding: FragmentAddMediaBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_media, container, false
        )

        val application = requireNotNull(this.activity).application
        val movieDao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(movieDao)

        val viewModelFactory = AddMediaViewModelFactory(datasource, application)
        val addMediaViewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddMediaViewModel::class.java)
        binding.addMediaViewModel = addMediaViewModel

        val adapter = AddMediaAdapter()
        binding.apiMoviesList.adapter = adapter

        addMediaViewModel.searchMovies.observe(this, Observer {
            if (it == true) {
                addMediaViewModel.searchForMovies(binding.editText.text.toString())
                this.activity!!.hideKeyboard()
                addMediaViewModel.doneSearching()
            }
        })

        addMediaViewModel.moviesList.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


//        addMediaViewModel.navigateToList.observe(this, Observer {
//            if(it == true) {
//                addMediaViewModel.searchForMovies(binding.autocompleteTitleTextview.text.toString())
//                val movieEntity = MovieEntity(null, binding.autocompleteTitleTextview.text.toString(), "", "", "", 1, "")
//                this.activity!!.hideKeyboard()
//                addMediaViewModel.addMovieToDatabase(movieEntity)
//                this.findNavController().navigate(
//                    AddMediaFragmentDirections.actionAddMediaFragmentToMainListFragment()
//                )
//                addMediaViewModel.doneNavigating()
//            }
//        })

        return binding.root
    }
}
