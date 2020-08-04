package com.example.mymedialist.statistics

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentStatisticsBinding
import com.example.mymedialist.repository.MovieRepository
import timber.log.Timber

class StatisticsFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
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

        statisticsViewModel.fromDatePressed.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                val fromDateDialog = SelectFromDateDialog(statisticsViewModel)
                fromDateDialog.show(parentFragmentManager, "from_date_dialog")
                statisticsViewModel.donePickingFromDate()
            }
        })

        statisticsViewModel.toDatePressed.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                val toDateDialog = SelectToDateDialog(statisticsViewModel)
                toDateDialog.show(parentFragmentManager, "to_date_dialog")
                statisticsViewModel.donePickingToDate()
            }
        })

        statisticsViewModel.okButtonPressed.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                if(statisticsViewModel.fromDate.value != null && statisticsViewModel.toDate.value != null) {
                    statisticsViewModel.getNumberOfMovies()
                } else {
                    Toast.makeText(context, "One of the dates is empty!", Toast.LENGTH_SHORT).show()
                }
                statisticsViewModel.doneNavigatingAfterOkButton()
            }
        })

        statisticsViewModel.movies.observe(viewLifecycleOwner, Observer {
            binding.numberTextInfo.visibility = View.VISIBLE
            binding.numberOfMovies.visibility = View.VISIBLE
            binding.numberOfMovies.text = statisticsViewModel.movies.value?.size.toString()

        })

        statisticsViewModel.fromDate.observe(viewLifecycleOwner, Observer {
            if(it != null) binding.fromDate.text = it.toString()
        })

        statisticsViewModel.toDate.observe(viewLifecycleOwner, Observer {
            if(it != null) binding.toDate.text = it.toString()
        })

        return binding.root
    }
}