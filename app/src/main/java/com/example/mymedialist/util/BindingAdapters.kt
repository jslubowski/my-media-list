package com.example.mymedialist.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mymedialist.R
import com.example.mymedialist.searchmedia.SearchMediaViewModel
import timber.log.Timber

//TODO Try to implement later

//@BindingAdapter("imageUrl")
//fun bindImage(imgView: ImageView, imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = ("https://image.tmdb.org/t/p/w500/$imgUrl").toUri().buildUpon().scheme("https").build()
//        Timber.i("URI: $imgUri")
//        Glide
//            .with(imgView.context)
//            .load(imgUri)
//            .into(imgView)
//    }
//}

@BindingAdapter("tmdbApiStatus")
fun bindStatus(statusImageView: ImageView, status: SearchMediaViewModel.TmdbApiStatus?) {
    Timber.i("Inside the tmdbApiStatus binding adapter")
    when (status) {
        SearchMediaViewModel.TmdbApiStatus.LOADING -> {
            Timber.i("TmdbApiStatus is LOADING")
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.baseline_hourglass_empty_black_18dp)
        }
        SearchMediaViewModel.TmdbApiStatus.ERROR -> {
            Timber.i("TmdbApiStatus is ERROR")
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.baseline_error_black_18dp)
        }
        SearchMediaViewModel.TmdbApiStatus.DONE -> {
            Timber.i("TmdbApiStatus is DONE")
            statusImageView.visibility = View.GONE
        }
    }
}