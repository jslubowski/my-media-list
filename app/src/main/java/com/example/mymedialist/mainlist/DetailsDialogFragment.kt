package com.example.mymedialist.mainlist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mymedialist.R
import com.example.mymedialist.model.MovieEntity

class DetailsDialogFragment(
    private val mainListViewModel: MainListViewModel,
    private var movieEntity: MovieEntity
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.dialog_properties_title)
            builder.setItems(
                R.array.options_array
            ) { _, which ->
                when (which) {
                    0 -> {
                        mainListViewModel.displayMovieDetails(movieEntity)
                    }
                    1 -> {
                        mainListViewModel.removeFromDatabase(movieEntity)
                    }
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}