package com.example.mymedialist.mainlist

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
import com.example.mymedialist.databinding.FragmentMainListBinding

class MainListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main_list, container, false
        )

        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application

        val dataSource = MediaDatabase.getInstance(application).movieDao

        val viewModelFactory = MainListViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainListViewModel::class.java)
        binding.mainListViewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = MainListAdapter()
        binding.mediaList.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }


}
