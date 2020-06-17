package com.example.mymedialist.addmedia

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymedialist.R
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentAddMediaBinding
import com.example.mymedialist.model.MovieEntity
import com.example.mymedialist.network.Result
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
        // TODO find out how to create adapter
        //  In order to create adapter,
        //  there has to be an array to pass to ArrayAdapter (duh),

        val adapter = ArrayAdapter<Result>(application, R.layout.movie_autocomplete_row)
        binding.autocompleteTitleTextview.setAdapter(adapter)

        binding.autocompleteTitleTextview.doAfterTextChanged {
            if(it?.length!! > 2) {
                val movieList = addMediaViewModel.getMovieSearchResult(binding.autocompleteTitleTextview.text.toString())
                Timber.i(movieList.toString())
                Timber.i("SIZE: ${movieList.size}")
            }
        }

        addMediaViewModel.navigateToList.observe(this, Observer {
            if(it == true) {
                addMediaViewModel.getMovieSearchResult(binding.autocompleteTitleTextview.text.toString())
                val movieEntity = MovieEntity(null, binding.autocompleteTitleTextview.text.toString(), "", "", "", 1, "")
                this.activity!!.hideKeyboard()
                addMediaViewModel.addMovieToDatabase(movieEntity)
                this.findNavController().navigate(
                    AddMediaFragmentDirections.actionAddMediaFragmentToMainListFragment()
                )
                addMediaViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
