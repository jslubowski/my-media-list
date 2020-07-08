package com.example.mymedialist.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentStatisticsBinding
import com.example.mymedialist.repository.MovieRepository

class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStatisticsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application
        val movieDao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(movieDao)

        val viewModelFactory = StatisticsViewModelFactory(datasource, application)
        val statisticsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(StatisticsViewModel::class.java)
        binding.statisticsViewModel = statisticsViewModel



        return binding.root
    }
}