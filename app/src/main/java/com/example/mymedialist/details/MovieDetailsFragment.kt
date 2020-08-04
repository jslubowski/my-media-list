package com.example.mymedialist.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mymedialist.R
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentMovieDetailsBinding
import com.example.mymedialist.repository.MovieRepository
import com.example.mymedialist.util.smartTruncate
import timber.log.Timber

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
        val movieEntity = MovieDetailsFragmentArgs.fromBundle(requireArguments()).selectedMovieEntity
        val dao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(dao)

        val viewModelFactory = MovieDetailsViewModelFactory(application, movieEntity, datasource)
        val movieDetailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)
        fillTextViews(binding, movieDetailsViewModel)

        movieDetailsViewModel.editAndNavigateToMainList.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                this.findNavController().navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToMainListFragment()
                )
                movieDetailsViewModel.doneNavigating()
            }
        })

        movieDetailsViewModel.editDateButtonPressed.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                val dateDialog = SelectDateDetailsDialog(movieDetailsViewModel)
                dateDialog.show(requireFragmentManager(), "date_details_dialog")
                movieDetailsViewModel.doneEditingDate()
            }
        })

        movieDetailsViewModel.editRatingButtonPressed.observe( viewLifecycleOwner, Observer {
            if(it == true) {
                val ratingDialog = SelectRatingDetailsDialog(movieDetailsViewModel)
                ratingDialog.show(requireFragmentManager(), "rating_details_dialog")
                movieDetailsViewModel.doneEditingRating()
            }
        })


        movieDetailsViewModel.selectedMovie.observe(viewLifecycleOwner, Observer {
            fillTextViews(binding, movieDetailsViewModel)
        })


        return binding.root
    }

    private fun fillTextViews(
        binding: FragmentMovieDetailsBinding,
        movieDetailsViewModel: MovieDetailsViewModel
    ) {
        binding.movieDetailsViewModel = movieDetailsViewModel
        binding.descriptionText.text =
            movieDetailsViewModel.selectedMovie.value?.description?.smartTruncate(400)
        bindImage(binding.imageCover, movieDetailsViewModel.selectedMovie.value?.imageUrl)
        binding.releaseYearInfoText.text = movieDetailsViewModel.selectedMovie.value?.releaseYear
        binding.lastSeenOnDate.text =
            movieDetailsViewModel.selectedMovie.value?.seenOnDate.toString()
        binding.userRatingText.text =
            " ${movieDetailsViewModel.selectedMovie.value?.rating.toString()} / 10"
    }

    fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri =
                ("https://image.tmdb.org/t/p/w500/$imgUrl").toUri().buildUpon().scheme("https")
                    .build()
            Timber.i("URI: $imgUri")
            Glide
                .with(imgView.context)
                .load(imgUri)
                .into(imgView)
        }
    }
}