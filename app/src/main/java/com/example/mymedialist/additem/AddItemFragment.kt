package com.example.mymedialist.additem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymedialist.addmedia.AddMediaViewModelFactory
import com.example.mymedialist.database.MediaDatabase
import com.example.mymedialist.databinding.FragmentAddItemBinding
import com.example.mymedialist.repository.MovieRepository
import timber.log.Timber

class AddItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAddItemBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedItem = AddItemFragmentArgs.fromBundle(arguments!!).selectedResult
        val application = requireNotNull(this.activity).application
        val movieDao = MediaDatabase.getInstance(application).movieDao
        val datasource = MovieRepository(movieDao)

        val viewModelFactory = AddItemViewModelFactory(selectedItem, datasource, application)
        val addItemViewModel = ViewModelProvider(this, viewModelFactory)
            .get(AddItemViewModel::class.java)
        binding.addItemViewModel = addItemViewModel

        binding.movieTitle.text = selectedItem.originalTitle
        binding.releaseYear.text = selectedItem.releaseDate
        binding.descriptionText.text = selectedItem.overview
        val seenOn = addItemViewModel.selectedDate.value.toString()
        if (seenOn != "null") {
            binding.seenOnDate.text = addItemViewModel.selectedDate.value.toString()
        } else {
            binding.seenOnDate.text = ""
        }
        bindImage(binding.movieCover, selectedItem.posterPath)

        addItemViewModel.setDatePressed.observe(this, Observer {
            val datePickerDialog = SelectDateDialog(addItemViewModel)
            datePickerDialog.show(fragmentManager!!, "date_dialog")
        })

        addItemViewModel.selectedDate.observe(this, Observer {
            if( it != null) {
                binding.seenOnDate.text = addItemViewModel.selectedDate.value.toString()
            }
            addItemViewModel.dateSet()
        })

        return binding.root
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