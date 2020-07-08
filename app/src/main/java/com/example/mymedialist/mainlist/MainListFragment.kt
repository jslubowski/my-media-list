package com.example.mymedialist.mainlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mymedialist.R
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentMainListBinding
import com.example.mymedialist.repository.MovieRepository

class MainListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val dao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(dao)

        val viewModelFactory = MainListViewModelFactory(datasource, application)
        val mainListViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainListViewModel::class.java)
        binding.mainListViewModel = mainListViewModel

        val adapter = MainListAdapter(MainListAdapter.OnLongClickListener {
            val dialog = DetailsDialogFragment(mainListViewModel, it)
            dialog.show(this.childFragmentManager , "options_dialog")
            return@OnLongClickListener true
        })

        binding.mediaList.adapter = adapter

        mainListViewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        mainListViewModel.navigateToMovieDetails.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(
                    MainListFragmentDirections.actionMainListFragmentToMovieDetailsFragment(it)
                )
                mainListViewModel.displayMovieDetailsComplete()
            }
        })

        mainListViewModel.navigateToAddScreen.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    MainListFragmentDirections.actionMainListFragmentToAddMediaFragment()
                )
                mainListViewModel.doneNavigating()
            }
        })

        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.statistics_menu -> {
                    this.findNavController().navigate(
                        MainListFragmentDirections.actionMainListFragmentToStatisticsFragment()
                    )
                    true
                }
                else -> false
            }
        }

        return binding.root
    }


}
